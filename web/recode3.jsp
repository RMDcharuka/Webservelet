<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Student Dashboard</title>
  <link rel="stylesheet" href="style.css"/>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
</head>
<body>

  <nav class="sidebar">
    <h2>Student Manager</h2>
    <ul>
      <li><a href="index.jsp"><i class="fas fa-home"></i> Home</a></li>

      <li class="dropdown">
        <input type="checkbox" id="students-toggle">
        <label for="students-toggle" class="dropdown-btn">
          <i class="fas fa-user-graduate"></i> Students
          <i class="fas fa-chevron-down dropdown-arrow"></i>
        </label>
        <ul class="dropdown-content">
          <li><a href="student.jsp">Add New Student</a></li>
          <li><a href="studentprofile.jsp">Student Profile</a></li>
        </ul>
      </li>

      <li class="dropdown">
        <input type="checkbox" id="academic-toggle">
        <label for="academic-toggle" class="dropdown-btn">
          <i class="fas fa-book"></i> Academic Records
          <i class="fas fa-chevron-down dropdown-arrow"></i>
        </label>
        <ul class="dropdown-content">
          <li><a href="recode1.jsp">Data Science</a></li>
          <li><a href="recode2.jsp">Cyber Security</a></li>
          <li><a href="recode3.jsp">Cloud Computing</a></li>
          <li><a href="recode4.jsp">Software Engineering</a></li>
        </ul>
      </li>

      <li class="dropdown">
        <input type="checkbox" id="attendance-toggle">
        <label for="attendance-toggle" class="dropdown-btn">
          <i class="fas fa-chart-bar"></i> Attendance
          <i class="fas fa-chevron-down dropdown-arrow"></i>
        </label>
        <ul class="dropdown-content">
          <li><a href="attendenc1.jsp">Data Science</a></li>
          <li><a href="attendenc2.jsp">Cyber Security</a></li>
          <li><a href="attendenc3.jsp">Cloud Computing</a></li>
          <li><a href="attendenc4.jsp">Software Engineering</a></li>
        </ul>
      </li>

      <li><a href="#"><i class="fas fa-cog"></i> Settings</a></li>
    </ul>
  </nav>

  <main class="main-content">

    <section class="welcome">
      <h1>Welcome to the Cloud Computing Records</h1>
    </section>

    <section class="hearde text">
      <div class="card">
        <div class="card-text">
          <h2>Hello!</h2>
          <p>Perfect day to chase dreams, believe in yourself, take bold steps, and make your hopes a reality. ✨ </p>
        </div>
        <div class="card-image">
          <img src="images/da5.jpg" alt="Illustration">
        </div>
      </div>
    </section>
   
    <section class="cards-container">
      <div class="small-card">
        <i class="fas fa-user-graduate"></i>
        <h3>Total Students</h3>
        <p>${totalStudents}</p> <!-- Dynamic value using EL -->
      </div>
      <div class="small-card">
        <i class="fas fa-user-clock"></i>
        <h3>Avg Attendance</h3>
        <p>${avgAttendance}%</p> <!-- Dynamic value using EL -->
      </div>
    </section>

    <section>
      <table border="1" cellspacing="0" cellpadding="8">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Course</th>
            <th>AVG Marks</th>
          </tr>
        </thead>
        <tbody>
          <%
            // Sample JSP scriptlet to generate table rows dynamically
            String[][] students = {
              {"001", "John Doe", "Mathematics", "85"},
              {"002", "Jane Smith", "Physics", "78"},
              {"003", "Ali Khan", "Chemistry", "90"}
            };
            
            for(String[] student : students) {
          %>
          <tr>
            <td><%= student[0] %></td>
            <td><%= student[1] %></td>
            <td><%= student[2] %></td>
            <td><%= student[3] %></td>
          </tr>
          <%
            }
          %>
        </tbody>
      </table>
    </section>

  </main>

</body>
</html>