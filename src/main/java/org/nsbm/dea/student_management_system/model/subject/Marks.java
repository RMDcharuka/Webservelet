package org.nsbm.dea.student_management_system.model.subject;

public class Marks {
  private int studentId;
  private String studentName;
  private String examinationName;
  private float examinationMarks;

  public Marks() {
  }

  public Marks(int studentId, String studentName, String examinationName, float examinationMarks) {
    this.studentId = studentId;
    this.studentName = studentName;
    this.examinationName = examinationName;
    this.examinationMarks = examinationMarks;
  }

  public int getStudentId() {
    return studentId;
  }

  public void setStudentId(int studentId) {
    this.studentId = studentId;
  }

  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public String getExaminationName() {
    return examinationName;
  }

  public void setExaminationName(String examinationName) {
    this.examinationName = examinationName;
  }

  public float getExaminationMarks() {
    return examinationMarks;
  }

  public void setExaminationMarks(float examinationMarks) {
    this.examinationMarks = examinationMarks;
  }
}
