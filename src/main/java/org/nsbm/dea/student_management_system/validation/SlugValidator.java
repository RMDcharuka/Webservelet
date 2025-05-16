package org.nsbm.dea.student_management_system.validation;

import org.nsbm.dea.student_management_system.validation.annotations.ValidSlug;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SlugValidator implements ConstraintValidator<ValidSlug, String> {
  @Override
  public void initialize(ValidSlug constraintAnnotation) {
  }

  private void addConstraintViolation(ConstraintValidatorContext context, String message) {
    context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
  }

  @Override
  public boolean isValid(String slug, ConstraintValidatorContext context) {
    if (slug == null) {
      return false;
    }
    context.disableDefaultConstraintViolation();

    if (slug.length() < 3) {
      addConstraintViolation(context, "Slug must be at least 8 characters long");
      return false;
    }
    if (slug.length() > 255) {
      addConstraintViolation(context, "Slug must be at most 255 characters long");
      return false;
    }
    if (slug.contains(" ")) {
      addConstraintViolation(context, "Slug must not contain spaces");
      return false;
    }
    if (slug.contains("..")) {
      addConstraintViolation(context, "Slug must not contain consecutive dots");
      return false;
    }
    if (slug.startsWith(".") || slug.endsWith(".")) {
      addConstraintViolation(context, "Slug must not start or end with a dot");
      return false;
    }
    if (slug.contains("..")) {
      addConstraintViolation(context, "Slug must not contain consecutive dots");
      return false;
    }
    if (slug.contains("^[a-zA-Z0-9]*$")) {
      addConstraintViolation(context, "Slug must only contain alphanumeric characters");
      return false;
    }

    return true;
  }
}
