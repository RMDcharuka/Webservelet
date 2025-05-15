package org.nsbm.dea.student_management_system.servlet.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nsbm.dea.student_management_system.config.Env;
import org.nsbm.dea.student_management_system.dao.UserDAO;
import org.nsbm.dea.student_management_system.dao.SessionDAO;
import org.nsbm.dea.student_management_system.enums.Environment;
import org.nsbm.dea.student_management_system.error.AppError;
import org.nsbm.dea.student_management_system.model.http.Response;
import org.nsbm.dea.student_management_system.model.user.LoginRequest;
import org.nsbm.dea.student_management_system.model.user.User;
import org.nsbm.dea.student_management_system.token.ExtendedClaims;
import org.nsbm.dea.student_management_system.token.PrimaryClaims;
import org.nsbm.dea.student_management_system.token.Claims;
import org.nsbm.dea.student_management_system.token.TokenError;
import org.nsbm.dea.student_management_system.token.TokenParams;
import org.nsbm.dea.student_management_system.token.TokenResponse;
import org.nsbm.dea.student_management_system.token.types.Access;
import org.nsbm.dea.student_management_system.token.types.Refresh;
import org.nsbm.dea.student_management_system.token.types.Session;

import com.google.gson.Gson;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@WebServlet("/api/auth/login")
public class Login extends HttpServlet {
  private static final ExecutorService executor = Executors.newFixedThreadPool(5);
  private static final Logger logger = Logger.getLogger(AppError.class.getName());

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    BufferedReader reader = request.getReader();
    Gson gson = new Gson();
    LoginRequest login = gson.fromJson(reader, LoginRequest.class);
    reader.close();

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Set<ConstraintViolation<LoginRequest>> violations = validator.validate(login);
    if (!violations.isEmpty()) {
      AppError.response(response, AppError.fromValidationError(violations));
      return;
    }

    String ip = request.getRemoteAddr();
    if (ip == null || ip.isEmpty()) {
      if (Environment.isPrd() || Environment.isStg()) {
        AppError.response(response, AppError.badRequest("IP address is required to process this request", null));
        return;
      }
      ip = "127.0.0.1";
    }

    try {
      Optional<User> payload = UserDAO.getByEmail(login.getEmail());
      if (payload.isEmpty()) {
        AppError.response(response, AppError.badRequest("The email address you provided is not yet registered", null));
        return;
      }
      User user = payload.get();
      BCrypt.Result result = BCrypt.verifyer().verify(login.getPassword().toCharArray(), user.getPassword());
      if (!result.verified) {
        AppError.response(response, AppError.unauthorized("The email/password combination is incorrect", null));
        return;
      }

      Refresh refresh = new Refresh(user.getId());
      TokenResponse<PrimaryClaims> refreshResponse = refresh.create(new TokenParams());
      Claims refreshClaims = refreshResponse.getClaims();
      String ajti = refreshClaims.getCustom().orElse("");
      if (ajti == null || ajti == "") {
        throw new Exception("Access token jti must be present with the refresh token claims");
      }
      Access access = new Access(user.getId());
      TokenResponse<PrimaryClaims> accessResponse = access
          .create(new TokenParams().withAjti(ajti).withRjti(refreshClaims.getJti()));
      Session session = new Session(user.toUserDetails());
      TokenResponse<ExtendedClaims> sessionResponse = session.create(new TokenParams());

      String refreshToken = refreshResponse.getToken();
      String accessToken = accessResponse.getToken();
      String sessionToken = sessionResponse.getToken();

      Cookie refreshCookie = new Cookie("dea_refresh", refreshToken);
      refreshCookie.setMaxAge((int) Env.getRefreshTokenExpiration());
      refreshCookie.setPath("/");
      refreshCookie.setHttpOnly(true);
      refreshCookie.setSecure(!Environment.isDev());
      refreshCookie.setDomain(Env.getDomain());

      Cookie sessionCookie = new Cookie("dea_session", sessionToken);
      sessionCookie.setMaxAge((int) Env.getSessionTokenExpiration());
      sessionCookie.setPath("/");
      sessionCookie.setHttpOnly(false);
      sessionCookie.setSecure(!Environment.isDev());
      sessionCookie.setDomain(Env.getDomain());

      response.setHeader("X-Access-Token", accessToken);
      response.addCookie(refreshCookie);
      response.addCookie(sessionCookie);

      String ipAddress = ip;
      executor.submit(() -> {
        try {
          SessionDAO.create(refreshClaims.getJti(), user.getId(), ipAddress, refreshClaims.getIat().getTime(),
              refreshClaims.getExp().getTime());
        } catch (Exception e) {
          logger.log(Level.SEVERE, "Failed to record the session in the database", e);
        }
      });

      new Response("success", null).toJson(response, HttpServletResponse.SC_OK);
    } catch (SQLException e) {
      AppError.response(response, AppError.fromDbError(e));
      return;
    } catch (TokenError e) {
      AppError.response(response, AppError.fromTokenError(e));
      return;
    } catch (Exception e) {
      AppError.response(response, AppError.fromGenericError(e));
      return;
    }
  }

  @Override
  public void destroy() {
    executor.shutdown();
    super.destroy();
  }
}
