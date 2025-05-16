package org.nsbm.dea.student_management_system.filters;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nsbm.dea.student_management_system.dao.UserDAO;
import org.nsbm.dea.student_management_system.model.user.UserDetails;
import org.nsbm.dea.student_management_system.token.ExtendedClaims;
import org.nsbm.dea.student_management_system.token.PrimaryClaims;
import org.nsbm.dea.student_management_system.token.TokenError;
import org.nsbm.dea.student_management_system.token.types.Refresh;
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
public class Auth implements Filter {
  private static final Logger logger = Logger.getLogger(Auth.class.getName());

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    Optional<String> refreshToken = Optional.empty();
    Optional<String> sessionToken = Optional.empty();

    Cookie[] cookies = req.getCookies();
    if (cookies == null || cookies.length == 0) {
      res.sendRedirect(req.getContextPath() + "/login");
      return;
    }
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("dea_refresh")) {
        refreshToken = Optional.of(cookie.getValue());
      }
      if (cookie.getName().equals("dea_session")) {
        sessionToken = Optional.of(cookie.getValue());
      }
    }

    if (refreshToken.isEmpty()) {
      res.sendRedirect(req.getContextPath() + "/login");
      return;
    }

    Refresh refresh = new Refresh();
    Session session = new Session();
    PrimaryClaims primaryClaims = new PrimaryClaims();
    ExtendedClaims extendedClaims = new ExtendedClaims();
    UserDetails userDetails = new UserDetails();

    try {
      var decodedRefreshToken = refresh.decode(refreshToken.get());
      var refreshTokenClaims = primaryClaims.getClaims(decodedRefreshToken);

      if (sessionToken.isEmpty()) {
        var user = UserDAO.getByID(refreshTokenClaims.getSub());
        if (user.isEmpty()) {
          res.sendRedirect(req.getContextPath() + "/login");
          return;
        }
        userDetails = user.get().toUserDetails();
      } else {
        var decodedSessionToken = session.decode(sessionToken.get());
        ExtendedClaims sessionTokenClaims = extendedClaims.getClaims(decodedSessionToken);
        userDetails = sessionTokenClaims.getUserDetails();
      }
    } catch (TokenError e) {
      if (e.getKind() == TokenError.ErrorKind.VALIDATION_FAILED) {
        res.sendRedirect(req.getContextPath() + "/login");
        return;
      }
      logger.log(Level.SEVERE, e.getMessage());
      throw new ServletException("Error while checking authentication");
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      throw new ServletException("Something went wrong");
    }

    req.setAttribute("userDetails", userDetails);
    chain.doFilter(request, response);
  }
}
