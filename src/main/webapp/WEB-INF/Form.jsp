<%-- 
    Document   : Form
    Created on : Apr 28, 2025, 3:48:13 PM
    Author     : hp
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Attendance Form</title>
  
  <style>
    body {
      font-family: Arial, sans-serif;
      padding: 30px;
      background: #123a6d;
    }   

    .container {
      max-width: 400px;
      margin: auto;
      padding: 20px;
      background: white;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }

    h1 {
      text-align: center;
      margin-bottom: 20px;
      color: #000;
    }
  
    label {
      display: block;
      margin-top: 15px;
      font-weight: bold;
    }

    input[type="text"], select {
      width: 100%;
      padding: 8px;
      margin-top: 5px;
      border-radius: 5px;
      border: 1px solid #ccc;
    }

    .date-fields {
      display: flex;
      justify-content: space-between;
      gap: 10px;
    }

    .date-fields input {
      flex: 1;
    }

    .buttons {
      display: flex;
      justify-content: space-between;
      margin-top: 20px;
    }

    button {
      width: 48%;
      padding: 10px;
      border: none;
      border-radius: 5px;
      font-weight: bold;
      cursor: pointer;
    }

    .submit-btn {
      background-color: #123a6d;
      color: white;
    }

    .cancel-btn {
      background-color: #f44336;
      color: white;
    }
  </style>

</head>

<body>

  <div class="container">
    <h1>Attendance Form</h1>

    <form action="AttendanceServlet" method="post">
      <label for="studentId">Student ID</label>
      <input type="text" id="studentId" name="studentId" required>

      <label for="courses">Courses</label>
      <select id="courses" name="courses" required>
        <option value="">Choose here</option>
        <option value="Data Science">Data Science</option>
        <option value="Cyber Security">Cyber Security</option>
        <option value="Cloud Computing">Cloud Computing</option>
      </select>

      <label>Date</label>
      <div class="date-fields">
        <input type="text" placeholder="Date" name="date" required>
        <input type="text" placeholder="Month" name="month" required>
        <input type="text" placeholder="Year" name="year" required>
      </div>

      <div class="buttons">
        <button class="submit-btn" type="submit">Submit</button>
        <button class="cancel-btn" type="reset">Cancel</button>
      </div>
    </form>
  </div>

</body>
</html>
