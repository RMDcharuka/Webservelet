
package servelet;

import dao.userDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "logingServelet", urlPatterns = {"/logingServelet"})
public class logingServelet extends HttpServlet {
    


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    userDAO ul = new userDAO();
    String status = ul.createUser(name,email,password);
    
    if(status == "success"){
        response.sendRedirect("login.html");
    }else{
        response.sendRedirect("index.html");
    }
    
    
    }
}
