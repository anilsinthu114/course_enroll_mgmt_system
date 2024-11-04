<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="test.Enrollment" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Enrollment</title>
</head>
<body>
    <h2>Update Enrollment</h2>
    <form action="UpdateEnrollmentServlet" method="post">
        <% 
            // Retrieve the enrollment object from the request scope
            Enrollment enrollment = (Enrollment) request.getAttribute("enrollment");
            
            // Check if the enrollment object is not null
            if (enrollment != null) { 
        %>
            <input type="hidden" name="enrollmentId" value="<%= enrollment.getEnrollmentId() %>">
            
            <label for="studentId">Student ID:</label>
            <input type="text" id="studentId" name="studentId" value="<%= enrollment.getStudentId() %>" required><br><br>
            <label for="courseId">Course ID:</label>
            <input type="text" id="courseId" name="courseId" value="<%= enrollment.getCourseId() %>" required><br><br>
            <label for="enrollmentDate">Enrollment Date:</label>
            <input type="date" id="enrollmentDate" name="enrollmentDate" value="<%= enrollment.getEnrollmentDate() %>" required><br><br>
            <button type="submit" name="update">Update</button>
        <% 
            } else { 
        %>
            <p>Error: Enrollment data not available.</p>
        <% 
            } 
        %>
    </form>
</body>
</html>
