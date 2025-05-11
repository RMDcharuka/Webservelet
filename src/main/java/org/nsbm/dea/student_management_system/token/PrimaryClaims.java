package org.nsbm.dea.student_management_system.token;

import java.util.Date;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.github.jaspeen.ulid.ULID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrimaryClaims implements Claims {
  private final int sub;
  private final String jti;
  private final String rjti;
  private final long exp;
  private final long iat;
  private final long nbf;
  private final Optional<String> custom;

  PrimaryClaims(int sub, long exp, Optional<String> jti, Optional<String> rjti, Optional<String> custom) {
    this.sub = sub;
    this.jti = jti.orElse(ULID.random().toString());
    this.rjti = rjti.orElse(this.jti);
    this.iat = new Date().getTime() / 1000;
    this.nbf = this.iat + 5;
    this.exp = this.iat + exp;
    this.custom = custom;
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
  public long getExp() {
    return exp;
  }

  @Override
  public long getIat() {
    return iat;
  }

  @Override
  public long getNbf() {
    return nbf;
  }

  @Override
  public Optional<String> getCustom() {
    return custom;
  }
}
