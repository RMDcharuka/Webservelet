package org.nsbm.dea.student_management_system.token.types;

import java.util.Optional;

import org.nsbm.dea.student_management_system.config.Env;
import org.nsbm.dea.student_management_system.model.user.UserDetails;
import org.nsbm.dea.student_management_system.token.ExtendedClaims;
import org.nsbm.dea.student_management_system.token.Token;
import org.nsbm.dea.student_management_system.token.TokenError;
import org.nsbm.dea.student_management_system.token.TokenParams;
import org.nsbm.dea.student_management_system.token.TokenResponse;
import org.nsbm.dea.student_management_system.token.TokenType;

public class Session extends Token<ExtendedClaims> {
  private Optional<UserDetails> userDetails = Optional.empty();

  public Session() {

  }

  public Session(UserDetails userDetails) {
    this.userDetails = Optional.of(userDetails);
  }

  @Override
  public String getSecretKey() {
    return Env.getSessionTokenSecret();
  }

  @Override
  public long getExp() {
    return Long.parseLong(Env.getSessionTokenSecret());
  }

  @Override
  public TokenResponse<ExtendedClaims> create(TokenParams params) throws TokenError {
    if (userDetails.isEmpty()) {
      throw new TokenError(TokenError.ErrorKind.OTHER, "userDetails is not set", null);
    }
    var userDetails = this.userDetails.get();

    var claims = new ExtendedClaims(userDetails, this.getExp());
    var token = this.generate(claims);

    return new TokenResponse<ExtendedClaims>(token, claims);
  }

  @Override
  public void verify(String token, TokenType type) throws TokenError {
    this.decode(token);
  }
}
