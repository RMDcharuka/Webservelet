package servelet;

import dao.userDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginValidationServlet", urlPatterns = {"/LoginValidationServlet"})
public class LoginValidationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get email and password from the login form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Call DAO to validate user
        userDAO dao = new userDAO();
        boolean isValidUser = dao.validateUser(email, password);

        // Redirect based on login result
        if (isValidUser) {
            response.sendRedirect("dashboard.html"); // Success login
        } else {
            response.sendRedirect("login.html"); // Invalid login
        }
    }
}
