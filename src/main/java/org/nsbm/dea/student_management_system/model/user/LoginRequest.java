package org.nsbm.dea.student_management_system.model.user;

import org.nsbm.dea.student_management_system.validation.annotations.ValidPassword;

import jakarta.validation.constraints.Email;

public class LoginRequest {
  @Email(message = "Email should be valid")
  private String email;

  @ValidPassword
  private String password;

  public LoginRequest() {
  }

  public LoginRequest(String email, String name, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
