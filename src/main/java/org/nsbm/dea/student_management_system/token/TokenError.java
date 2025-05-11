package org.nsbm.dea.student_management_system.token;

public class TokenError extends Exception {
  public enum ErrorKind {
    CREATION_FAILED,
    VALIDATION_FAILED,
    PARSING_FAILED,
    INVALID_FORMAT,
    MISSING_CLAIMS,
    OTHER
  }

  private final ErrorKind kind;

  public TokenError(ErrorKind kind, String message, Throwable cause) {
    super(message, cause);
    this.kind = kind;
  }

  public TokenError(ErrorKind kind, String message) {
    super(message);
    this.kind = kind;
  }

  public ErrorKind getKind() {
    return kind;
  }

  @Override
  public String toString() {
    return "[" + kind.name().toLowerCase() + "] " + getMessage();
  }

  public static TokenError creation(Throwable cause) {
    return new TokenError(ErrorKind.CREATION_FAILED, cause.getMessage(), cause);
  }

  public static TokenError validation(Throwable cause) {
    return new TokenError(ErrorKind.VALIDATION_FAILED, cause.getMessage(), cause);
  }

  public static TokenError parsing(Throwable cause) {
    return new TokenError(ErrorKind.PARSING_FAILED, cause.getMessage(), cause);
  }

  public static TokenError invalidFormat(Throwable cause) {
    return new TokenError(ErrorKind.INVALID_FORMAT, cause.getMessage(), cause);
  }

  public static TokenError missingClaims(Throwable cause) {
    return new TokenError(ErrorKind.MISSING_CLAIMS, cause.getMessage(), cause);
  }

  public static TokenError other(Throwable cause) {
    return new TokenError(ErrorKind.OTHER, cause.getMessage(), cause);
  }
}
