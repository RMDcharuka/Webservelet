package org.nsbm.dea.student_management_system.token;

import java.util.Optional;

public class TokenParams {
  public Optional<String> ajti;
  public Optional<String> rjti;

  public TokenParams(Optional<String> ajti, Optional<String> rjti) {
    this.ajti = ajti;
    this.rjti = rjti;
  }

  public TokenParams() {
    this.ajti = Optional.empty();
    this.rjti = Optional.empty();
  }

  public TokenParams withAjti(String ajti) {
    this.ajti = Optional.of(ajti);
    return this;
  }

  public TokenParams withRjti(String rjti) {
    this.rjti = Optional.of(rjti);
    return this;
  }
}
