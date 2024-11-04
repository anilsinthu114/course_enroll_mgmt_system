package test;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.PrintWriter;

@WebServlet("/EnrollStudentServlet")
public class EnrollStudentServlet extends HttpServlet {
//	@Resource(name="jdbc/enrollmentdb")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String studentId = request.getParameter("studentId");
        String courseId = request.getParameter("courseId");
        String enrollmentDate = request.getParameter("enrollmentDate");

        try {
            Connection con = DBUtil.getConnection();
             response.getWriter().println("Database Connected");
            String query = "INSERT INTO StudentCourse (student_id, course_id, enrollment_date) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, studentId);
            pst.setString(2, courseId);
            pst.setString(3, enrollmentDate);
            pst.executeUpdate();
response.getWriter().println("Data Successfully Inserted");
            con.close();
            response.sendRedirect("viewEnrollment.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
