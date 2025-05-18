package org.nsbm.dea.student_management_system.model.student;

public class Performer {
  private int subject_id;
  private int student_id;
  private double average_marks;
  private String subject_name;
  private String student_name;

  public Performer() {
  }

  public Performer(int subject_id, int student_id, double average_marks, String subject_name, String student_name) {
    this.subject_id = subject_id;
    this.student_id = student_id;
    this.average_marks = average_marks;
    this.subject_name = subject_name;
    this.student_name = student_name;
  }

  public int getSubject_id() {
    return subject_id;
  }

  public void setSubject_id(int subject_id) {
    this.subject_id = subject_id;
  }

  public int getStudent_id() {
    return student_id;
  }

  public void setStudent_id(int student_id) {
    this.student_id = student_id;
  }

  public double getAverage_marks() {
    return average_marks;
  }

  public void setAverage_marks(double average_marks) {
    this.average_marks = average_marks;
  }

  public String getSubject_name() {
    return subject_name;
  }

  public void setSubject_name(String subject_name) {
    this.subject_name = subject_name;
  }

  public String getStudent_name() {
    return student_name;
  }

  public void setStudent_name(String student_name) {
    this.student_name = student_name;
  }

}
