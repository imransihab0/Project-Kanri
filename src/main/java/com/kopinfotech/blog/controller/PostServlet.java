package com.kopinfotech.blog.controller;

import com.kopinfotech.blog.dao.PostDAO;
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
import java.sql.SQLException; // Needed?

// Handle various post-related actions
@WebServlet(urlPatterns = {"/posts/new", "/posts/save", "/posts/edit", "/posts/delete"})
public class PostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PostDAO postDAO;

    @Override
    public void init() {
        postDAO = new PostDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Route POST requests based on the path
        String action = request.getServletPath(); // Gets the specific path like /posts/save

        // --- Security Check: Ensure user is CR --- (Filter handles basic auth)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
             response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (!"CR".equals(currentUser.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. Only CR users can manage posts.");
            return;
        }
        // --- End Security Check ---

        try {
            switch (action) {
                case "/posts/save":
                    savePost(request, response, currentUser);
                    break;
                case "/posts/delete":
                    deletePost(request, response);
                    break;
                default:
                     response.sendRedirect(request.getContextPath() + "/cr/dashboard"); // Redirect if unknown POST action
            }
        } catch (Exception e) {
            // Basic error handling
             System.err.println("Error in PostServlet doPost: " + e.getMessage());
             e.printStackTrace();
             request.setAttribute("errorMessage", "An error occurred while processing your request.");
             // Redirect to a safe place, maybe dashboard
             response.sendRedirect(request.getContextPath() + "/cr/dashboard?error=true");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Route GET requests based on the path
        String action = request.getServletPath();

         // --- Security Check: Ensure user is CR --- (Filter handles basic auth)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
             response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (!"CR".equals(currentUser.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. Only CR users can manage posts.");
            return;
        }
        // --- End Security Check ---

        try {
            switch (action) {
                case "/posts/new":
                    showNewForm(request, response);
                    break;
                case "/posts/edit":
                    showEditForm(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/cr/dashboard"); // Redirect if unknown GET action
            }
        } catch (Exception e) {
             System.err.println("Error in PostServlet doGet: " + e.getMessage());
             e.printStackTrace();
             request.setAttribute("errorMessage", "An error occurred while processing your request.");
             response.sendRedirect(request.getContextPath() + "/cr/dashboard?error=true");
        }
    }

    // --- Action Methods ---

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set an empty post object for the form (optional, good practice)
        request.setAttribute("post", new Post());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/post_form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Post existingPost = postDAO.getPostById(id);
        if (existingPost != null) {
            request.setAttribute("post", existingPost);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/post_form.jsp");
            dispatcher.forward(request, response);
        } else {
            // Handle post not found
            response.sendRedirect(request.getContextPath() + "/cr/dashboard?error=postNotFound");
        }
    }

    private void savePost(HttpServletRequest request, HttpServletResponse response, User currentUser) throws IOException, ServletException {
        String idParam = request.getParameter("postId");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        // Basic validation
        if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Title and content cannot be empty.");
            // Need to repopulate form
            Post post = new Post();
            if (idParam != null && !idParam.isEmpty()) post.setPostId(Integer.parseInt(idParam));
            post.setTitle(title);
            post.setContent(content);
            request.setAttribute("post", post);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/post_form.jsp");
            dispatcher.forward(request, response);
            return;
        }

        boolean success;
        if (idParam == null || idParam.isEmpty()) {
            // Create new post
            Post newPost = new Post();
            newPost.setTitle(title);
            newPost.setContent(content);
            newPost.setUserId(currentUser.getUserId()); // Set the author
            success = postDAO.addPost(newPost);
        } else {
            // Update existing post
            Post existingPost = new Post();
            existingPost.setPostId(Integer.parseInt(idParam));
            existingPost.setTitle(title);
            existingPost.setContent(content);
            // userId is not updated
            success = postDAO.updatePost(existingPost);
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/cr/dashboard?success=postSaved");
        } else {
            request.setAttribute("errorMessage", "Failed to save post.");
             // Repopulate form on error
            Post post = new Post();
            if (idParam != null && !idParam.isEmpty()) post.setPostId(Integer.parseInt(idParam));
            post.setTitle(title);
            post.setContent(content);
            request.setAttribute("post", post);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/post_form.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void deletePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("postId"));
        boolean success = postDAO.deletePost(id);
        if (success) {
             response.sendRedirect(request.getContextPath() + "/cr/dashboard?success=postDeleted");
        } else {
             response.sendRedirect(request.getContextPath() + "/cr/dashboard?error=deleteFailed");
        }
    }
} 