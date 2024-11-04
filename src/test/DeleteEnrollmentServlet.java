package test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteEnrollmentServlet")
public class DeleteEnrollmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String enrollmentId = request.getParameter("enrollment_id");

        try {
            // DEBUG: Check for null value
            if (enrollmentId == null) {
                throw new Exception("enrollmentID is null");  // Explicitly throw an exception for debugging
            }

            // Establishing database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection con = DBUtil.getConnection();

            // Check if the enrollment ID exists in the database
            String checkQuery = "SELECT * FROM StudentCourse WHERE enrollment_id=?";
            PreparedStatement checkStatement = con.prepareStatement(checkQuery);
            checkStatement.setString(1, enrollmentId);
            ResultSet resultSet = checkStatement.executeQuery();
            
            // If enrollment ID doesn't exist, redirect to error page
            if (!resultSet.next()) {
                request.setAttribute("error", "Enrollment ID " + enrollmentId + " does not exist.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }
            
            // Deleting record from the database
            String deleteQuery = "DELETE FROM StudentCourse WHERE enrollment_id=?";
            PreparedStatement deleteStatement = con.prepareStatement(deleteQuery);
            deleteStatement.setString(1, enrollmentId);
            deleteStatement.executeUpdate();

            // Closing resources
            deleteStatement.close();
            checkStatement.close();
            resultSet.close();
            con.close();

            // Redirecting to viewEnrollment.jsp after successful deletion
            response.sendRedirect("viewEnrollment.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while deleting the enrollment: " + e.getMessage());
            try {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            } catch (ServletException | IOException e1) {
                e1.printStackTrace();
            }  // Forward to error page
        }
    }
}
