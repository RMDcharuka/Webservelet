package org.nsbm.dea.student_management_system.token;

import java.util.Optional;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface Claims {
  Claims newInstance();

  int getSub();

  String getJti();

  String getRjti();

  long getIat();

  long getExp();

  long getNbf();

  Optional<String> getCustom();

  String toJsonString() throws TokenError;

  Claims getClaims(DecodedJWT jwt) throws TokenError;
}
