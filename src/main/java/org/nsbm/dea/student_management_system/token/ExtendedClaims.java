package org.nsbm.dea.student_management_system.token;

import java.util.Date;
import java.util.Optional;

import org.nsbm.dea.student_management_system.model.user.UserDetails;
import org.nsbm.dea.student_management_system.token.TokenError.ErrorKind;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExtendedClaims implements Claims {
  @JsonUnwrapped
  private PrimaryClaims primaryClaims;

  @JsonUnwrapped
  private UserDetails userDetails;

  public ExtendedClaims() {
  }

  public ExtendedClaims(UserDetails user, long exp) {
    this.userDetails = user;
    this.primaryClaims = new PrimaryClaims(this.userDetails.getId(), exp, Optional.empty(), Optional.empty(),
        Optional.empty());
  }

  public PrimaryClaims getPrimaryClaims() {
    return primaryClaims;
  }

  public UserDetails getUserDetails() {
    return userDetails;
  }

  @Override
  public ExtendedClaims newInstance() {
    return new ExtendedClaims();
  }

  @Override
  public int getSub() {
    return primaryClaims.getSub();
  }

  @Override
  public String getJti() {
    return primaryClaims.getJti();
  }

  @Override
  public String getRjti() {
    return primaryClaims.getRjti();
  }

  @Override
  public long getIat() {
    return primaryClaims.getIat();
  }

  @Override
  public long getExp() {
    return primaryClaims.getExp();
  }

  @Override
  public long getNbf() {
    return primaryClaims.getNbf();
  }

  @Override
  public Optional<String> getCustom() {
    return primaryClaims.getCustom();
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

  @Override
  public ExtendedClaims getClaims(DecodedJWT jwt) throws TokenError {
    try {
      int sub = jwt.getClaim("sub").asInt();
      String jti = jwt.getClaim("jti").asString();
      String rjti = jwt.getClaim("rjti").asString();
      Date iat = jwt.getClaim("iat").asDate();
      Date exp = jwt.getClaim("exp").asDate();
      Date nbf = jwt.getClaim("nbf").asDate();
      String custom = jwt.getClaim("custom").asString();
      String email = jwt.getClaim("email").asString();
      String photoURL = jwt.getClaim("photo_url").asString();
      String name = jwt.getClaim("name").asString();

      this.primaryClaims = new PrimaryClaims(sub, exp, iat, nbf, jti, rjti, custom);
      this.userDetails = new UserDetails(sub, email, name, photoURL);

      return this;
    } catch (Exception exception) {
      throw new TokenError(ErrorKind.OTHER, "Failed to decode the token", exception);
    }
  }
}
