package org.nsbm.dea.student_management_system.filters;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nsbm.dea.student_management_system.token.TokenError;
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

@WebFilter(urlPatterns = { "/dashboard/*" })
public class Guard implements Filter {
  private static final Logger logger = Logger.getLogger(Auth.class.getName());

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    Optional<String> sessionToken = Optional.empty();

    Cookie[] cookies = req.getCookies();
    if (cookies != null && cookies.length > 0) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("dea_session")) {
          sessionToken = Optional.of(cookie.getValue());
          break;
        }
      }
      if (!sessionToken.isEmpty()) {
        Session session = new Session();
        try {
          session.decode(sessionToken.get());
          chain.doFilter(request, response);
          return;
        } catch (TokenError e) {
          if (e.getKind() != TokenError.ErrorKind.VALIDATION_FAILED) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new ServletException();
          }
        }
      }
    }

    res.sendRedirect(req.getContextPath() + "/");
    return;
  }
}
