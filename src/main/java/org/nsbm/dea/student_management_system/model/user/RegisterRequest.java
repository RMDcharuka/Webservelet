package org.nsbm.dea.student_management_system.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import org.nsbm.dea.student_management_system.validation.annotations.ValidPassword;

public class RegisterRequest {
  @Email(message = "Email should be valid")
  private String email;

  @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
  private String name;

  @ValidPassword
  private String password;

  public RegisterRequest() {
  }

  public RegisterRequest(String email, String name, String password) {
    this.email = email;
    this.name = name;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
