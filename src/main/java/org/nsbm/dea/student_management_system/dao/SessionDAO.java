package org.nsbm.dea.student_management_system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.nsbm.dea.student_management_system.state.DB;

public class SessionDAO {
  public static void create(String id, int user_id, String ip, long login_at, long exp) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("INSERT INTO _session (id, user_id, ip_address, login_at, exp) VALUES (?, ?, ?, ?, ?)")) {
        statement.setString(1, id);
        statement.setInt(2, user_id);
        statement.setString(3, ip);
        statement.setLong(4, login_at);
        statement.setLong(5, exp);

        statement.executeUpdate();
      }
    }
  }
}
