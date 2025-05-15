package org.nsbm.dea.student_management_system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.nsbm.dea.student_management_system.state.DB;

public class UserDAO {
  public void create(String email, String name, String password) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("INSERT INTO user (email, name, passsword, photo_url) VALUES (?, ?, ?, ?)")) {
        statement.setString(1, email);
        statement.setString(2, name);
        statement.setString(3, password);
        statement.setString(4, String.format("https://api.dicebear.com/9.x/pixel-art/svg?seed=%s", name));

        statement.executeUpdate();
      }
    }
  }
}
