package org.nsbm.dea.student_management_system.config;

import io.github.cdimascio.dotenv.Dotenv;

public class Env {
  private static final Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().systemProperties()
      .load();

  public static String getDbHost() {
    return dotenv.get("DB_HOST");
  }

  public static String getDbPort() {
    return dotenv.get("DB_PORT");
  }

  public static String getDbName() {
    return dotenv.get("DB_NAME");
  }

  public static String getDbUser() {
    return dotenv.get("DB_USER");
  }

  public static String getDbPassword() {
    return dotenv.get("DB_PASSWORD");
  }

  public static String getRedisURL() {
    return dotenv.get("REDIS_URL");
  }

  public static String getAccessTokenSecret() {
    return dotenv.get("ACCESS_TOKEN_SECRET");
  }

  public static String getRefreshTokenSecret() {
    return dotenv.get("REFRESH_TOKEN_SECRET");
  }

  public static String getSessionTokenSecret() {
    return dotenv.get("SESSION_TOKEN_SECRET");
  }

  public static long getAccessTokenExpiration() {
    return Long.parseLong(dotenv.get("ACCESS_EXPIRES_IN"));
  }

  public static long getRefreshTokenExpiration() {
    return Long.parseLong(dotenv.get("REFRESH_EXPIRES_IN"));
  }

  public static long getSessionTokenExpiration() {
    return Long.parseLong(dotenv.get("SESSION_EXPIRES_IN"));
  }

  public static String getEnv() {
    return dotenv.get("ENV");
  }

  public static String getDomain() {
    return dotenv.get("DOMAIN");
  }
}
