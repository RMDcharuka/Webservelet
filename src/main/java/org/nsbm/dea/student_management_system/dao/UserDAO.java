package org.nsbm.dea.student_management_system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nsbm.dea.student_management_system.state.DB;
import org.nsbm.dea.student_management_system.state.Redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import org.nsbm.dea.student_management_system.model.user.User;

public class UserDAO {
  private static User getUserFromResultSet(ResultSet resultSet) throws SQLException {
    return new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("name"),
        resultSet.getString("photo_url"), resultSet.getString("password"));
  }

  public static void create(String email, String name, String password) throws SQLException {
    final String REDIS_KEY = "nsbm:total_users";
    final Logger logger = Logger.getLogger(SubjectDAO.class.getName());
    JedisPool pool = Redis.getPool();
    ExecutorService executor = Executors.newSingleThreadExecutor();

    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("INSERT INTO _user (email, name, password, photo_url) VALUES (?, ?, ?, ?)")) {
        statement.setString(1, email);
        statement.setString(2, name);
        statement.setString(3, password);
        statement.setString(4, String.format("https://api.dicebear.com/9.x/pixel-art/svg?seed=%s", name));

        statement.executeUpdate();
      }

      executor.submit(() -> {
        try (Jedis jedis = pool.getResource()) {
          Long newCount = jedis.incr(REDIS_KEY);
          if (newCount == 1) {
            try (
                Connection connection2 = DB.getConnection();
                PreparedStatement statement2 = connection2.prepareStatement("SELECT COUNT(*) AS count FROM _user");
                ResultSet resultSet = statement2.executeQuery()) {
              if (resultSet.next()) {
                int count = resultSet.getInt("count");
                jedis.setex(REDIS_KEY, 2 * 24 * 60 * 60, String.format("%d", count));
              }
            } catch (SQLException e) {
              logger.log(Level.SEVERE, e.getMessage());
            }
          } else {
            jedis.expire(REDIS_KEY, 2 * 24 * 60 * 60);
          }
        } catch (Exception e) {
          logger.log(Level.SEVERE, e.getMessage());
        }
      });
    } finally {
      executor.shutdown();
      pool.close();
    }
  }

  public static int getTotalUsers() throws SQLException {
    final String REDIS_KEY = "nsbm:total_users";
    final String QUERY = "SELECT COUNT(*) AS count FROM _user";

    ExecutorService executor = Executors.newFixedThreadPool(2);
    Integer users = 0;

    JedisPool pool = Redis.getPool();

    try {
      Callable<Integer> redisTask = () -> {
        try (Jedis jedis = pool.getResource()) {
          String value = jedis.get(REDIS_KEY);
          if (value != null && !value.isEmpty()) {
            try {
              return Integer.parseInt(value);
            } catch (Exception e) {
              return null;
            }
          }
          return null;
        }
      };

      Callable<Integer> dbTask = () -> {
        try (
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(QUERY);
            ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            try {
              return resultSet.getInt("count");
            } catch (Exception e) {
              return 0;
            }
          }
        }
        return 0;
      };

      Future<Integer> redisFuture = executor.submit(redisTask);
      Future<Integer> dbFuture = executor.submit(dbTask);

      try {
        Integer redisResult = redisFuture.get();
        if (redisResult != null) {
          users = redisResult;
        } else {
          Integer dbResult = dbFuture.get();
          executor.submit(() -> {
            try (Jedis jedis = pool.getResource()) {
              jedis.setex(REDIS_KEY, 2 * 24 * 60 * 60, String.format("%d", dbResult));
            }
          });
          users = dbResult;
        }
      } catch (Exception e) {
        throw new SQLException(e.getMessage());
      }

    } finally {
      executor.shutdown();
      pool.close();
    }

    return users;
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

  public static Optional<User> getByID(int id) throws SQLException {
    String query = "SELECT * FROM _user WHERE id = ? LIMIT 1";

    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, id);
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
