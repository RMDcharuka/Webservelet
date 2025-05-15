package org.nsbm.dea.student_management_system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.nsbm.dea.student_management_system.state.DB;
import org.nsbm.dea.student_management_system.model.user.User;

public class UserDAO {
  private static User getUserFromResultSet(ResultSet resultSet) throws SQLException {
    return new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("name"),
        resultSet.getString("photo_url"), resultSet.getString("password"));
  }

  public static void create(String email, String name, String password) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("INSERT INTO _user (email, name, password, photo_url) VALUES (?, ?, ?, ?)")) {
        statement.setString(1, email);
        statement.setString(2, name);
        statement.setString(3, password);
        statement.setString(4, String.format("https://api.dicebear.com/9.x/pixel-art/svg?seed=%s", name));

        statement.executeUpdate();
      }
    }
  }

  public static Optional<User> getByEmail(String email) throws SQLException {
    String query = "SELECT * FROM _user WHERE email = ? LIMIT 1";

    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, email);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            return Optional.of(UserDAO.getUserFromResultSet(resultSet));
          } else {
            return Optional.empty();
          }
        }
      }
    }
  }
}
