<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Kanri Blog</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 50%, #dee2e6 100%);
        }
        .container {
            width: 100%;
            max-width: 400px;
            padding: 2.5em;
            background: white;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .container:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 30px rgba(0, 0, 0, 0.15);
        }
        h2 {
            color: #212529;
            text-align: center;
            margin-bottom: 1.5em;
            font-size: 2em;
            font-weight: 600;
            background: linear-gradient(135deg, #212529 0%, #495057 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }
        .form-group {
            margin-bottom: 1.5em;
        }
        label {
            display: block;
            margin-bottom: 0.5em;
            color: #343a40;
            font-weight: 500;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 0.8em;
            border: 2px solid #e9ecef;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 1em;
            transition: all 0.3s ease;
            background-color: #f8f9fa;
        }
        input[type="text"]:focus, input[type="password"]:focus {
            outline: none;
            border-color: #adb5bd;
            background-color: white;
            box-shadow: 0 0 0 3px rgba(173, 181, 189, 0.2);
        }
        button {
            width: 100%;
            padding: 1em;
            background: linear-gradient(135deg, #212529 0%, #343a40 100%);
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1em;
            font-weight: 600;
            transition: all 0.3s ease;
        }
        button:hover {
            background: linear-gradient(135deg, #343a40 0%, #212529 100%);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(33, 37, 41, 0.3);
        }
        .error {
            color: #dc3545;
            margin-bottom: 1em;
            padding: 0.8em;
            background-color: #f8d7da;
            border-radius: 5px;
            border-left: 4px solid #dc3545;
        }
        .success {
            color: #28a745;
            margin-bottom: 1em;
            padding: 0.8em;
            background-color: #d4edda;
            border-radius: 5px;
            border-left: 4px solid #28a745;
        }
        p {
            text-align: center;
            margin-top: 1.5em;
            color: #6c757d;
        }
        a {
            color: #212529;
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease;
            position: relative;
        }
        a:hover {
            color: #495057;
        }
        a::after {
            content: '';
            position: absolute;
            width: 100%;
            height: 2px;
            bottom: -2px;
            left: 0;
            background: linear-gradient(135deg, #212529 0%, #495057 100%);
            transform: scaleX(0);
            transform-origin: right;
            transition: transform 0.3s ease;
        }
        a:hover::after {
            transform: scaleX(1);
            transform-origin: left;
        }
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