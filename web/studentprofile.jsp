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
    <div class="profile-header">
        <h1>Student Profile</h1>
        <div class="search-box">
          <input type="text" placeholder="Search student..." />
          <button><i class="fas fa-search"></i></button>
        </div>
      </div>
      
    
</header>

<main class="profile-container">
  <div class="left-panel">
    <div class="profile-picture">
      <img src="images/st3.jpeg" alt="Student Photo">
    </div>
    <h2>John Doe</h2>
    <p class="student-id">ID: 2025001</p>
    <p class="course">B.Sc Computer Science</p>
    <button class="contact-btn">Change Details</button>
  </div>
  
  <div class="right-panel">
    

    <section class="cards-container">
        <div class="small-card">
          <i class="fas fa-user-graduate"></i>
          <h3>Total Students</h3>
          <p>400</p>
        </div>
        <div class="small-card">
            <i class="fas fa-user-clock"></i>
            <h3>Avg Attendance</h3>
            <p>76%</p>
          </div>
          <div class="small-card">
            <i class="fas fa-user-clock"></i>
            <h3>Avg Attendance</h3>
            <p>76%</p>
          </div> <div class="small-card">
            <i class="fas fa-user-clock"></i>
            <h3>Avg Attendance</h3>
            <p>76%</p>
          </div>
      </section>


    <div class="info-section">
      <h3>Personal Details</h3>
      <ul>
        <li><strong>Email:</strong> john.doe@example.com</li>
        <li><strong>Phone:</strong> +123-456-7890</li>
        <li><strong>Date of Birth:</strong> January 1, 2000</li>
        <li><strong>Address:</strong> 123 Main Street, City, Country</li>
      </ul>
    </div>

    <div class="info-section">
      <h3>Subject</h3>
      <ul class="skills">
        <li>HTML5 & CSS3</li>
        <li>JavaScript</li>
        <li>Python</li>
        <li>Java</li>
        <li>SQL Databases</li>
      </ul>
    </div>


    <h2>About Student</h2>
    <p class="about">
      I am a passionate Computer Science student with interests in web development, AI, and mobile app design. I enjoy participating in hackathons and coding competitions.
    </p>