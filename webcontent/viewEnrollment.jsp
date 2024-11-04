<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.naming.*, javax.sql.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>View Service</title>
</head>
<body>



	<h2>View Service</h2>

	<table border='1'>
		<tr>
			<th>Enrollment ID</th>
			<th>Student ID</th>
			<th>Course UD</th>
			<th>Enrollment Date</th>

			<!-- Other table headers -->
			<th>Actions</th>
		</tr>
		<%
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EnrollmentDB");
			Connection con = ds.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM StudentCourse");

			while (rs.next()) {
				int enrollmentId = rs.getInt("enrollment_id");
				int studentId = rs.getInt("student_id");
				int courseId = rs.getInt("course_id");
				java.sql.Date date = rs.getDate("enrollment_date");
				String dateString = date.toString();
		%>
		<tr>
			<td><%=enrollmentId%></td>
			<td><%=studentId%></td>
			<td><%=courseId%></td>
			<td><%=dateString%></td>

			<td><a href="updateEnrollment.jsp?id=<%=enrollmentId%>">Edit</a> |
				<a href="deleteEnrollment.jsp?id=<%=enrollmentId%>">Delete</a></td>
		</tr>
		<%
		}
		con.close();
		} catch (Exception e) {
		out.println("Error: " + e.getMessage());
		}
		%>
	</table>
</body>
</html>
