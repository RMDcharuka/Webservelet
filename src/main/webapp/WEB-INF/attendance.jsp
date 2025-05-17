<%@ page import="org.nsbm.dea.student_management_system.model.subject.Attendance" %>
<jsp:useBean id="attendanceList" scope="request" type="java.util.List<org.nsbm.dea.student_management_system.model.subject.Attendance>"/>
<jsp:useBean id="subject" scope="request" type="java.lang.String"/>
<%
   int students = attendanceList.size();
   float attendancePercentageSum = 0;
   for(Attendance attendance : attendanceList) {
      attendancePercentageSum += attendance.getAttendancePercentage();
   }
   float averageAttendancePercentage = students > 0 ? attendancePercentageSum / students : 0;
   request.setAttribute("averageAttendancePercentage", averageAttendancePercentage);
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
            <h1>${subject} Attendance</h1>
         </header>
         <section class="cards-container">
            <div class="card">
               <i class="fas fa-user-graduate"></i>
               <h3>Total Students</h3>
               <p>${attendanceList.size()}</p>
            </div>
            <div class="card">
               <i class="fas fa-user-clock"></i>
               <h3>Avg Attendance</h3>
               <p>${averageAttendancePercentage}%</p>
            </div>
         </section>
         <section>
            <table border="1" cellspacing="0" cellpadding="8">
               <thead>
                  <tr>
                     <th>ID</th>
                     <th>Name</th>
                     <th> Total Days</th>
                     <th>Present</th>
                     <th>Absent</th>
                     <th>Attendance</th>
                  </tr>
               </thead>
               <tbody>
               <c:forEach var="attendance" items="${attendanceList}">
                  <tr>
                     <td>${attendance.studentId}</td>
                     <td>${attendance.studentName}</td>
                     <td>${attendance.subjectTotalSessions}</td>
                     <td>${attendance.presentSessions}</td>
                     <td>${attendance.subjectTotalSessions - attendance.presentSessions}</td>
                     <td>${attendance.presentSessions / attendance.subjectTotalSessions * 100}%</td>
                  </tr>
               </c:forEach>
               </tbody>
            </table>
         </section>
      </main>
   </body>
</html>
