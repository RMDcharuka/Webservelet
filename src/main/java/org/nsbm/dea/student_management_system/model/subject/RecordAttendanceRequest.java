package org.nsbm.dea.student_management_system.model.subject;

import jakarta.validation.constraints.Min;

public class RecordAttendanceRequest {
  @Min(value = 1, message = "Student ID must be valid")
  private int student_id;

  @Min(value = 1, message = "Subject ID must be valid")
  private int subject_id;

  public RecordAttendanceRequest() {
  }

  public RecordAttendanceRequest(int student_id, int subject_id) {
    this.subject_id = subject_id;
    this.student_id = student_id;
  }

  public int getStudentId() {
    return student_id;
  }

  public void setStudentId(int student_id) {
    this.student_id = student_id;
  }

  public int getSubjectId() {
    return subject_id;
  }

  public void setSubjectId(int subject_id) {
    this.subject_id = subject_id;
  }
}
