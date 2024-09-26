package com.hallio.admin;

import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String regDate;
    private String lastLogin;

    // Constructor
    public User(int id, String username, String password, String role, String firstName, String lastName, String phone, String email, String regDate, String lastLogin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.regDate = regDate;
        this.lastLogin = lastLogin;
    }

    // Default constructor
    public User() {}

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    // Method to load user data from a list of strings
    public void loadFromString(List<String> attributes) {
        // Implementation to load user data from attributes
    }

    // Method to get user attributes as a string
    public String getAttributesWithIdAsString() {
        // Implementation to return user attributes as a string
        return id + "," + username + "," + password + "," + role + "," + firstName + "," + lastName + "," + phone + "," + email + "," + regDate + "," + lastLogin;
    }
}