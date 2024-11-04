package test;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import test.DBUtil;

@WebServlet("/ViewEnrollmentServlet")
public class ViewEnrollmentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Establish database connection
            Connection con = DBUtil.getConnection();

            // Execute SQL query to fetch enrollment records
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM StudentCourse");
            ResultSet rs = stmt.executeQuery();

            // Generate HTML table to display enrollment records
            out.println("<html><head><title>Enrollment Records</title></head><body>");
            out.println("<h2>Enrollment Records</h2>");
            out.println("<table border='1'><tr><th>Enrollment ID</th><th>Student ID</th><th>Course ID</th><th>Enrollment Date</th></tr>");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("enrollment_id") + "</td>");
                out.println("<td>" + rs.getInt("student_id") + "</td>");
                out.println("<td>" + rs.getInt("course_id") + "</td>");
                out.println("<td>" + rs.getDate("enrollment_date") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            // Close resources
            rs.close();
            stmt.close();
            con.close();

            out.println("</body></html>");
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
