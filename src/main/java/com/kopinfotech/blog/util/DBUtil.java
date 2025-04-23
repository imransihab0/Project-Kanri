package com.kopinfotech.blog.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // --- Database Configuration ---
    // Ensure these match your MySQL setup
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kanridb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Your MySQL root password (leave empty if none)
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            // Load the MySQL JDBC driver
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading MySQL JDBC Driver: " + e.getMessage());
            // Consider throwing a RuntimeException or logging more formally
            // For a web app, proper logging is crucial
            throw new RuntimeException("Failed to load database driver", e);
        }
    }

    /**
     * Gets a connection to the database.
     *
     * @return A Connection object.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * Closes a database connection quietly (without throwing exceptions).
     *
     * @param connection The connection to close.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Log this error appropriately in a real application
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    // Optional: Add methods to close Statement and ResultSet quietly as well
    // public static void closeStatement(java.sql.Statement statement) { ... }
    // public static void closeResultSet(java.sql.ResultSet resultSet) { ... }
} 