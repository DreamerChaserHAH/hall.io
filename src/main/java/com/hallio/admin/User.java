package com.hallio.admin;

public class User {
    private String username;
    private String password;
    private String role;
    private String email; // New attribute
    private boolean isActive; // New attribute

    public User(String username, String password, String role, String email, boolean isActive) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.isActive = isActive;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}