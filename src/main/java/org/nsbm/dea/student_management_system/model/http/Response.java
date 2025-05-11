package org.nsbm.dea.student_management_system.model.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;

public class Response {
  private static final Gson gson = new GsonBuilder().create();

  private final String message;
  private final Object payload;

  public Response(String message, Object payload) {
    this.message = message;
    this.payload = payload;
  }

  public void toJson(HttpServletResponse response, int status) {
    response.setContentType("application/json");
    response.setStatus(status);

    Response res = new Response(message, payload);
    try {
      response.getWriter().write(gson.toJson(res));
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
