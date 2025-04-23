package com.kopinfotech.blog.dao;

import com.kopinfotech.blog.model.User;
import com.kopinfotech.blog.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    /**
     * Adds a new user to the database.
     * Handles password hashing (placeholder for now).
     */
    public boolean addUser(User user) {
        // TODO: Implement password hashing before storing
        String hashedPassword = hashPassword(user.getPassword()); // Placeholder

        String sql = "INSERT INTO users (name, username, email, password, student_id, role) VALUES (?, ?, ?, ?, ?, ?)";
        boolean rowInserted = false;

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, hashedPassword); // Store hashed password
            pstmt.setString(5, user.getStudentId());
            pstmt.setString(6, user.getRole());

            rowInserted = pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("SQL Error adding user: " + e.getMessage());
            // Consider more robust error handling/logging
        } finally {
            closeQuietly(pstmt);
            DBUtil.closeConnection(conn);
        }
        return rowInserted;
    }

    /**
     * Finds a user by their username.
     * Used for login validation.
     */
    public User findUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error finding user by username: " + e.getMessage());
        } finally {
            closeQuietly(rs);
            closeQuietly(pstmt);
            DBUtil.closeConnection(conn);
        }
        return user;
    }

     /**
     * Retrieves all users from the database.
     * Used for the name card display.
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, name, username, email, student_id, role, created_at FROM users ORDER BY name ASC"; // Exclude password
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                // Password is intentionally not fetched
                user.setStudentId(rs.getString("student_id"));
                user.setRole(rs.getString("role"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error getting all users: " + e.getMessage());
        } finally {
            closeQuietly(rs);
            closeQuietly(stmt);
            DBUtil.closeConnection(conn);
        }
        return users;
    }

    /**
     * Placeholder for password hashing. Replace with a secure implementation.
     */
    private String hashPassword(String plainPassword) {
        // NEVER store plain text passwords!
        // TODO: Use a strong hashing library like BCrypt or Argon2
        System.out.println("Warning: Password hashing not implemented! Storing plain text (unsafe).");
        return plainPassword;
    }

    /**
     * Placeholder for password verification.
     */
    public boolean checkPassword(String plainPassword, String hashedPassword) {
        // TODO: Implement verification matching the hashPassword method
        System.out.println("Warning: Password verification not implemented!");
        return plainPassword.equals(hashedPassword);
    }

    // Helper method to map ResultSet to User object
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setName(rs.getString("name"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password")); // Retrieve hashed password
        user.setStudentId(rs.getString("student_id"));
        user.setRole(rs.getString("role"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }

    // Helper methods to close resources quietly
    private void closeQuietly(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // Log or ignore
            }
        }
    }

    private void closeQuietly(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // Log or ignore
            }
        }
    }
} 