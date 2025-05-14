package org.nsbm.dea.student_management_system.token;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.nsbm.dea.student_management_system.token.TokenError.ErrorKind;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.jaspeen.ulid.ULID;

/**
 * PrimaryClaims
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrimaryClaims implements Claims {
  private int sub;
  private String jti;
  private String rjti;
  private Date exp;
  private Date iat;
  private Date nbf;
  private Optional<String> custom;

  public PrimaryClaims() {

  }

  public PrimaryClaims(int sub, long exp, Optional<String> jti, Optional<String> rjti, Optional<String> custom) {
    Instant now = Instant.now();

    this.sub = sub;
    this.jti = jti.orElse(ULID.random().toString());
    this.rjti = rjti.orElse(this.jti);
    this.iat = Date.from(now);
    this.nbf = Date.from(now.plusSeconds(5));
    this.exp = Date.from(now.plusSeconds(exp));
    this.custom = custom;
  }

  public PrimaryClaims(int sub, Date exp, Date iat, Date nbf, String jti, String rjti, String custom) {
    this.sub = sub;
    this.jti = jti;
    this.rjti = rjti;
    this.iat = iat;
    this.nbf = nbf;
    this.exp = exp;
    if (custom == null || custom == "") {
      this.custom = Optional.empty();
    } else {
      this.custom = Optional.of(custom);
    }
  }

  @Override
  public PrimaryClaims newInstance() {
    return new PrimaryClaims();
  }

  @Override
  public int getSub() {
    return sub;
  }

  @Override
  public String getJti() {
    return jti;
  }

  @Override
  public String getRjti() {
    return rjti;
  }

  @Override
  public Date getExp() {
    return exp;
  }

  @Override
  public Date getIat() {
    return iat;
  }

  @Override
  public Date getNbf() {
    return nbf;
  }

  @Override
  public Optional<String> getCustom() {
    return custom;
  }

  @Override
  public Claims getClaims(DecodedJWT jwt) throws TokenError {
    try {
      this.sub = jwt.getClaim("sub").asInt();
      this.jti = jwt.getClaim("jti").asString();
      this.rjti = jwt.getClaim("rjti").asString();
      this.iat = jwt.getClaim("iat").asDate();
      this.exp = jwt.getClaim("exp").asDate();

      String custom = jwt.getClaim("custom").asString();
      if (custom == null || custom == "") {
        this.custom = Optional.empty();
      } else {
        this.custom = Optional.of(custom);
      }

      return this;
    } catch (Exception exception) {
      throw new TokenError(ErrorKind.OTHER, "Failed to decode the token", exception);
    }
  }

  @Override
  public String toJsonString() throws TokenError {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(this);
    } catch (Exception exception) {
      throw new TokenError(ErrorKind.OTHER, "failed to convert the token to json", exception);
    }
  }
}
