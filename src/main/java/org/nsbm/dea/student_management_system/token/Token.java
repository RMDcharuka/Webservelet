package org.nsbm.dea.student_management_system.token;

import org.nsbm.dea.student_management_system.token.TokenError.ErrorKind;
import org.nsbm.dea.student_management_system.state.Redis;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import redis.clients.jedis.exceptions.JedisException;

public abstract class Token<T extends Claims> {
  public abstract String getSecretKey();

  public abstract long getExp();

  public String generate(T claims) throws TokenError {
    Algorithm algorithm = Algorithm.HMAC256(this.getSecretKey());

    return JWT.create()
        .withIssuer("org.nsbm.dea")
        .withPayload(claims.toJsonString())
        .sign(algorithm);
  }

  public DecodedJWT decode(String token) throws TokenError {
    try {
      Algorithm algorithm = Algorithm.HMAC256(this.getSecretKey());
      JWTVerifier verifier = JWT.require(algorithm)
          .withIssuer("org.nsbm.dea")
          .withClaimPresence("sub")
          .withClaimPresence("jti")
          .withClaimPresence("rjti")
          .build();

      return verifier.verify(token);
    } catch (JWTVerificationException exception) {
      throw new TokenError(ErrorKind.VALIDATION_FAILED, "failed to validate the token", exception);
    } catch (Exception e) {
      throw new TokenError(ErrorKind.OTHER, "failed to decode the token", e);
    }
  }

  String jti(DecodedJWT decodedJWT) {
    return decodedJWT.getClaim("jti").asString();
  }

  String rjti(DecodedJWT decodedJWT) {
    return decodedJWT.getClaim("rjti").asString();
  }

  String sub(DecodedJWT decodedJWT) {
    return decodedJWT.getClaim("sub").asString();
  }

  public abstract TokenResponse<T> create(TokenParams params) throws TokenError;

  public void verify(String token, TokenType type) throws TokenError {
    DecodedJWT decodedJWT = this.decode(token);
    var pool = Redis.getPool();
    var jedis = pool.getResource();

    String jti = this.jti(decodedJWT);
    String rjti = this.rjti(decodedJWT);
    String sub = this.sub(decodedJWT);
    if (jti == null || jti == "" || rjti == null || rjti == "" || sub == null || sub == "") {
      throw new TokenError(ErrorKind.VALIDATION_FAILED, "token is not valid", null);
    }

    try {
      String value = jedis.get(type.getKey(jti));
      if (value == null || value == "") {
        throw new TokenError(ErrorKind.VALIDATION_FAILED, "token is not valid", null);
      }

      switch (type) {
        case ACCESS:
          if (!value.equals(sub)) {
            throw new TokenError(ErrorKind.VALIDATION_FAILED, "token is not valid", null);
          }
        case REFRESH:
          if (value.isEmpty()) {
            throw new TokenError(ErrorKind.VALIDATION_FAILED, "token is not valid", null);
          }

        case SESSION:
          throw new UnsupportedOperationException("session token can be veirfied with self.decode method");
      }
    } catch (JedisException e) {
      throw new TokenError(ErrorKind.OTHER, "failed to get the token from redis", e);
    } finally {
      pool.returnResource(jedis);
      pool.close();
    }
  }
}
