package org.nsbm.dea.student_management_system.token;

public class TokenResponse<T extends Claims> {
  private final String token;
  private final T claims;

  public TokenResponse(String token, T claims) {
    this.token = token;
    this.claims = claims;
  }

  public String getToken() {
    return token;
  }

  public T getClaims() {
    return claims;
  }
}
