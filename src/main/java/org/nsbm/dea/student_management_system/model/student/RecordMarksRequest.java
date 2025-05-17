package org.nsbm.dea.student_management_system.model.student;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class RecordMarksRequest {
  @Min(value = 1, message = "Examintion ID must be valid")
  private int examination_id;

  @Min(value = 1, message = "Student ID must be valid")
  private int student_id;

  @Min(value = 0, message = "Marks must be valid")
  @Max(value = 100, message = "Marks must be valid")
  private float marks;

  public RecordMarksRequest() {
  }

  public RecordMarksRequest(int examination_id, int student_id, float marks) {
    this.examination_id = examination_id;
    this.student_id = student_id;
    this.marks = marks;
  }

  public int getExamination_id() {
    return examination_id;
  }

  public void setExamination_id(int examination_id) {
    this.examination_id = examination_id;
  }

  public int getStudent_id() {
    return student_id;
  }

  public void setStudent_id(int student_id) {
    this.student_id = student_id;
  }

  public float getMarks() {
    return marks;
  }

  public void setMarks(float marks) {
    this.marks = marks;
  }
}
