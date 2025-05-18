package org.nsbm.dea.student_management_system.filters;

import java.io.IOException;
import java.security.cert.PKIXRevocationChecker.Option;
import java.util.Arrays;
import java.util.List;
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

@WebFilter(urlPatterns = { "/dashboard/*", "/record/*", "/attendance/*", "/login", "/signup" }, servletNames = {
    "index" })
public class Guard implements Filter {
  private static final Logger logger = Logger.getLogger(Guard.class.getName());
  private static final List<String> PROTECTED_PATHS = List.of("/dashboard", "/record", "/attendance");

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    final String path = req.getRequestURI();

    if (!isAuthenticated(req)) {
      if (PROTECTED_PATHS.stream().anyMatch(path::startsWith)) {
        res.sendRedirect(req.getContextPath() + "/");
        return;
      }
    } else {
      if (path.equals("/") || path.equals("/login") || path.equals("/signup")) {
        res.sendRedirect(req.getContextPath() + "/dashboard");
        return;
      }
    }

    chain.doFilter(request, response);
  }

  private boolean isAuthenticated(HttpServletRequest req) {
    Optional<Cookie> session = Optional.ofNullable(req.getCookies())
        .flatMap(cookies -> Arrays.stream(cookies)
            .filter(cookie -> "dea_session".equals(cookie.getName()))
            .findFirst());
    if (session.isEmpty()) {
      return false;
    }
    try {
      Session s = new Session();
      s.decode(session.get().getValue());
      return true;
    } catch (TokenError e) {
      if (e.getKind() != TokenError.ErrorKind.VALIDATION_FAILED) {
        logger.log(Level.SEVERE, e.getMessage());
      }
      return false;
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      return false;
    }
  }
}
