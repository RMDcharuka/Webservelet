package org.nsbm.dea.student_management_system.token;

abstract class Token<T extends Claims> {
  abstract String getSecretKey();

  abstract long getExp();
}
