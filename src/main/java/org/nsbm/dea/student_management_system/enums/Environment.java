package org.nsbm.dea.student_management_system.enums;

import org.nsbm.dea.student_management_system.config.Env;

public enum Environment {
  PRODUCTION("prd"),
  STAGING("stg"),
  DEVELOPMENT("dev");

  private final String value;

  Environment(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Environment fromValue(String value) {
    for (Environment env : Environment.values()) {
      if (env.getValue().equalsIgnoreCase(value)) {
        return env;
      }
    }
    throw new IllegalArgumentException("Invalid environment value: " + value);
  }

  public static boolean isPrd() {
    String env = Env.getEnv();
    return env != null && env.equalsIgnoreCase(PRODUCTION.getValue());
  }

  public static boolean isStg() {
    String env = Env.getEnv();
    return env != null && env.equalsIgnoreCase(STAGING.getValue());
  }

  public static boolean isDev() {
    String env = Env.getEnv();
    return env != null && env.equalsIgnoreCase(DEVELOPMENT.getValue());
  }
}
