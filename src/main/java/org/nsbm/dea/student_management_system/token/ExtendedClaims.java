package org.nsbm.dea.student_management_system.token;

import java.util.Optional;
import org.nsbm.dea.student_management_system.model.user.UserDetails;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class ExtendedClaims implements Claims {
  @JsonUnwrapped
  private final PrimaryClaims primaryClaims;

  @JsonUnwrapped
  private final UserDetails userDetails;

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
}
