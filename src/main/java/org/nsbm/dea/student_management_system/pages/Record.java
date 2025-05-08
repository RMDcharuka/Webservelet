package org.nsbm.dea.student_management_system.pages;

import java.io.IOException;
import java.util.Optional;
import org.nsbm.dea.student_management_system.lib.Faculty;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/record/*")
public class Record extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String pathInfo = request.getPathInfo();
    String faculty = pathInfo != null && pathInfo.length() > 1 ? pathInfo.substring(1) : null;
    if (faculty == null || faculty.isEmpty()) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND, "Faculty not found");
      return;
    }
    Optional<String> facultyName = Faculty.getFacultyName(faculty);
    if (facultyName.isEmpty()) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND, "Faculty not found");
      return;
    }

    request.setAttribute("faculty", facultyName.get());
    request.getRequestDispatcher("/WEB-INF/record.jsp").forward(request, response);
  }
}
