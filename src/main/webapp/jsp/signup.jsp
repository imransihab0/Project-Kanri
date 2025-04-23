<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- For conditional logic --%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up - Kanri Blog</title>
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
        input[type="text"], 
        input[type="email"], 
        input[type="password"], 
        select {
            width: 100%;
            padding: 0.8em;
            border: 2px solid #e9ecef;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 1em;
            transition: all 0.3s ease;
            background-color: #f8f9fa;
        }
        select {
            appearance: none;
            background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='%23212529' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'%3e%3c/polyline%3e%3c/svg%3e");
            background-repeat: no-repeat;
            background-position: right 1rem center;
            background-size: 1em;
            padding-right: 2.5em;
        }
        input[type="text"]:focus, 
        input[type="email"]:focus, 
        input[type="password"]:focus, 
        select:focus {
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