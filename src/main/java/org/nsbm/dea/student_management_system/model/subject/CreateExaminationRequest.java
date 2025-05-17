package org.nsbm.dea.student_management_system.model.subject;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class CreateExaminationRequest {
  @Min(value = 1, message = "Subject ID must be at least 1")
  private int subject_id;

  @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
  private String name;

  @Min(value = 1, message = "Date must be valid")
  private long date;

  public CreateExaminationRequest() {
  }

  public CreateExaminationRequest(int subject_id, String name, long date) {
    this.subject_id = subject_id;
    this.name = name;
    this.date = date;
  }

  public int getSubject_id() {
    return subject_id;
  }

  public void setSubject_id(int subject_id) {
    this.subject_id = subject_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }
}
