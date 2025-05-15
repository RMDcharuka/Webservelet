package org.nsbm.dea.student_management_system.validation;

import org.nsbm.dea.student_management_system.validation.annotations.ValidPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
  @Override
  public void initialize(ValidPassword constraintAnnotation) {
  }

  private void addConstraintViolation(ConstraintValidatorContext context, String message) {
    context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    if (password == null) {
      return false;
    }
    context.disableDefaultConstraintViolation();

    if (password.length() < 8) {
      addConstraintViolation(context, "Password must be at least 8 characters long");
      return false;
    }
    if (password.length() > 255) {
      addConstraintViolation(context, "Password must be at most 255 characters long");
      return false;
    }
    if (password.matches("^[a-zA-Z0-9]*$")) {
      addConstraintViolation(context, "Password must contain at least one special character");
      return false;
    }
    if (!password.matches(".*[a-z].*")) {
      addConstraintViolation(context, "Password must contain at least one lowercase letter");
      return false;
    }
    if (!password.matches(".*[A-Z].*")) {
      addConstraintViolation(context, "Password must contain at least one uppercase letter");
      return false;
    }
    if (!password.matches(".*[0-9].*")) {
      addConstraintViolation(context, "Password must contain at least one number");
      return false;
    }

    return true;
  }
}
