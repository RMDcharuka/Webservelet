package org.nsbm.dea.student_management_system.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import org.nsbm.dea.student_management_system.validation.annotations.ValidPassword;

public class User {
  @Min(value = 1, message = "ID must be greater than 0")
  private int id;

  @Email(message = "Email should be valid")
  private String email;

  @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
  private String name;

  @Size(min = 5, message = "PhotoURL must be a valid URL")
  private String photoURL;

  @ValidPassword
  private String password;

  public User() {
  }

  public User(int id, String email, String name, String photoURL, String password) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.photoURL = photoURL;
    this.password = password;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public String getPhotoURL() {
    return photoURL;
  }

  public void setPhotoURL(String photoURL) {
    this.photoURL = photoURL;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserDetails toUserDetails() {
    return new UserDetails(this.getId(), this.getEmail(), this.getName(), this.getPhotoURL());
  }
}
