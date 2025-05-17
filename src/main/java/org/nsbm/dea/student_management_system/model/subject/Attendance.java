package org.nsbm.dea.student_management_system.model.subject;

public class Attendance {
  private int studentId;
  private String studentName;
  private int subjectTotalSessions;
  private int presentSessions;

  public Attendance() {
  }

  public Attendance(int studentId, String studentName, int subjectTotalSessions, int presentSessions) {
    this.studentId = studentId;
    this.studentName = studentName;
    this.subjectTotalSessions = subjectTotalSessions;
    this.presentSessions = presentSessions;
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

  public int getSubjectTotalSessions() {
    return subjectTotalSessions;
  }

  public void setSubjectTotalSessions(int subjectTotalSessions) {
    this.subjectTotalSessions = subjectTotalSessions;
  }

  public int getPresentSessions() {
    return presentSessions;
  }

  public void setPresentSessions(int presentSessions) {
    this.presentSessions = presentSessions;
  }

  public int getAbsentSessions() {
    return subjectTotalSessions - presentSessions;
  }

  public float getAttendancePercentage() {
    if (subjectTotalSessions == 0) {
      return 0;
    }
    return ((float) presentSessions / subjectTotalSessions) * 100;
  }
}
