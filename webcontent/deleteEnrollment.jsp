<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Service</title>
</head>
<body>
    <%@ page import="java.sql.*" %>
    <%@ page import="javax.naming.*, javax.sql.*" %>
    <%@ page import="java.util.*" %>

    <h2>Delete Service</h2>
    
    <% 
    // Retrieve the enrollment_id parameter passed from viewService.jsp
    String enrollmentId = request.getParameter("id"); 
    %>
    
    <p>Enrollment ID: <%= enrollmentId %></p>
    
    <form action="DeleteEnrollmentServlet" method="POST">
        Are you sure you want to delete enrollment <%= enrollmentId %>?<br><br>
        <input type="hidden" name="enrollment_id" value="<%= enrollmentId %>">
        <input type="submit" value="Delete">
    </form>
</body>
</html>
