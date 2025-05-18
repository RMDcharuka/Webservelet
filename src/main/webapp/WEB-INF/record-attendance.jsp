<%@ page import="java.util.logging.Logger" %>
<%@ page import="java.util.logging.Level" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  final Logger logger = Logger.getLogger("dashboard");
  try {
    request.setAttribute("subjects", SubjectDAO.getNamesAndSlugs());
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
  <title>Attendance Form</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/layout/style.css"/>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
  <style>
    .form-container {
      max-width: 500px;
      margin: 40px auto;
      background: white;
      padding: 30px;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }

    .form-container h1 {
      text-align: center;
      margin-bottom: 20px;
      color: #123a6d;
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

    #message {
      text-align: center;
      margin-top: 15px;
      font-weight: bold;
    }
  </style>
</head>
<body>
<%@include file="components/sidebar.jsp"%>

<main class="main-content">
  <header class="topbar">
    <h1>Attendance Form</h1>
  </header>

  <div class="form-container">
    <form method="post">
      <label for="studentId">Student ID</label>
      <input type="text" id="studentId" name="studentId" required>

      <label for="courses">Courses</label>
      <select id="courses" name="courses" required>
        <option value="">Choose here</option>
        <c:forEach var="subject" items="${subjects}">
          <option value="${subject.id}">${subject.name}</option>
        </c:forEach>
      </select>

      <div class="buttons">
        <button class="submit-btn" type="submit">Submit</button>
        <button class="cancel-btn" type="reset">Cancel</button>
      </div>
    </form>
    <div id="message"></div>
  </div>
</main>

<script>
  document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const submitBtn = document.querySelector(".submit-btn");
    const cancelBtn = document.querySelector(".cancel-btn");
    const messageDiv = document.getElementById("message");

    form.addEventListener("submit", async function (e) {
      e.preventDefault();

      messageDiv.textContent = "";
      messageDiv.style.color = "red";

      submitBtn.disabled = true;
      cancelBtn.disabled = true;
      const originalText = submitBtn.textContent;
      submitBtn.textContent = "Processing...";

      const studentId = document.getElementById("studentId").value;
      const subjectId = document.getElementById("courses").value;

      try {
        const tokenRes = await fetch("/api/auth/rotate", { method: "POST", withCredential: true });
        if (tokenRes.status !== 200) {
          const err = await tokenRes.json();
          messageDiv.textContent = err.message || "Failed to get token";
          return;
        }

        const token = tokenRes.headers.get("X-Access-Token");
        if (!token) {
          messageDiv.textContent = "No token received.";
          return;
        }

        const attendanceRes = await fetch("/api/subject/record-attendance", {
          method: "POST",
          withCredential: true,
          headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token,
          },
          body: JSON.stringify({
            student_id: parseInt(studentId),
            subject_id: parseInt(subjectId)
          }),
        });

        const result = await attendanceRes.json();

        if (attendanceRes.status !== 200) {
          if (result.message === "foreign key violation") {
            messageDiv.textContent = "Student with ID " + studentId + " is not enrolled in this subject.";
          } else {
            messageDiv.textContent = result.message || "Failed to record attendance.";
          }
        } else {
          messageDiv.style.color = "green";
          messageDiv.textContent = "Attendance recorded successfully!";
          form.reset();
        }
      } catch (err) {
        messageDiv.textContent = "An unexpected error occurred.";
      } finally {
        submitBtn.disabled = false;
        cancelBtn.disabled = false;
        submitBtn.textContent = originalText;
      }
    });
  });
</script>
</body>
</html>