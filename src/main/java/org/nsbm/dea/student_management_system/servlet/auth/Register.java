package org.nsbm.dea.student_management_system.servlet.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import com.google.gson.Gson;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.nsbm.dea.student_management_system.model.user.RegisterRequest;
import org.nsbm.dea.student_management_system.error.AppError;
import org.nsbm.dea.student_management_system.model.http.Response;
import org.nsbm.dea.student_management_system.dao.UserDAO;

@WebServlet("/api/auth/register")
public class Register extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    BufferedReader reader = request.getReader();
    Gson gson = new Gson();
    RegisterRequest register = gson.fromJson(reader, RegisterRequest.class);
    reader.close();

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(register);
    if (!violations.isEmpty()) {
      AppError.response(response, AppError.fromValidationError(violations));
      return;
    }

    String passwordHash = BCrypt.withDefaults().hashToString(10, register.getPassword().toCharArray());
    try {
      UserDAO.create(register.getEmail(), register.getName(), passwordHash);
    } catch (SQLException e) {
      AppError.response(response, AppError.fromDbError(e));
      return;
    } catch (Exception e) {
      AppError.response(response, AppError.fromGenericError(e));
      return;
    }

    new Response("success", null).toJson(response, HttpServletResponse.SC_OK);
  }
}
