package org.nsbm.dea.student_management_system.dao;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.nsbm.dea.student_management_system.model.subject.Attendance;
import org.nsbm.dea.student_management_system.model.subject.SubjectDetails;
import org.nsbm.dea.student_management_system.state.DB;
import org.nsbm.dea.student_management_system.state.Redis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SubjectDAO {
  public static final Gson gson = new Gson();

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

  public static void recordAttendance(int user_id, int student_id, int subject_id) throws SQLException {
    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection
          .prepareStatement("INSERT INTO _attendance (recorded_by, student_id, subject_id) VALUES (?, ?, ?)")) {
        statement.setInt(1, user_id);
        statement.setInt(2, student_id);
        statement.setInt(3, subject_id);

        statement.executeUpdate();
      }
    }
  }

  public static List<SubjectDetails> getNamesAndSlugs() throws SQLException {
    final String REDIS_KEY = "nsbm:subject_names_and_slugs";
    final String QUERY = "SELECT id, name, slug FROM _subject";

    ExecutorService executor = Executors.newFixedThreadPool(2);
    List<SubjectDetails> subjects = new ArrayList<>();

    JedisPool pool = Redis.getPool();

    try {
      Callable<List<SubjectDetails>> redisTask = () -> {
        try (Jedis jedis = pool.getResource()) {
          String value = jedis.get(REDIS_KEY);
          if (value != null && !value.isEmpty()) {
            Type subjectListType = new TypeToken<List<SubjectDetails>>() {
            }.getType();
            return gson.fromJson(value, subjectListType);
          }
          return null;
        }
      };

      Callable<List<SubjectDetails>> dbTask = () -> {
        List<SubjectDetails> dbSubjects = new ArrayList<>();
        try (
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(QUERY);
            ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            SubjectDetails subject = new SubjectDetails(resultSet.getInt("id"), resultSet.getString("name"),
                resultSet.getString("slug"));
            dbSubjects.add(subject);
          }
        }

        return dbSubjects;
      };

      Future<List<SubjectDetails>> redisFuture = executor.submit(redisTask);
      Future<List<SubjectDetails>> dbFuture = executor.submit(dbTask);

      try {
        List<SubjectDetails> redisResult = redisFuture.get();
        if (redisResult != null) {
          subjects = redisResult;
        } else {
          List<SubjectDetails> dbResult = dbFuture.get();
          executor.submit(() -> {
            try (Jedis jedis = pool.getResource()) {
              jedis.setex(REDIS_KEY, 5 * 60, gson.toJson(dbResult));
            }
          });
          subjects = dbResult;
        }
      } catch (Exception e) {
        throw new SQLException(e.getMessage());
      }

    } finally {
      executor.shutdown();
      pool.close();
    }

    return subjects;
  }

  public static List<Attendance> getAttendanceForSubject(String slug) throws SQLException {
    String query = "SELECT s.id AS student_id, s.name AS student_name, COUNT(DISTINCT DATE(a.arrived_at)) AS total_present_sessions, sub.total_sessions AS total_sessions FROM _student s JOIN _attendance a ON s.id = a.student_id JOIN _subject sub ON a.subject_id = sub.id WHERE sub.slug = ? GROUP BY s.id, s.name, sub.total_sessions";

    List<Attendance> attendanceList = new ArrayList<>();

    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, slug);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            Attendance attendance = new Attendance(resultSet.getInt("student_id"), resultSet.getString("student_name"),
                resultSet.getInt("total_sessions"), resultSet.getInt("total_present_sessions"));
            attendanceList.add(attendance);
          }
        }
      }
    }

    return attendanceList;
  }

  public static Optional<String> getSubjectBySlug(String slug) throws SQLException {
    String query = "SELECT name FROM _subject WHERE slug = ?";

    try (Connection connection = DB.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, slug);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            return Optional.of(resultSet.getString("name"));
          }
        }
      }
    }

    return Optional.empty();
  }
}
