package org.nsbm.dea.student_management_system.config;

import io.github.cdimascio.dotenv.Dotenv;

public class Env {
  private static final Dotenv dotenv = Dotenv.load();

  public static String getDBURL() {
    return dotenv.get("DB_URL");
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
}
