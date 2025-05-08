<%-- 
    Document   : recode1
    Created on : May 8, 2025, 11:10:05 AM
    Author     : user
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Student Dashboard</title>
  <link rel="stylesheet" href="style2.css"/>
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
            <li><a href="recode1.jsp">Data Science</a></li>
            <li><a href="recode2.jsp">Cyber security</a></li>
            <li><a href="recods3.jsp">Cloud Computing
