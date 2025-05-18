package org.nsbm.dea.student_management_system.dao;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nsbm.dea.student_management_system.state.DB;
import org.nsbm.dea.student_management_system.state.Redis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.nsbm.dea.student_management_system.model.student.Performer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class StudentDAO {
  public static final Gson gson = new Gson();
  private static final Logger logger = Logger.getLogger(StudentDAO.class.getName());

  public static void create(int user_id, String name, String email, String address, String mobile_number)
      throws SQLException {
    final String REDIS_KEY = "nsbm:total_students";
    JedisPool pool = Redis.getPool();
    ExecutorService executor = Executors.newSingleThreadExecutor();

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

      executor.submit(() -> {
        try (Jedis jedis = pool.getResource()) {
          Long newCount = jedis.incr(REDIS_KEY);
          if (newCount == 1) {
            try (
                Connection connection2 = DB.getConnection();
                PreparedStatement statement2 = connection2.prepareStatement("SELECT COUNT(*) AS count FROM _student");
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

  public static int getTotalStudents() throws SQLException {
    final String REDIS_KEY = "nsbm:total_students";
    final String QUERY = "SELECT COUNT(*) AS count FROM _student";

    ExecutorService executor = Executors.newFixedThreadPool(2);
    Integer students = 0;

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
          students = redisResult;
        } else {
          Integer dbResult = dbFuture.get();
          executor.submit(() -> {
            try (Jedis jedis = pool.getResource()) {
              jedis.setex(REDIS_KEY, 2 * 24 * 60 * 60, String.format("%d", dbResult));
            }
          });
          students = dbResult;
        }
      } catch (Exception e) {
        throw new SQLException(e.getMessage());
      }

    } finally {
      executor.shutdown();
      pool.close();
    }

    return students;
  }

  public static List<Performer> getTopPerformers() throws SQLException {
    final String REDIS_KEY = "nsbm:top_performers";
    final String QUERY = "SELECT s.id AS subject_id, s.name AS subject_name, st.id AS student_id, st.name AS student_name, AVG(m.marks) AS average_marks FROM _subject s JOIN _examinations e ON s.id = e.subject_id JOIN _marks m ON e.id = m.examination_id JOIN _student st ON m.student_id = st.id GROUP BY s.id, s.name, st.id, st.name HAVING AVG(m.marks) = (SELECT MAX(avg_marks) FROM (SELECT AVG(m2.marks) AS avg_marks FROM _examinations e2 JOIN _marks m2 ON e2.id = m2.examination_id WHERE e2.subject_id = s.id GROUP BY m2.student_id) sub) ORDER BY s.id";

    ExecutorService executor = Executors.newFixedThreadPool(2);
    List<Performer> performers = new ArrayList<>();

    JedisPool pool = Redis.getPool();

    try {
      Callable<List<Performer>> redisTask = () -> {
        try (Jedis jedis = pool.getResource()) {
          String value = jedis.get(REDIS_KEY);
          if (value != null && !value.isEmpty()) {
            Type subjectListType = new TypeToken<List<Performer>>() {
            }.getType();
            return gson.fromJson(value, subjectListType);
          }
          return null;
        }
      };

      Callable<List<Performer>> dbTask = () -> {
        List<Performer> dbPerformers = new ArrayList<>();
        try (
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(QUERY);
            ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            Performer performer = new Performer(resultSet.getInt("subject_id"), resultSet.getInt("student_id"),
                resultSet.getDouble("average_marks"), resultSet.getString("subject_name"),
                resultSet.getString("student_name"));
            dbPerformers.add(performer);
          }
        }

        return dbPerformers;
      };

      Future<List<Performer>> redisFuture = executor.submit(redisTask);
      Future<List<Performer>> dbFuture = executor.submit(dbTask);

      try {
        List<Performer> redisResult = redisFuture.get();
        if (redisResult != null) {
          performers = redisResult;
        } else {
          List<Performer> dbResult = dbFuture.get();
          executor.submit(() -> {
            try (Jedis jedis = pool.getResource()) {
              jedis.setex(REDIS_KEY, 5 * 60, gson.toJson(dbResult));
            }
          });
          performers = dbResult;
        }
      } catch (Exception e) {
        throw new SQLException(e.getMessage());
      }

    } finally {
      executor.shutdown();
      pool.close();
    }

    return performers;
  }
}
