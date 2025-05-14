package org.nsbm.dea.student_management_system.token;

import java.util.Optional;

public class TokenParams {
  public Optional<String> ajti;
  public Optional<String> rjti;

  TokenParams(Optional<String> ajti, Optional<String> rjti) {
    this.ajti = ajti;
    this.rjti = rjti;
  }

  public static TokenParams withAjti(String ajti) {
    return new TokenParams(Optional.of(ajti), Optional.empty());
  }

  public static TokenParams withRjti(String rjti) {
    return new TokenParams(Optional.empty(), Optional.of(rjti));
  }
}
