package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnrollmentDAO {
    private static Connection connection;

    // Constructor
    public EnrollmentDAO(Connection connection) {
        EnrollmentDAO.connection = connection;
    }

    // Update enrollment method
    public boolean updateEnrollment(int enrollmentId, int studentId, int courseId, Date enrollmentDate) throws SQLException {
        String query = "UPDATE StudentCourse SET student_id = ?, course_id = ?, enrollment_date = ? WHERE enrollment_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, studentId);
            pst.setInt(2, courseId);
            pst.setDate(3, enrollmentDate);
            pst.setInt(4, enrollmentId);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public static Enrollment getEnrollmentById(int enrollmentId) throws SQLException {
        String query = "SELECT * FROM StudentCourse WHERE enrollment_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, enrollmentId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int studentId = rs.getInt("student_id");
                    int courseId = rs.getInt("course_id");
                    Date enrollmentDate = rs.getDate("enrollment_date");
                    // Create and return an Enrollment object
                    return new Enrollment(enrollmentId, studentId, courseId, enrollmentDate);
                } else {
                    // No enrollment found for the given ID
                    return null;
                }
            }
        }
    }

    // Other methods like insertEnrollment(), deleteEnrollment(), etc. can be added here
}
