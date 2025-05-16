package org.nsbm.dea.student_management_system.servlet.auth;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.nsbm.dea.student_management_system.error.AppError;
import org.nsbm.dea.student_management_system.model.http.Response;
import org.nsbm.dea.student_management_system.token.PrimaryClaims;
import org.nsbm.dea.student_management_system.token.TokenError;
import org.nsbm.dea.student_management_system.token.TokenResponse;
import org.nsbm.dea.student_management_system.token.types.Access;
import org.nsbm.dea.student_management_system.token.types.Refresh;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/auth/rotate")
public class Rotate extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      Optional<String> refreshToken = Optional.empty();
      Cookie[] cookies = request.getCookies();
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("dea_refresh")) {
          refreshToken = Optional.of(cookie.getValue());
          break;
        }
      }
      if (refreshToken.isEmpty()) {
        AppError.response(response, AppError.unauthorized("Refresh token is missing", null));
        return;
      }
      Refresh refresh = new Refresh();
      PrimaryClaims primaryClaims = new PrimaryClaims();
      var decodedRefreshToken = refresh.decode(refreshToken.get());
      var refreshTokenClaims = primaryClaims.getClaims(decodedRefreshToken);

      Access access = new Access(refreshTokenClaims.getSub());
      TokenResponse<PrimaryClaims> accessTokenResponse = access.refresh(refreshTokenClaims.getJti());

      response.setHeader("X-Access-Token", accessTokenResponse.getToken());
      new Response("success", null).toJson(response, HttpServletResponse.SC_OK);
    } catch (TokenError e) {
      AppError.response(response, AppError.fromTokenError(e));
      return;
    } catch (Exception e) {
      AppError.response(response, AppError.fromGenericError(e));
      return;
    }
  }
}
