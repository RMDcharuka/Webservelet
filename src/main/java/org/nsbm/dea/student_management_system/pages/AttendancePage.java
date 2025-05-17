package org.nsbm.dea.student_management_system.pages;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.nsbm.dea.student_management_system.dao.SubjectDAO;
import org.nsbm.dea.student_management_system.model.subject.Attendance;
import org.nsbm.dea.student_management_system.model.subject.SubjectDetails;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/attendance/*")
public class AttendancePage extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String pathInfo = request.getPathInfo();
    String subjectSlug = pathInfo != null && pathInfo.length() > 1 ? pathInfo.substring(1) : null;
    if (subjectSlug == null || subjectSlug.isEmpty()) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND, "Subject not found");
      return;
    }

    try {
      Optional<SubjectDetails> subject = SubjectDAO.getSubjectBySlug(subjectSlug);
      if (subject.isEmpty()) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Subject not found");
        return;
      }

      List<Attendance> attendanceList = SubjectDAO.getAttendanceForSubject(subjectSlug);

      request.setAttribute("attendanceList", attendanceList);
      request.setAttribute("subject", subject.get());
      request.getRequestDispatcher("/WEB-INF/attendance.jsp").forward(request, response);
    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong");
      return;
    }
  }
}
