package org.nsbm.dea.student_management_system.state;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.nsbm.dea.student_management_system.config.Env;

public class DB {
  private static Logger logger = Logger.getLogger(DB.class.getName());

  static {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      logger.log(Level.SEVERE, "PostgreSQL JDBC Driver not found", e);
    }
  }

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
        String.format("jdbc:postgresql://%s:%s/%s?sslmode=require",
            Env.getDbHost(),
            Env.getDbPort(),
            Env.getDbName()),
        Env.getDbUser(),
        Env.getDbPassword());
  }

  public static void closeConnection(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        logger.log(Level.SEVERE, "Error closing the database connection", e);
      }
    }
  }
}
