package org.nsbm.dea.student_management_system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.nsbm.dea.student_management_system.state.DB;

public class SubjectDAO {
  public static void create(int user_id, String name, String slug, int total_sessions) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("INSERT INTO _subject (added_by, name, slug, total_sessions) VALUES (?, ?, ?, ?)")) {
        statement.setInt(1, user_id);
        statement.setString(2, name);
        statement.setString(3, slug);
        statement.setInt(4, total_sessions);

        statement.executeUpdate();
      }
    }
  }

  public static void enroll(int user_id, int student_id, int subject_id) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("INSERT INTO _student_subject (added_by, student_id, subject_id) VALUES (?, ?, ?)")) {
        statement.setInt(1, user_id);
        statement.setInt(2, student_id);
        statement.setInt(3, subject_id);

        statement.executeUpdate();
      }
    }
  }
}
