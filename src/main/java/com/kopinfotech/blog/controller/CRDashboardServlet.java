package com.kopinfotech.blog.controller;

import com.kopinfotech.blog.dao.PostDAO;
import com.kopinfotech.blog.dao.UserDAO;
import com.kopinfotech.blog.model.Post;
import com.kopinfotech.blog.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/cr/dashboard")
public class CRDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private PostDAO postDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
        postDAO = new PostDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Double-check authentication and role (Filter should handle primary auth)
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (!"CR".equals(loggedInUser.getRole())) {
            // If a non-CR user somehow accesses this, redirect them (or show error)
             response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied.");
             return;
        }

        // Fetch data needed for the dashboard
        List<User> allUsers = userDAO.getAllUsers(); // For name card
        List<Post> allPosts = postDAO.getAllPosts(); // For post list

        // Set data as request attributes to be accessed by JSP
        request.setAttribute("allUsers", allUsers);
        request.setAttribute("allPosts", allPosts);
        request.setAttribute("currentUser", loggedInUser); // Pass current user info if needed

        // Forward to the CR dashboard JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/cr_dashboard.jsp");
        dispatcher.forward(request, response);
    }

    // doPost might be used later if dashboard has forms (e.g., search)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
} 