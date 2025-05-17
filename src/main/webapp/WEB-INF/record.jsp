<%@ page import="org.nsbm.dea.student_management_system.model.subject.Marks" %>
<%@ page import="org.nsbm.dea.student_management_system.dao.StudentDAO" %>
<jsp:useBean id="marksList" scope="request" type="java.util.List<org.nsbm.dea.student_management_system.model.subject.Marks>"/>
<jsp:useBean id="subject" scope="request" type="org.nsbm.dea.student_management_system.model.subject.SubjectDetails"/>
<jsp:useBean id="totalStudents" scope="request" type="java.lang.Integer" />
<jsp:useBean id="avgAttendance" scope="request" type="java.lang.Float" />
<%
   float totalMarks = 0;
   int n = 0;
   for (Marks marks : marksList) {
      totalMarks += marks.getExaminationMarks();
      n += 1;
   }
   request.setAttribute("averageMarks", n > 0 ? totalMarks/n : 0);
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
            <h1>${subject.name} Students Records</h1>
         </header>
         <section class="cards-container">
            <div class="card">
               <i class="fas fa-user-graduate"></i>
               <h3>Total Students</h3>
               <p>${totalStudents}</p>
            </div>
            <div class="card">
               <i class="fas fa-file-alt"></i>
               <h3>Avg Marks</h3>
               <p>${averageMarks}</p>
            </div>
            <div class="card">
               <i class="fas fa-user-clock"></i>
               <h3>Avg Attendance</h3>
               <p>${avgAttendance}%</p>
            </div>
         </section>
         <section>
            <table border="1" cellspacing="0" cellpadding="8">
               <thead>
                  <tr>
                     <th>ID</th>
                     <th>Name</th>
                     <th>Examination name</th>
                     <th>Marks</th>
                  </tr>
               </thead>
               <tbody>
               <c:forEach var="marks" items="${marksList}">
                  <tr>
                     <td>${marks.studentId}</td>
                     <td>${marks.studentName}</td>
                     <td>${marks.examinationName}</td>
                     <td>${marks.examinationMarks}</td>
                  </tr>
               </c:forEach>
               </tbody>
            </table>
         </section>
      </main>
   </body>
</html>
