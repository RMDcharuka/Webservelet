package org.nsbm.dea.student_management_system.token;

import java.util.Date;
import java.util.Optional;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface Claims {
  Claims newInstance();

  int getSub();

  String getJti();

  String getRjti();

  Date getIat();

  Date getExp();

  Date getNbf();

  Optional<String> getCustom();

  String toJsonString() throws TokenError;

  Claims getClaims(DecodedJWT jwt) throws TokenError;
}
