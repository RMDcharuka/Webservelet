<jsp:useBean id="faculty" scope="request" type="java.lang.String"/>

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
            <h1>${faculty} Students Records</h1>
         </header>
         <section class="cards-container">
            <div class="card">
               <i class="fas fa-user-graduate"></i>
               <h3>Total Students</h3>
               <p>400</p>
            </div>
            <div class="card">
               <i class="fas fa-file-alt"></i>
               <h3>Avg Marks</h3>
               <p>20</p>
            </div>
            <div class="card">
               <i class="fas fa-user-clock"></i>
               <h3>Avg Attendance</h3>
               <p>86%</p>
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
                     <th>Attendance</th>
                  </tr>
               </thead>
               <tbody>
                  <tr>
                     <td>001</td>
                     <td>John Doe</td>
                     <td>Mathematics</td>
                     <td>85</td>
                     <td>92%</td>
                  </tr>
                  <tr>
                     <td>002</td>
                     <td>Jane Smith</td>
                     <td>Physics</td>
                     <td>78</td>
                     <td>88%</td>
                  </tr>
                  <tr>
                     <td>003</td>
                     <td>Ali Khan</td>
                     <td>Chemistry</td>
                     <td>90</td>
                     <td>95%</td>
                  </tr>
               </tbody>
            </table>
         </section>
      </main>
   </body>
</html>
