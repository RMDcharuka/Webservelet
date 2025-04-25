package dao;

import java.sql.*;

public class userDAO {

    String dbUrl = "jdbc:mysql://localhost:3306/project_db";
    String Username = "root";
    String Password = "";

    // Method to create/register a new user
    public String createUser(String name, String email, String password) {
        String status = "unsuccess";

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to DB
            Connection conn = DriverManager.getConnection(dbUrl, Username, Password);

            // SQL query
            String query = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";

            // Insert data
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                status = "success";
            }

            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return status;
    }

    // Method to validate login credentials
    public boolean validateUser(String email, String password) {
        boolean isValid = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbUrl, Username, Password);

            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                isValid = true;
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return isValid;
    }
}
