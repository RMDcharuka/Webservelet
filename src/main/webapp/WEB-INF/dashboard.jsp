<%@ page import="java.util.logging.Logger" %>
<%@ page import="java.util.logging.Level" %>
<%@ page import="org.nsbm.dea.student_management_system.dao.StudentDAO" %>
<%@ page import="org.nsbm.dea.student_management_system.dao.UserDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%
    final Logger logger = Logger.getLogger("dashboard");
    try {
        request.setAttribute("totalSubjects", SubjectDAO.getTotalSubjects());
        request.setAttribute("totalStudents", StudentDAO.getTotalStudents());
        request.setAttribute("totalExaminations", SubjectDAO.getTotalExaminations());
        request.setAttribute("totalUsers", UserDAO.getTotalUsers());
        request.setAttribute("averageAttendance", String.format("%.2f", SubjectDAO.getAverageAttendanceAcrossAllSubjects()));
        request.setAttribute("topPerformers", StudentDAO.getTopPerformers());
        request.setAttribute("exams", SubjectDAO.getUpcommingExaminations());

        // INFO: The task progress via subject is done like this because the full feature could not be implemented
        List<SubjectDetails> subjects = SubjectDAO.getNamesAndSlugs();
        List<Map<String, Object>> subjectProgressList = new ArrayList<>();
        for (SubjectDetails subject : subjects) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("subject", subject);
            entry.put("progress", (int) (Math.random() * 101));
            subjectProgressList.add(entry);
        }
        request.setAttribute("subjectProgressList", subjectProgressList);
    } catch (Exception e) {
        logger.log(Level.SEVERE, e.getMessage());
        throw new ServletException();
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Student Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/layout/style.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
</head>
<body>

<%@include file="components/sidebar.jsp"%>
<main class="main-content">
    <header class="topbar">
        <h1>Welcome to Student Management System</h1>
    </header>

    <section class="cards-container">
        <div class="card"><i class="fas fa-user-graduate"></i><h3>Total Students</h3><p>${totalStudents}</p></div>
        <div class="card"><i class="fas fa-book"></i><h3>Subjects</h3><p>${totalSubjects}</p></div>
        <div class="card"><i class="fas fa-chalkboard-teacher"></i><h3>Instructors</h3><p>${totalUsers}</p></div>
        <div class="card"><i class="fas fa-file-alt"></i><h3>Exams</h3><p>${totalExaminations}</p></div>
        <div class="card"><i class="fas fa-user-clock"></i><h3>Avg Attendance</h3><p>${averageAttendance}%</p></div>
    </section>

    <section class="lower-cards-container">
        <div class="subject-task">
            <h4>Subject Task</h4>
            <c:forEach var="entry" items="${subjectProgressList}">
                <c:set var="subject" value="${entry.subject}" />
                <c:set var="progress" value="${entry.progress}" />
                <div class="bar"><span>${subject.name}</span><div style="width: ${progress}%;" class="bar-fill orange">${progress}%</div></div>
            </c:forEach>
        </div>

        <div class="top-students">
            <h4>Top Performers</h4>
            <c:forEach var="performer" items="${topPerformers}">
                <ul>
                    <li>[${performer.subject_name}] ${performer.student_name} - Average: ${performer.average_marks}%</li>
                </ul>
            </c:forEach>
        </div>
    </section>


    <section class="exam-cards-row">
        <!-- Upcoming Exams Card (Left side) -->
        <div class="upcoming-exams">
            <h4>Upcoming Exams</h4>
            <div class="exam-card">
                <c:forEach var="exam" items="${exams}">
                    <p>${exam.subject_name} <strong>${exam.name}</strong> - ${exam.date}</p>
                </c:forEach>
            </div>
        </div>

        <!-- Right Side Image Cards -->
        <div class="image-cards">
            <div class="img-card">
                <img src="assets/images/dashboard/boy.jpeg" alt="Exam Poster">
                <p>Prepare for Math Exam</p>
            </div>
            <div class="img-card">
                <img src="assets/images/dashboard/boy.jpeg" alt="Physics Poster">
                <p>Physics Concepts Review</p>
            </div>
        </div>
    </section>
</main>
</body>
</html>
