package org.nsbm.dea.student_management_system.model.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class CreateStudentRequest {
  @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
  private String name;

  @Email(message = "Email must be valid")
  private String email;

  @Size(min = 3, max = 255, message = "Address must be between 3 and 255 characters")
  private String address;

  @Size(min = 3, max = 255, message = "Mobile number must be between 3 and 255 characters")
  private String mobile_number;

  public CreateStudentRequest() {
  }

  public CreateStudentRequest(String name, String email, String address, String mobile_number) {
    this.name = name;
    this.email = email;
    this.address = address;
    this.mobile_number = mobile_number;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getMobile_number() {
    return mobile_number;
  }

  public void setMobile_number(String mobile_number) {
    this.mobile_number = mobile_number;
  }
}
