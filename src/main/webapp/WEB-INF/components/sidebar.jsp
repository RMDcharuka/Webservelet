<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.nsbm.dea.student_management_system.model.subject.SubjectDetails" %>
<%@ page import="java.util.List" %>
<%@ page import="org.nsbm.dea.student_management_system.dao.SubjectDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<%
    List<SubjectDetails> subjects = new ArrayList<>();
    try {
        subjects = SubjectDAO.getNamesAndSlugs();
        request.setAttribute("subjects", subjects);
    } catch (SQLException e) {
        throw new ServletException();
    }
%>

<nav class="sidebar">
    <h2>Student Manager</h2>
    <ul>
      <li><a href="${pageContext.request.contextPath}/dashboard"><i class="fas fa-home"></i> Home</a></li>

        <li class="dropdown">
            <input type="checkbox" id="students-toggle">
            <label for="students-toggle" class="dropdown-btn">
                <i class="fas fa-user-graduate"></i> Students
                <i class="fas fa-chevron-down dropdown-arrow"></i>
            </label>
            <ul class="dropdown-content">
                <li><a href="#">Add New Student</a></li>
                <li><a href="#">Student Profile</a></li>
            </ul>
        </li>

        <li class="dropdown">
          <input type="checkbox" id="academic-toggle">
          <label for="academic-toggle" class="dropdown-btn">
            <i class="fas fa-book"></i> Academic Records
            <i class="fas fa-chevron-down dropdown-arrow"></i>
          </label>
          <ul class="dropdown-content">
            <c:forEach var="subject" items="${subjects}">
              <li><a href="${pageContext.request.contextPath}/record/${subject.slug}">${subject.name}</a></li>
            </c:forEach>
          </ul>
        </li>

        <li class="dropdown">
            <input type="checkbox" id="attendance-toggle">
            <label for="attendance-toggle" class="dropdown-btn">
                <i class="fas fa-chart-bar"></i> Attendance
                <i class="fas fa-chevron-down dropdown-arrow"></i>
            </label>
            <ul class="dropdown-content">
              <c:forEach var="subject" items="${subjects}">
                <li><a href="${pageContext.request.contextPath}/attendance/${subject.slug}">${subject.name}</a></li>
              </c:forEach>
            </ul>
        </li>

        <li><a href="#"><i class="fas fa-cog"></i> Settings</a></li>
    </ul>
</nav>
