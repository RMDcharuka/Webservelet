<!DOCTYPE html>
<!--
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Html.html to edit this template
-->

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up Form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/signup/style.css">
</head>


<div class="form-box">
    <h2><span>Sign Up</span></h2>
    <form action ="logingServelet" method="POST">
        <label>
            <input name="name" type="text" placeholder="Enter your name" required>
        </label>
        <label>
            <input name="email" type="text" placeholder="Enter your email" required>
        </label>
        <label>
            <input name="password" type="text" placeholder="Create password" required>
        </label>
        <div class="checkbox">
            <input type="checkbox" id="terms">
            <label for="terms">I accept all terms & conditions</label>
        </div>
        <button type="submit">Sign Up Now</button>
        <p class="login-link">Already have an account? <a href="login.jsp">Login now</a></p>
    </form>
</div>
<div class="side-text">
    <h1>Create your own<br>Account now!</h1>
</div>



