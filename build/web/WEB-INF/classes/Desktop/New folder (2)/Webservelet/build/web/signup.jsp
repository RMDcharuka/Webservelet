<%-- 
    Document   : signup
    Created on : May 8, 2025, 11:16:05 AM
    Author     : user
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Sign Up Form</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>

  <div class="form-box">
    <h2><span>Sign Up</span></h2>
    <form action="loginServlet" method="POST">
      <input name="name" type="text" placeholder="Enter your name" required>
      <input name="email" type="email" placeholder="Enter your email" required>
      <input name="password" type="password" placeholder="Create password" required>
      <div class="checkbox">
        <input type="checkbox" id="terms" required>
        <label for="terms">I accept all terms & conditions</label>
      </div>
      <button type="submit">Sign Up Now</button>
      <p class="login-link">Already have an account? <a href="login.jsp">Login now</a></p>
    </form>
  </div>

  <div class="side-text">
    <h1>Create your own<br>Account now!</h1>
  </div>

</body>
</html>
