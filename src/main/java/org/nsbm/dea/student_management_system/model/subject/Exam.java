package org.nsbm.dea.student_management_system.model.subject;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Exam {
  private int id;
  private String name;
  private int subject_id;
  private String subject_name;
  private String date;

  public Exam() {
  }

  public Exam(int id, String name, int subject_id, String subject_name, long date) {
    this.id = id;
    this.name = name;
    this.subject_name = subject_name;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a")
        .withZone(ZoneId.of("Asia/Colombo"));
    this.date = formatter.format(Instant.ofEpochSecond(date));
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSubject_id() {
    return subject_id;
  }

  public void setSubject_id(int subject_id) {
    this.subject_id = subject_id;
  }

  public String getSubject_name() {
    return subject_name;
  }

  public void setSubject_name(String subject_name) {
    this.subject_name = subject_name;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
