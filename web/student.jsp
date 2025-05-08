<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Student Dashboard</title>
  <link rel="stylesheet" href="style.css"/>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
  <style>
    /* Container */
    .container {
      width: 100%;
      max-width: 1300px;
      background: white;
      padding: 30px;
      border-radius: 15px;
      box-shadow: 0 8px 20px rgba(0,0,0,0.1);
    }

    /* Form Title */
    .student-form h2 {
      text-align: center;
      margin-bottom: 20px;
      color: #333;
    }

    /* Form Group */
    .form-group {
      display: flex;
      gap: 20px;
      margin-bottom: 20px;
    }

    /* Input Field */
    .input-field {
      position: relative;
      flex: 1;
    }

    .input-field input,
    .input-field select,
    .input-field textarea {
      width: 100%;
      padding: 14px 10px 14px 10px;
      border: 1px solid #ccc;
      border-radius: 8px;
      outline: none;
      background: none;
      font-size: 16px;
    }

    .input-field textarea {
      resize: none;
    }

    /* For file input field (photo) */
    .upload-field input[type="file"] {
      padding: 14px;
      font-size: 16px;
      background: white;
      border: 1px dashed #007bff;
      cursor: pointer;
    }

    .input-field label {
      position: absolute;
      top: 14px;
      left: 14px;
      background: white;
      padding: 0 5px;
      color: #888;
      transition: 0.2s;
      pointer-events: none;
    }

    /* Floating Label Effect */
    .input-field input:focus + label,
    .input-field input:not(:placeholder-shown) + label,
    .input-field select:focus + label,
    .input-field select:not([value=""]) + label,
    .input-field textarea:focus + label,
    .input-field textarea:not(:placeholder-shown) + label {
      top: -8px;
      left: 10px;
      font-size: 12px;
      color: #007bff;
    }

    /* Full Width */
    .full-width {
      margin-bottom: 20px;
    }

    /* Buttons */
    .button-group {
      text-align: center;
    }

    .btn {
      padding: 12px 30px;
      background: #007bff;
      color: white;
      border: none;
      border-radius: 8px;
      margin: 10px;
      cursor: pointer;
      font-size: 16px;
      transition: background 0.3s;
    }

    .btn:hover {
      background: #0056b3;
    }

    
    

    .btn-secondary:hover {
      background:  #0056b3      ;
;
    }

    /* Responsive */
    @media (max-width: 600px) {
      .form-group {
        flex-direction: column;
      }
    }
  </style>
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
    <div class="container">
      <form class="student-form" action="processStudent.jsp" method="post" enctype="multipart/form-data">
        <h2>Student Registration Form</h2>
    
        <div class="form-group">
          <div class="input-field">
            <input type="text" id="firstName" name="firstName" required>
            <label for="firstName">First Name</label>
          </div>
          <div class="input-field">
            <input type="text" id="lastName" name="lastName" required>
            <label for="lastName">Last Name</label>
          </div>
        </div>
    
        <div class="form-group">
          <div class="input-field">
            <input type="email" id="email" name="email" required>
            <label for="email">Email</label>
          </div>
          <div class="input-field">
            <input type="tel" id="phone" name="phone" required>
            <label for="phone">Phone Number</label>
          </div>
        </div>
    
        <div class="form-group">
          <div class="input-field">
            <input type="date" id="dob" name="dob" required>
            <label for="dob">Date of Birth</label>
          </div>
          <div class="input-field">
            <select id="gender" name="gender" required>
              <option value="" disabled selected>Select Gender</option>
              <option value="male">Male</option>
              <option value="female">Female</option>
              <option value="other">Other</option>
            </select>
            <label for="gender">Gender</label>
          </div>
        </div>
    
        <div class="input-field full-width">
          <textarea id="address" name="address" rows="4" required></textarea>
          <label for="address">Address</label>
        </div>
    
        <div class="form-group">
          <div class="input-field">
            <input type="text" id="course" name="course" required>
            <label for="course">Course</label>
          </div>
          <div class="input-field">
            <input type="text" id="studentId" name="studentId" required>
            <label for="studentId">Student ID</label>
          </div>
        </div>
    
        <div class="input-field full-width upload-field">
          <input type="file" id="photo" name="photo" accept="image/*" required>
          <label for="photo" class="upload-label">Upload Student Photo</label>
        </div>

        <div class="button-group">
          <button type="submit" class="btn">Register</button>
          <button type="reset" class="btn btn-secondary">Update</button>
          <button type="reset" class="btn btn-secondary">Delete</button>
        </div>
      </form>
    </div>
  </main>

</body>
</html>