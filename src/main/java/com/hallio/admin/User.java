package com.hallio.admin;
import java.util.Date;

public class User {
    private String username;
    private String password;
    private String role;
    private String email;
    private boolean isActive;
    private Date lastLogin;
    private Date registeredDate;

    public User(String username, String password, String role, String email, boolean isActive, Date lastLogin, Date registeredDate) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.isActive = isActive;
        this.lastLogin = lastLogin;
        this.registeredDate = registeredDate;
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

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }
}