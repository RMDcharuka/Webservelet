package org.nsbm.dea.student_management_system.servlet.subject;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import org.nsbm.dea.student_management_system.dao.SubjectDAO;
import org.nsbm.dea.student_management_system.error.AppError;
import org.nsbm.dea.student_management_system.model.http.Response;
import org.nsbm.dea.student_management_system.model.subject.RecordAttendanceRequest;
import org.nsbm.dea.student_management_system.model.user.UserDetails;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@WebServlet("/api/subject/record-attendance")
public class RecordAttendance extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    UserDetails user = (UserDetails) request.getAttribute("userDetails");

    BufferedReader reader = request.getReader();
    Gson gson = new Gson();
    RecordAttendanceRequest enroll = gson.fromJson(reader, RecordAttendanceRequest.class);
    reader.close();

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Set<ConstraintViolation<RecordAttendanceRequest>> violations = validator.validate(enroll);
    if (!violations.isEmpty()) {
      AppError.response(response, AppError.fromValidationError(violations));
      return;
    }

    try {
      SubjectDAO.recordAttendance(user.getId(), enroll.getStudentId(), enroll.getSubjectId());
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
