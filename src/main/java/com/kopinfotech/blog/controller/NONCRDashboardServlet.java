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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@WebServlet("/noncr/dashboard")
public class NONCRDashboardServlet extends HttpServlet {
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

        // Authentication check (Filter should handle primary auth)
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        // NON-CR role specifically is not strictly required here, as CR might also view this page.
        // The filter ensures they are logged in. We just need to display data.

        // Fetch data needed for the dashboard
        List<User> allUsers = userDAO.getAllUsers(); // For name card
        List<Post> allPosts = postDAO.getAllPosts(); // For post list

        // Create a Map for easy author lookup in JSP (UserID -> User Object)
        Map<Integer, User> userMap = allUsers.stream()
                .collect(Collectors.toMap(User::getUserId, Function.identity()));

        // Set data as request attributes to be accessed by JSP
        request.setAttribute("allUsers", allUsers);
        request.setAttribute("allPosts", allPosts);
        request.setAttribute("userMap", userMap);
        request.setAttribute("currentUser", loggedInUser); // Pass current user info

        // Forward to the NON-CR dashboard JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/non_cr_dashboard.jsp");
        dispatcher.forward(request, response);
    }

     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Usually dashboards are GET, but handle POST same way for simplicity now
        doGet(request, response);
    }

} 