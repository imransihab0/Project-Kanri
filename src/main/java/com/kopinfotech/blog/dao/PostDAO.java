package com.kopinfotech.blog.dao;

import com.kopinfotech.blog.model.Post;
import com.kopinfotech.blog.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    /**
     * Adds a new post to the database.
     */
    public boolean addPost(Post post) {
        String sql = "INSERT INTO posts (user_id, title, content) VALUES (?, ?, ?)";
        int rowsAffected = 0; // Initialize
        Connection conn = null;
        PreparedStatement pstmt = null;

        System.out.println("Attempting to add post: " + post.getTitle()); // Log entry

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, post.getUserId());
            pstmt.setString(2, post.getTitle());
            pstmt.setString(3, post.getContent());

            rowsAffected = pstmt.executeUpdate();
            System.out.println("Add Post - Rows Affected: " + rowsAffected); // Log result

        } catch (SQLException e) {
            System.err.println("SQL Error adding post: " + e.getMessage());
            e.printStackTrace(); // Print full stack trace for detailed error
        } finally {
            closeQuietly(pstmt);
            DBUtil.closeConnection(conn);
        }
        return rowsAffected > 0; // Return true only if rows were actually inserted
    }

    /**
     * Retrieves a specific post by its ID.
     */
    public Post getPostById(int postId) {
        String sql = "SELECT * FROM posts WHERE post_id = ?";
        Post post = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, postId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                post = mapResultSetToPost(rs);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error getting post by ID: " + e.getMessage());
             e.printStackTrace(); // Add stack trace
        } finally {
            closeQuietly(rs);
            closeQuietly(pstmt);
            DBUtil.closeConnection(conn);
        }
        return post;
    }

    /**
     * Retrieves all posts from the database, ordered by creation date descending.
     */
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        // Optional: JOIN with users table to get author name directly
        // String sql = "SELECT p.*, u.name as authorName FROM posts p JOIN users u ON p.user_id = u.user_id ORDER BY p.created_at DESC";
        String sql = "SELECT * FROM posts ORDER BY created_at DESC";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                posts.add(mapResultSetToPost(rs));
                // If joining: post.setAuthorName(rs.getString("authorName"));
            }
        } catch (SQLException e) {
            System.err.println("SQL Error getting all posts: " + e.getMessage());
             e.printStackTrace(); // Add stack trace
        } finally {
            closeQuietly(rs);
            closeQuietly(stmt);
            DBUtil.closeConnection(conn);
        }
        return posts;
    }

    /**
     * Updates an existing post in the database.
     */
    public boolean updatePost(Post post) {
        String sql = "UPDATE posts SET title = ?, content = ? WHERE post_id = ?";
        int rowsAffected = 0; // Initialize
        Connection conn = null;
        PreparedStatement pstmt = null;

        System.out.println("Attempting to update post ID: " + post.getPostId()); // Log entry

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getContent());
            pstmt.setInt(3, post.getPostId());

            rowsAffected = pstmt.executeUpdate();
             System.out.println("Update Post - Rows Affected: " + rowsAffected); // Log result

        } catch (SQLException e) {
            System.err.println("SQL Error updating post: " + e.getMessage());
            e.printStackTrace(); // Print full stack trace for detailed error
        } finally {
            closeQuietly(pstmt);
            DBUtil.closeConnection(conn);
        }
        return rowsAffected > 0; // Return true only if rows were actually updated
    }

    /**
     * Deletes a post from the database by its ID.
     */
    public boolean deletePost(int postId) {
        String sql = "DELETE FROM posts WHERE post_id = ?";
        int rowsAffected = 0; // Initialize
        Connection conn = null;
        PreparedStatement pstmt = null;

         System.out.println("Attempting to delete post ID: " + postId); // Log entry

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, postId);

            rowsAffected = pstmt.executeUpdate();
            System.out.println("Delete Post - Rows Affected: " + rowsAffected); // Log result

        } catch (SQLException e) {
            System.err.println("SQL Error deleting post: " + e.getMessage());
             e.printStackTrace(); // Add stack trace
        } finally {
            closeQuietly(pstmt);
            DBUtil.closeConnection(conn);
        }
        return rowsAffected > 0;
    }

    // Helper method to map ResultSet to Post object
    private Post mapResultSetToPost(ResultSet rs) throws SQLException {
        Post post = new Post();
        post.setPostId(rs.getInt("post_id"));
        post.setUserId(rs.getInt("user_id"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setCreatedAt(rs.getTimestamp("created_at"));
        post.setUpdatedAt(rs.getTimestamp("updated_at"));
        return post;
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