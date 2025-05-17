package org.nsbm.dea.student_management_system.model.subject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SubjectDetails {
  private int id;
  private String name;
  private String slug;
  private int total_sessions;
  private int added_by;

  public SubjectDetails() {

  }

  public SubjectDetails(int id, String name, String slug, int total_sessions, int added_by) {
    this.id = id;
    this.name = name;
    this.slug = slug;
    this.total_sessions = total_sessions;
    this.added_by = added_by;
  }

  public SubjectDetails(int id, String name, String slug) {
    this.id = id;
    this.name = name;
    this.slug = slug;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    if (name == null) {
      throw new IllegalArgumentException();
    }
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSlug() {
    if (slug == null) {
      throw new IllegalArgumentException();
    }
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public int getTotal_sessions() {
    if (total_sessions <= 0) {
      throw new IllegalArgumentException();
    }
    return total_sessions;
  }

  public void setTotal_sessions(int total_sessions) {
    this.total_sessions = total_sessions;
  }

  public int getAdded_by() {
    if (added_by <= 0) {
      throw new IllegalArgumentException();
    }
    return added_by;
  }

  public void setAdded_by(int added_by) {
    this.added_by = added_by;
  }

  public String toJsonString() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(this);
  }
}
