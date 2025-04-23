package com.kopinfotech.blog.model;

import java.sql.Timestamp;

/**
 * Represents a User in the blog platform.
 */
public class User {

    private int userId;
    private String name;
    private String username;
    private String email;
    private String password; // Should store the hashed password
    private String studentId;
    private String role; // "CR" or "NON-CR"
    private Timestamp createdAt;

    // Constructors
    public User() {
    }

    public User(int userId, String name, String username, String email, String password, String studentId, String role, Timestamp createdAt) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.studentId = studentId;
        this.role = role;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        // Optional: Add validation to ensure role is either "CR" or "NON-CR"
        this.role = role;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // toString (optional, for debugging)
    @Override
    public String toString() {
        return "User{" +
               "userId=" + userId +
               ", name='" + name + '\'' +
               ", username='" + username + '\'' +
               ", email='" + email + '\'' +
               // Avoid printing password
               ", studentId='" + studentId + '\'' +
               ", role='" + role + '\'' +
               ", createdAt=" + createdAt +
               '}';
    }
} 