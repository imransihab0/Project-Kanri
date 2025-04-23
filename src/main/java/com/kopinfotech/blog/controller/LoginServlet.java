package com.kopinfotech.blog.controller;

import com.kopinfotech.blog.dao.UserDAO;
import com.kopinfotech.blog.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    // Handles GET requests (display login page)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if there's a signup success message
        if ("success".equals(request.getParameter("signup"))) {
            request.setAttribute("successMessage", "Signup successful! Please log in.");
        }
        // Forward to login.jsp (to be created)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login.jsp");
        dispatcher.forward(request, response);
    }

    // Handles POST requests (process login form)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String errorMessage = null;

        // Basic validation
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            errorMessage = "Username and password are required.";
        } else {
            // Find user by username
            User user = userDAO.findUserByUsername(username);

            // Check if user exists and password matches (using placeholder checkPassword)
            if (user != null && userDAO.checkPassword(password, user.getPassword())) {
                // --- Login Successful ---

                // 1. Create a session or get the existing one
                HttpSession session = request.getSession(true); // true = create if doesn't exist

                // 2. Store user information in the session
                session.setAttribute("loggedInUser", user); // Store the whole user object (or relevant parts)
                session.setAttribute("userRole", user.getRole());
                session.setAttribute("username", user.getUsername());

                // 3. Redirect to the appropriate dashboard based on role
                if ("CR".equals(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/cr/dashboard"); // Define this path later
                } else if ("NON-CR".equals(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/noncr/dashboard"); // Define this path later
                } else {
                    // Should not happen if validation is correct, but handle defensively
                    errorMessage = "Invalid user role found.";
                }

            } else {
                // --- Login Failed ---
                errorMessage = "Invalid username or password.";
            }
        }

        // If there was an error or login failed, forward back to login page
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login.jsp");
            dispatcher.forward(request, response);
        }
        // If successful, redirection already happened, so no further action here
    }
} 