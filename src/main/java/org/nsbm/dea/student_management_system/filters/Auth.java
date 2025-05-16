package org.nsbm.dea.student_management_system.filters;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nsbm.dea.student_management_system.dao.UserDAO;
import org.nsbm.dea.student_management_system.error.AppError;
import org.nsbm.dea.student_management_system.model.user.UserDetails;
import org.nsbm.dea.student_management_system.token.ExtendedClaims;
import org.nsbm.dea.student_management_system.token.PrimaryClaims;
import org.nsbm.dea.student_management_system.token.TokenError;
import org.nsbm.dea.student_management_system.token.TokenType;
import org.nsbm.dea.student_management_system.token.types.Access;
import org.nsbm.dea.student_management_system.token.types.Session;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = { "/api/subject/*" })
public class Auth implements Filter {
  private static final Logger logger = Logger.getLogger(Auth.class.getName());

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    String authorization = req.getHeader("Authorization");
    if (authorization == null || !authorization.startsWith("Bearer ")) {
      AppError.response(res, AppError.unauthorized("You are not authorized to perform this operation", null));
      return;
    }
    String token = authorization.substring(7);

    Access access = new Access();
    PrimaryClaims primaryClaims = new PrimaryClaims();

    UserDetails userDetails = new UserDetails();

    try {
      access.verify(token, TokenType.ACCESS);

      Cookie[] cookies = req.getCookies();
      if (cookies == null || cookies.length == 0) {
        AppError.response(res, AppError.unauthorized("You are not authorized to perform this operation", null));
        return;
      }
      Optional<String> sessionToken = Optional.empty();
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("dea_session")) {
          sessionToken = Optional.of(cookie.getValue());
          break;
        }
      }
      if (sessionToken.isEmpty()) {
        AppError.response(res, AppError.unauthorized("You are not authorized to perform this operation", null));
        return;
      }

      var decodedSessionToken = new Session().decode(sessionToken.get());
      var sessionTokenClaims = new ExtendedClaims().getClaims(decodedSessionToken);
      userDetails = sessionTokenClaims.getUserDetails();
    } catch (TokenError e) {
      AppError.response(res, AppError.fromTokenError(e));
      return;
    }

    req.setAttribute("userDetails", userDetails);
    chain.doFilter(request, response);
  }
}
