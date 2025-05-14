package org.nsbm.dea.student_management_system.token;

public enum TokenType {
  ACCESS("access_token"),
  REFRESH("refresh_token"),
  SESSION("session_token");

  private final String value;

  TokenType(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }

  public String getKey(String jti) {
    return String.format("%s:%s:%s", "nsbm", this, jti);
  }
}
