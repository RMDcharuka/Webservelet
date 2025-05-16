package org.nsbm.dea.student_management_system.servlet.auth;

import java.io.IOException;
import java.util.Optional;

import org.nsbm.dea.student_management_system.config.Env;
import org.nsbm.dea.student_management_system.enums.Environment;
import org.nsbm.dea.student_management_system.error.AppError;
import org.nsbm.dea.student_management_system.token.PrimaryClaims;
import org.nsbm.dea.student_management_system.token.TokenError;
import org.nsbm.dea.student_management_system.token.types.Refresh;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/auth/logout")
public class Logout extends HttpServlet {
  @Override
  public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        this.removeCookies(response);
        response.sendRedirect(request.getContextPath() + "/login");
        return;
      }

      Refresh refresh = new Refresh();
      var decodedRefreshToken = refresh.decode(refreshToken.get());
      PrimaryClaims primaryClaims = new PrimaryClaims();
      var refreshTokenClaims = primaryClaims.getClaims(decodedRefreshToken);
      refresh.delete(refreshTokenClaims.getJti());

      this.removeCookies(response);
      response.sendRedirect(request.getContextPath() + "/login");
      return;
    } catch (TokenError e) {
      if (e.getKind() == TokenError.ErrorKind.VALIDATION_FAILED) {
        this.removeCookies(response);
        response.sendRedirect(request.getContextPath() + "/login");
        return;
      }
      AppError.response(response, AppError.fromTokenError(e));
      return;
    } catch (Exception e) {
      AppError.response(response, AppError.fromGenericError(e));
      return;
    }
  }

  private void removeCookies(HttpServletResponse response) {
    Cookie refreshCookie = new Cookie("dea_refresh", "");
    refreshCookie.setMaxAge(0);
    refreshCookie.setPath("/");
    refreshCookie.setHttpOnly(true);
    refreshCookie.setSecure(!Environment.isDev());
    refreshCookie.setDomain(Env.getDomain());

    Cookie sessionCookie = new Cookie("dea_session", "");
    sessionCookie.setMaxAge(0);
    sessionCookie.setPath("/");
    sessionCookie.setHttpOnly(false);
    sessionCookie.setSecure(!Environment.isDev());
    sessionCookie.setDomain(Env.getDomain());

    response.addCookie(refreshCookie);
    response.addCookie(sessionCookie);
  }
}
