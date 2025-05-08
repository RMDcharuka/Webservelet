<%-- 
    Document   : index
    Created on : May 8, 2025, 10:55:18 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            body {
      margin: 0;
      font-family: "Segoe UI",Tahoma, Geneva, Verdana, sans-serif;
      background-color: #eef2f7;
      color: black;
    }

    .header {
      background-color: #123a6d;
      color: white;
      padding: 70px 20px;
      text-align: center;
    }

    .header h1 {
      font-size: 3rem;
      margin-bottom: 10px;
    }

    .header p {
      font-size: 1.2rem;
      margin-bottom: 20px;
    }

    .header .buttons a {
      background-color: #1abc9c;
      color: white;
      text-decoration: none;
      padding: 12px 25px;
      margin: 10px;
      border-radius: 25px;
      font-weight: bold;
      display: inline-block;
      transition: background 0.3s ease;
    }

    .header .buttons a:hover {
      background-color: lightskyblue;
    }

    .section {
      padding: 60px 20px;
      text-align: center;
      background-color: white;
      margin: 30px auto;
      border-radius: 20px;
      width: 90%;
      max-width: 1000px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    .section h2 {
      font-size: 2rem;
      margin-bottom: 30px;
    }

    .features {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      gap: 30px;
    }

    .feature-box {
      background-color: rgba(242, 237, 237, 0.985);
      padding: 20px;
      border-radius: 10px;
      width: 250px;
      box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
      transition: transform 0.3s ease, box-shadow 0.3s ease;
    }

    .feature-box:hover {
      transform: translateY(-10px);
      box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
    }

    .feature-box h3 {
      margin-top: 10px;
      color: black;
    }

    .feature-box p {
      font-size: 0.95rem;
    }

    .vision-mission {
      background-color: #ffffff;
      margin: 30px auto;
      padding: 40px 20px;
      text-align: center;
      border-radius: 30px;
      width: 90%;
      max-width: 700px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    .vision-mission h2 {
      margin-bottom: 20px;
    }

    .vision-mission p {
      max-width: 700px;
      margin: 10px auto;
      font-size: 1rem;
      line-height: 1.6;
    }

    .footer {
      background-color: #123a6d;
      color: white;
      text-align: center;
      padding: 20px 10px;
    }
        </style>
    </head>
    <body>
         <div class="header">
    <h1>Student Management Portal</h1>
    <p>"An all-in-one platform to manage student records, attendance, exams, and more."</p>
    <div class="buttons">
      <a href="login.html">Login</a>
      <a href="signup.html">Sign Up</a>
    </div>
  </div>

  <div class="section">
    <h2>What Can You Do?</h2>
    <div class="features">
      <div class="feature-box">
        <h3>📝 Add Students</h3>
        <p>Register new students and store their academic information securely.</p>
      </div>
      <div class="feature-box">
        <h3>📅 Attendance</h3>
        <p>Record daily attendance and view monthly summaries in one click.</p>
      </div>
      <div class="feature-box">
        <h3>📚 Courses</h3>
        <p>Assign and manage courses, subjects, and academic schedules for each class.</p>
      </div>
      <div class="feature-box">
        <h3>🧪 Examinations</h3>
        <p>Create and manage exams, track results, and maintain grading records.</p>
      </div>
      <div class="feature-box">
        <h3>📊 Reports</h3>
        <p>Generate performance and attendance reports in PDF or Excel format.</p>
      </div>
      <div class="feature-box">
        <h3>📬 Notifications</h3>
        <p>Send announcements and alerts to students via email or SMS.</p>
      </div>
      
    </div>
  </div>

  <div class="vision-mission">
    <h2>Our Vision</h2>
    <p>To revolutionize the education system by providing seamless and smart digital solutions that empower institutions and enhance student success.</p><br></br>
    
    <h2>Our Mission</h2>
    <p>To deliver a user-friendly and feature-rich platform for managing academic activities, ensuring transparency, efficiency, and progress in educational institutions.</p>
  </div>

  <div class="footer">
    <p>&copy; 2025 Student Management System. All rights reserved.</p>
  </div>

    </body>
</html>
