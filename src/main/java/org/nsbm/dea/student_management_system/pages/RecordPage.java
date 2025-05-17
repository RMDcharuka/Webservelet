package org.nsbm.dea.student_management_system.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nsbm.dea.student_management_system.dao.SubjectDAO;
import org.nsbm.dea.student_management_system.model.subject.Marks;
import org.nsbm.dea.student_management_system.model.subject.SubjectDetails;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/record/*")
public class RecordPage extends HttpServlet {
  private static final Logger logger = Logger.getLogger(RecordPage.class.getName());

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String pathInfo = request.getPathInfo();
      String subjectSlug = pathInfo != null && pathInfo.length() > 1 ? pathInfo.substring(1) : null;
      if (subjectSlug == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Record not found");
        return;
      }
      Optional<SubjectDetails> subject = SubjectDAO.getSubjectBySlug(subjectSlug);
      if (subject.isEmpty()) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Record not found");
        return;
      }

      List<Marks> marksList = new ArrayList<>();
      marksList = SubjectDAO.getExaminationResultsForSubject(subjectSlug);
      int totalStudents = SubjectDAO.getTotalStudents(subject.get().getId());
      Optional<Float> avgAttendance = SubjectDAO.getAverageAttendancePerSubject(subjectSlug);

      if (avgAttendance.isPresent()) {
        request.setAttribute("avgAttendance", avgAttendance.get());
      } else {
        request.setAttribute("avgAttendance", 0);
      }
      request.setAttribute("marksList", marksList);
      request.setAttribute("subject", subject.get());
      request.setAttribute("totalStudents", totalStudents);
      request.getRequestDispatcher("/WEB-INF/record.jsp").forward(request, response);
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving marks");
      return;
    }
  }
}
