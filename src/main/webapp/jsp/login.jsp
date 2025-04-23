<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Kanri Blog</title>
    <%-- Reusing basic styling from signup.jsp --%>
    <style>
        body { font-family: sans-serif; margin: 2em; }
        .container { max-width: 400px; margin: auto; padding: 2em; border: 1px solid #ccc; border-radius: 5px; }
        .form-group { margin-bottom: 1em; }
        label { display: block; margin-bottom: 0.5em; }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 0.5em;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }
        button { padding: 0.7em 1.5em; background-color: #007bff; color: white; border: none; border-radius: 3px; cursor: pointer; }
        button:hover { background-color: #0056b3; }
        .error { color: red; margin-bottom: 1em; }
        .success { color: green; margin-bottom: 1em; }
    </style>
</head>
<body>

    <div class="container">
        <h2>Login</h2>

        <%-- Display error message if present --%>
        <c:if test="${not empty errorMessage}">
            <p class="error">${errorMessage}</p>
        </c:if>

        <%-- Display success message if present (e.g., after signup) --%>
        <c:if test="${not empty successMessage}">
            <p class="success">${successMessage}</p>
        </c:if>

        <%-- Login Form --%>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit">Login</button>
        </form>

        <p style="margin-top: 1em;">Don't have an account? <a href="${pageContext.request.contextPath}/signup">Sign up here</a></p>
    </div>

</body>
</html> 