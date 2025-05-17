package org.nsbm.dea.student_management_system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.nsbm.dea.student_management_system.state.DB;

public class StudentDAO {
  public static void create(int user_id, String name, String email, String address, String mobile_number)
      throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement(
              "INSERT INTO _student (enrolled_by, name, email, address, mobile_number) VALUES (?, ?, ?, ?, ?)")) {
        statement.setInt(1, user_id);
        statement.setString(2, name);
        statement.setString(3, email);
        statement.setString(4, address);
        statement.setString(5, mobile_number);

        statement.executeUpdate();
      }
    }
  }
}
