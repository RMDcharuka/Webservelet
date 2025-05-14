package org.nsbm.dea.student_management_system.error;

import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nsbm.dea.student_management_system.model.http.Response;
import org.nsbm.dea.student_management_system.token.TokenError;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

public class AppError extends Exception {
  private static final Logger logger = Logger.getLogger(AppError.class.getName());

  private final String type;
  private final String message;
  private final Throwable cause;

  public static final String ERR_NOT_FOUND = "NotFound";
  public static final String ERR_BAD_REQUEST = "BadRequest";
  public static final String ERR_UNIQUE_VIOLATION = "UniqueViolation";
  public static final String ERR_UNAUTHORIZED = "Unauthorized";
  public static final String ERR_VALIDATION = "Validation";
  public static final String ERR_INTERNAL = "Internal";

  AppError(String type, String message, Throwable cause) {
    super(message, cause);
    this.type = type;
    this.message = message;
    this.cause = cause;
  }

  public static AppError notFound(String message, Throwable cause) {
    return new AppError(ERR_NOT_FOUND, message, cause);
  }

  public static AppError badRequest(String message, Throwable cause) {
    return new AppError(ERR_BAD_REQUEST, message, cause);
  }

  public static AppError uniqueViolation(String message, Throwable cause) {
    return new AppError(ERR_UNIQUE_VIOLATION, message, cause);
  }

  public static AppError unauthorized(String message, Throwable cause) {
    return new AppError(ERR_UNAUTHORIZED, message, cause);
  }

  public static AppError validation(String message, Throwable cause) {
    return new AppError(ERR_VALIDATION, message, cause);
  }

  public static AppError internal(String message, Throwable cause) {
    return new AppError(ERR_INTERNAL, message, cause);
  }

  public static AppError fromDbError(SQLException exception) {
    if (exception == null) {
      return null;
    }

    if (exception instanceof PSQLException) {
      PSQLException pgEx = (PSQLException) exception;
      String sqlState = pgEx.getSQLState();

      if (PSQLState.UNIQUE_VIOLATION.getState().equals(sqlState)) {
        return uniqueViolation("unique constraint violation", exception);
      } else if (PSQLState.FOREIGN_KEY_VIOLATION.getState().equals(sqlState)) {
        return badRequest("foreign key violation", exception);
      } else if (PSQLState.NOT_NULL_VIOLATION.getState().equals(sqlState)) {
        return badRequest("null value violation", exception);
      }
    }

    return internal("internal server error", exception);
  }

  public static AppError fromTokenError(TokenError exception) {
    if (exception == null) {
      return null;
    }

    switch (exception.getKind()) {
      case MISSING_CLAIMS:
      case INVALID_FORMAT:
      case PARSING_FAILED:
      case VALIDATION_FAILED:
        return badRequest(exception.getMessage(), exception);
      default:
        return internal("Something went wrong", exception);
    }
  }

  public static <T> AppError fromValidationError(Set<ConstraintViolation<T>> violations) {
    if (violations == null || violations.isEmpty()) {
      return null;
    }

    String violation = violations.iterator().next().getMessage();
    return badRequest(violation, null);
  }

  public static AppError fromGenericError(Exception exception) {
    if (exception == null) {
      return null;
    }

    return internal("something went wrong", exception);
  }

  public void toResponse(HttpServletResponse response) {
    response.setContentType("application/json");

    int statusCode;
    switch (this.type) {
      case ERR_NOT_FOUND:
        statusCode = HttpServletResponse.SC_NOT_FOUND;
        break;
      case ERR_BAD_REQUEST:
      case ERR_UNIQUE_VIOLATION:
        statusCode = HttpServletResponse.SC_BAD_REQUEST;
        break;
      case ERR_UNAUTHORIZED:
        statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        break;
      case ERR_INTERNAL:
      default:
        statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        break;
    }

    logger.log(Level.SEVERE, String.format("[%s] %s", this.type, this.message), this.cause);
    Response res = new Response(message, null);
    res.toJson(response, statusCode);
  }

  public static void response(HttpServletResponse response, Exception exception) {
    if (exception == null) {
      AppError.internal("Something went wrong", null).toResponse(response);
      return;
    }

    if (exception instanceof AppError) {
      ((AppError) exception).toResponse(response);
      return;
    }

    AppError.internal("Something went wrong", exception).toResponse(response);
  }
}
