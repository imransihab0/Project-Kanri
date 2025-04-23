package com.kopinfotech.blog.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filter to ensure users are authenticated before accessing protected resources.
 */
// Apply this filter to specific URL patterns (e.g., dashboards, post actions)
@WebFilter(urlPatterns = {"/cr/*", "/noncr/*", "/posts/*"}) // Adjust paths as needed
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
        System.out.println("AuthenticationFilter initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false); // Don't create session if it doesn't exist

        boolean isLoggedIn = (session != null && session.getAttribute("loggedInUser") != null);
        String loginURI = httpRequest.getContextPath() + "/login";
        String signupURI = httpRequest.getContextPath() + "/signup";

        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean isSignupRequest = httpRequest.getRequestURI().equals(signupURI);
        // Allow access to static resources like CSS/JS if needed (adjust pattern)
        boolean isStaticResource = httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + "/css/") ||
                                   httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + "/js/");


        // If user is logged in OR the request is for login/signup page OR a static resource, continue
        if (isLoggedIn || isLoginRequest || isSignupRequest || isStaticResource) {
            System.out.println("AuthFilter: Allowing request for " + httpRequest.getRequestURI());
            chain.doFilter(request, response); // User is logged in or accessing public page, continue request
        } else {
            // User is not logged in and trying to access a protected resource
            System.out.println("AuthFilter: Blocking request for " + httpRequest.getRequestURI() + ". Redirecting to login.");
            httpResponse.sendRedirect(loginURI); // Redirect to login page
        }
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
        System.out.println("AuthenticationFilter destroyed.");
    }
} 