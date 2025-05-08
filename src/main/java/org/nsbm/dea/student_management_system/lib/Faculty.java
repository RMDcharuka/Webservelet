package org.nsbm.dea.student_management_system.lib;

import java.util.Optional;

public class Faculty {
  public static final String[][] FACULTIES = {
      { "cs", "Computer Science" },
      { "it", "Information Technology" },
      { "se", "Software Engineering" },
      { "ds", "Data Science" },
      { "ce", "Civil Engineering" },
      { "me", "Mechanical Engineering" },
      { "ee", "Electrical Engineering" },
      { "be", "Biomedical Engineering" },
      { "ar", "Architecture" },
      { "ba", "Business Administration" }
  };

  public static Optional<String> getFacultyName(String facultyCode) {
    for (String[] faculty : FACULTIES) {
      if (faculty[0].equalsIgnoreCase(facultyCode)) {
        return Optional.of(faculty[1]);
      }
    }
    return Optional.empty();
  }

  public static Optional<String> getFacultyCode(String facultyName) {
    for (String[] faculty : FACULTIES) {
      if (faculty[1].equalsIgnoreCase(facultyName)) {
        return Optional.of(faculty[0]);
      }
    }
    return Optional.empty();
  }

  public static String[] getFacultyCodes() {
    String[] facultyCodes = new String[FACULTIES.length];
    for (int i = 0; i < FACULTIES.length; i++) {
      facultyCodes[i] = FACULTIES[i][0];
    }
    return facultyCodes;
  }

  public static String[] getFacultyNames() {
    String[] facultyNames = new String[FACULTIES.length];
    for (int i = 0; i < FACULTIES.length; i++) {
      facultyNames[i] = FACULTIES[i][1];
    }
    return facultyNames;
  }
}
