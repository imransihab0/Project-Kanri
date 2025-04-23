package com.kopinfotech.blog.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the current session, but don't create one if it doesn't exist
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Invalidate the session (remove all attributes and effectively log the user out)
            session.invalidate();
        }

        // Redirect the user to the login page
        // Optionally add a message like ?logout=success
        response.sendRedirect(request.getContextPath() + "/login");
    }

    // Typically, logout is done via GET, but you could implement POST if needed
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
} 