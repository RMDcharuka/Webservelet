package org.nsbm.dea.student_management_system.model.subject;

import org.nsbm.dea.student_management_system.validation.annotations.ValidSlug;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class CreateSubjectRequest {
  @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
  private String name;

  @ValidSlug
  private String slug;

  @Min(value = 1, message = "Total sessions must be at least 1")
  private int total_sessions;

  public CreateSubjectRequest() {
  }

  public CreateSubjectRequest(String name, String slug, int total_sessions) {
    this.name = name;
    this.slug = slug.toLowerCase();
    this.total_sessions = total_sessions;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug.toLowerCase();
  }

  public int getTotalSessions() {
    return total_sessions;
  }

  public void setTotalSessions(int total_sessions) {
    this.total_sessions = total_sessions;
  }
}
