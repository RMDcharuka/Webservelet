package org.nsbm.dea.student_management_system.token;

import java.util.Optional;

public interface Claims {
  int getSub();

  String getJti();

  String getRjti();

  long getIat();

  long getExp();

  long getNbf();

  Optional<String> getCustom();
}
