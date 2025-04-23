package com.kopinfotech.blog.controller;

import com.kopinfotech.blog.dao.UserDAO;
import com.kopinfotech.blog.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

// Maps this servlet to the /signup URL path
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Recommended for servlets
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO(); // Initialize DAO
    }

    // Handles GET requests (e.g., when user navigates to /signup)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to the signup JSP page
        // We'll create signup.jsp later
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/signup.jsp");
        dispatcher.forward(request, response);
    }

    // Handles POST requests (e.g., when user submits the signup form)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Retrieve form parameters
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String studentId = request.getParameter("studentId");
        String role = request.getParameter("role"); // "CR" or "NON-CR"

        // Basic Validation (Add more robust validation as needed)
        if (name == null || name.isEmpty() || username == null || username.isEmpty() ||
            email == null || email.isEmpty() || password == null || password.isEmpty() ||
            studentId == null || studentId.isEmpty() || role == null || role.isEmpty()) {

            request.setAttribute("errorMessage", "All fields are required.");
            doGet(request, response); // Show form again with error
            return;
        }

        if (!"CR".equals(role) && !"NON-CR".equals(role)) {
             request.setAttribute("errorMessage", "Invalid role selected.");
             doGet(request, response); // Show form again with error
             return;
        }

        // 2. Create a User object
        User newUser = new User();
        newUser.setName(name);
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password); // Password will be hashed by DAO (currently placeholder)
        newUser.setStudentId(studentId);
        newUser.setRole(role);

        // 3. Attempt to add the user via DAO
        boolean success = userDAO.addUser(newUser);

        // 4. Redirect based on success
        if (success) {
            // Redirect to login page after successful signup
            // Set a success message (optional, using session or query param)
            response.sendRedirect(request.getContextPath() + "/login?signup=success");
        } else {
            // Set an error message (e.g., username/email/studentId already exists)
            // TODO: Add more specific error handling based on DAO feedback if possible
            request.setAttribute("errorMessage", "Signup failed. Username, Email, or Student ID might already exist.");
            // Forward back to the signup form with the error message
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/signup.jsp");
            dispatcher.forward(request, response);
        }
    }
} 