package org.nsbm.dea.student_management_system.token.types;

import java.util.Optional;

import org.nsbm.dea.student_management_system.config.Env;
import org.nsbm.dea.student_management_system.state.Redis;
import org.nsbm.dea.student_management_system.token.PrimaryClaims;
import org.nsbm.dea.student_management_system.token.Token;
import org.nsbm.dea.student_management_system.token.TokenError;
import org.nsbm.dea.student_management_system.token.TokenParams;
import org.nsbm.dea.student_management_system.token.TokenResponse;
import org.nsbm.dea.student_management_system.token.TokenType;

import io.github.jaspeen.ulid.ULID;
import redis.clients.jedis.AbstractPipeline;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisException;

public class Access extends Token<PrimaryClaims> {
  private Optional<Integer> sub = Optional.empty();

  @Override
  public String getSecretKey() {
    return Env.getRefreshTokenSecret();
  }

  @Override
  public long getExp() {
    return Env.getAccessTokenExpiration();
  }

  public Access() {
  }

  public Access(int sub) {
    this.sub = Optional.of(sub);
  }

  @Override
  public TokenResponse<PrimaryClaims> create(TokenParams params) throws TokenError {
    if (sub.isEmpty()) {
      throw new TokenError(TokenError.ErrorKind.OTHER, "sub is not set", null);
    }
    String ajti = params.ajti.orElse(ULID.random().toString());
    String rjti = params.rjti.orElseThrow(() -> new TokenError(TokenError.ErrorKind.OTHER,
        "refresh token jti is needed to create the an access token", null));
    PrimaryClaims claims = new PrimaryClaims(sub.get(), this.getExp(), Optional.of(ajti), Optional.of(rjti),
        Optional.empty());
    String token = this.generate(claims);

    var pool = Redis.getPool();
    var jedis = pool.getResource();

    try {
      String value = jedis.get(TokenType.REFRESH.getKey(rjti));
      String key = "no-key";
      if (value != null || value != "") {
        key = TokenType.ACCESS.getKey(value);
      }

      try (AbstractPipeline pipe = jedis.pipelined()) {
        pipe.del(key);
        pipe.sendCommand(Protocol.Command.SET, TokenType.REFRESH.getKey(rjti), ajti, "KEEPTTL");
        pipe.setex(TokenType.ACCESS.getKey(ajti), this.getExp(), String.format("%d", sub.get()));

        pipe.sync();
      }

      return new TokenResponse<PrimaryClaims>(token, claims);
    } catch (JedisException e) {
      throw new TokenError(TokenError.ErrorKind.OTHER, "failed to establish redis connection", e);
    } finally {
      pool.returnResource(jedis);
      pool.close();
    }
  }

  public TokenResponse<PrimaryClaims> refresh(String rjti) throws TokenError {
    if (sub.isEmpty()) {
      throw new TokenError(TokenError.ErrorKind.OTHER, "sub is not set", null);
    }

    var claims = new PrimaryClaims(sub.get(), this.getExp(), Optional.empty(), Optional.of(rjti), Optional.empty());
    var token = this.generate(claims);

    var pool = Redis.getPool();
    var jedis = pool.getResource();

    try {
      String value = jedis.get(TokenType.REFRESH.getKey(rjti));
      String key = "no-key";
      if (value != null || value != "") {
        key = TokenType.ACCESS.getKey(value);
      }

      try (AbstractPipeline pipe = jedis.pipelined()) {
        pipe.del(key);
        pipe.sendCommand(Protocol.Command.SET, TokenType.REFRESH.getKey(rjti), claims.getJti(), "KEEPTTL");
        pipe.setex(TokenType.ACCESS.getKey(claims.getJti()), this.getExp(), String.format("%d", sub.get()));

        pipe.sync();
      }

      return new TokenResponse<PrimaryClaims>(token, claims);
    } catch (JedisException exception) {
      throw new TokenError(TokenError.ErrorKind.OTHER, "failed to establish redis connection", exception);
    } finally {
      pool.returnResource(jedis);
      pool.close();
    }
  }
}
