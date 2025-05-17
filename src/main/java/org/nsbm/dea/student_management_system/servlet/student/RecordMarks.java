package org.nsbm.dea.student_management_system.servlet.student;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import org.nsbm.dea.student_management_system.dao.SubjectDAO;
import org.nsbm.dea.student_management_system.error.AppError;
import org.nsbm.dea.student_management_system.model.http.Response;
import org.nsbm.dea.student_management_system.model.student.RecordMarksRequest;
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

@WebServlet("/api/subject/record-marks")
public class RecordMarks extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    UserDetails user = (UserDetails) request.getAttribute("userDetails");

    BufferedReader reader = request.getReader();
    Gson gson = new Gson();
    RecordMarksRequest recordMarks = gson.fromJson(reader, RecordMarksRequest.class);
    reader.close();

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Set<ConstraintViolation<RecordMarksRequest>> violations = validator.validate(recordMarks);
    if (!violations.isEmpty()) {
      AppError.response(response, AppError.fromValidationError(violations));
      return;
    }

    try {
      boolean isEnrolled = SubjectDAO.getEnrollmentStatus(recordMarks.getStudent_id(), recordMarks.getStudent_id());
      if (!isEnrolled) {
        AppError.response(response, AppError.badRequest("Student is not enrolled in the subject", null));
        return;
      }

      SubjectDAO.recordExaminationMarks(user.getId(), recordMarks.getExamination_id(),
          recordMarks.getStudent_id(), recordMarks.getMarks());
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
