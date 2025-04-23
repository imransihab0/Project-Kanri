<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- For conditional logic --%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up - Kanri Blog</title>
    <%-- Basic Styling - Consider adding a CSS file later --%>
    <style>
        body { font-family: sans-serif; margin: 2em; }
        .container { max-width: 400px; margin: auto; padding: 2em; border: 1px solid #ccc; border-radius: 5px; }
        .form-group { margin-bottom: 1em; }
        label { display: block; margin-bottom: 0.5em; }
        input[type="text"], input[type="email"], input[type="password"], select {
            width: 100%;
            padding: 0.5em;
            border: 1px solid #ccc;
            box-sizing: border-box; /* Include padding in width */
        }
        button { padding: 0.7em 1.5em; background-color: #007bff; color: white; border: none; border-radius: 3px; cursor: pointer; }
        button:hover { background-color: #0056b3; }
        .error { color: red; margin-bottom: 1em; }
    </style>
</head>
<body>

    <div class="container">
        <h2>Sign Up</h2>

        <%-- Display error message if present --%>
        <c:if test="${not empty errorMessage}">
            <p class="error">${errorMessage}</p>
        </c:if>

        <%-- Signup Form --%>
        <form action="${pageContext.request.contextPath}/signup" method="post">
            <div class="form-group">
                <label for="name">Full Name:</label>
                <input type="text" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="studentId">Student ID:</label>
                <input type="text" id="studentId" name="studentId" required>
            </div>
            <div class="form-group">
                <label for="role">Role:</label>
                <select id="role" name="role" required>
                    <option value="" disabled selected>-- Select Role --</option>
                    <option value="CR">CR (Class Representative)</option>
                    <option value="NON-CR">NON-CR (Student)</option>
                </select>
            </div>
            <button type="submit">Sign Up</button>
        </form>

        <p style="margin-top: 1em;">Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a></p>
    </div>

</body>
</html> 