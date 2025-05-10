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
}
