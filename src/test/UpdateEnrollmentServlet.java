package test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@WebServlet("/UpdateEnrollmentServlet")
public class UpdateEnrollmentServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get the enrollment ID from the request parameter
            int enrollmentId = Integer.parseInt(request.getParameter("id"));

            // Connect to the database
            Connection connection = DBUtil.getConnection();

            // Prepare a query to retrieve the enrollment details
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM StudentCourse WHERE enrollment_id = ?");
            ps.setInt(1, enrollmentId);

            // Execute the query
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // If the enrollment exists, create an Enrollment object
                Enrollment enrollment = new Enrollment(
                        rs.getInt("enrollment_id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getDate("enrollment_date")
                );

                // Set the enrollment object as an attribute in the request
                request.setAttribute("enrollment", enrollment);

                // Forward the request to the updateEnrollment.jsp for updating the details
                request.getRequestDispatcher("updateEnrollment.jsp").forward(request, response);
            } else {
                // If no enrollment found, set an error message and redirect to a suitable page
                request.setAttribute("errorMessage", "Enrollment not found");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }

            // Close database resources
            rs.close();
            ps.close();
            connection.close();

        } catch (NumberFormatException e) {
            // Handle invalid enrollment ID format
            request.setAttribute("errorMessage", "Invalid enrollment ID format");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            // Handle database errors gracefully
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        int enrollmentId = Integer.parseInt(request.getParameter("enrollmentId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        String enrollmentDate = request.getParameter("enrollmentDate");

        try {
            // Update the enrollment record in the database
            Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE StudentCourse SET student_id=?, course_id=?, enrollment_date=? WHERE enrollment_id=?");
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.setString(3, enrollmentDate);
            ps.setInt(4, enrollmentId);
            int rowsUpdated = ps.executeUpdate();

            ps.close();
            connection.close();

            if (rowsUpdated > 0) {
                // Redirect to viewEnrollment.jsp if update successful
                response.sendRedirect("viewEnrollment.jsp");
            } else {
                // Forward to error.jsp if update fails
                request.setAttribute("errorMessage", "Failed to update enrollment record");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            // Handle invalid input format
            request.setAttribute("errorMessage", "Invalid input format");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            // Handle database errors gracefully
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            e.printStackTrace();
        }
    }
}
