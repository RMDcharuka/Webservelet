package org.nsbm.dea.student_management_system.token.types;

import java.sql.SQLException;
import java.util.Optional;

import org.nsbm.dea.student_management_system.config.Env;
import org.nsbm.dea.student_management_system.dao.SessionDAO;
import org.nsbm.dea.student_management_system.state.Redis;
import org.nsbm.dea.student_management_system.token.PrimaryClaims;
import org.nsbm.dea.student_management_system.token.Token;
import org.nsbm.dea.student_management_system.token.TokenError;
import org.nsbm.dea.student_management_system.token.TokenParams;
import org.nsbm.dea.student_management_system.token.TokenResponse;
import org.nsbm.dea.student_management_system.token.TokenType;

import io.github.jaspeen.ulid.ULID;
import redis.clients.jedis.AbstractPipeline;
import redis.clients.jedis.exceptions.JedisException;

public class Refresh extends Token<PrimaryClaims> {
  private Optional<Integer> sub = Optional.empty();

  @Override
  public String getSecretKey() {
    return Env.getRefreshTokenSecret();
  }

  @Override
  public long getExp() {
    return Env.getRefreshTokenExpiration();
  }

  public Refresh() {
  }

  public Refresh(int sub) {
    this.sub = Optional.of(sub);
  }

  @Override
  public TokenResponse<PrimaryClaims> create(TokenParams params) throws TokenError {
    if (sub.isEmpty()) {
      throw new TokenError(TokenError.ErrorKind.OTHER, "sub is not set", null);
    }

    String ajti = ULID.random().toString();
    PrimaryClaims claims = new PrimaryClaims(sub.get(), this.getExp(), Optional.empty(), Optional.empty(),
        Optional.of(ajti));
    String token = this.generate(claims);

    var pool = Redis.getPool();
    var jedis = pool.getResource();

    try {
      try (AbstractPipeline pipe = jedis.pipelined()) {
        pipe.setex(TokenType.REFRESH.getKey(claims.getJti()), this.getExp(), ajti);
        pipe.setex(TokenType.ACCESS.getKey(ajti), this.getExp(), String.format("%d", claims.getSub()));

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

  public void delete(String rjti) throws TokenError {
    var pool = Redis.getPool();
    var jedis = pool.getResource();

    try {
      SessionDAO.delete(rjti);

      String value = jedis.get(TokenType.REFRESH.getKey(rjti));
      if (value == null || value == "") {
        throw new TokenError(TokenError.ErrorKind.VALIDATION_FAILED, "refresh token is not found", null);
      }

      try (AbstractPipeline pipe = jedis.pipelined()) {
        pipe.del(TokenType.REFRESH.getKey(rjti));
        pipe.del(TokenType.ACCESS.getKey(value));

        pipe.sync();
      }
    } catch (JedisException e) {
      throw new TokenError(TokenError.ErrorKind.OTHER, "failed to establish redis connection", e);
    } catch (SQLException e) {
      throw new TokenError(TokenError.ErrorKind.OTHER, "failed to delete session from database", e);
    } finally {
      pool.returnResource(jedis);
      pool.close();
    }
  }
}
