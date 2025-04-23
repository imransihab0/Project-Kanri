package com.kopinfotech.blog.model;

import java.sql.Timestamp;

/**
 * Represents a Blog Post.
 */
public class Post {

    private int postId;
    private int userId; // Foreign key referencing User
    private String title;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Optional: Include user details if needed frequently
    // private String authorName; // Example: Could be populated by a JOIN query

    // Constructors
    public Post() {
    }

    public Post(int postId, int userId, String title, String content, Timestamp createdAt, Timestamp updatedAt) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // toString (optional, for debugging)
    @Override
    public String toString() {
        return "Post{" +
               "postId=" + postId +
               ", userId=" + userId +
               ", title='" + title + '\'' +
               ", content='" + content + '\'' +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
               '}';
    }
} 