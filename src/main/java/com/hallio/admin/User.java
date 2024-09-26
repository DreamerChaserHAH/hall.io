package com.hallio.admin;

import com.hallio.dms.IObject;

public class User implements IObject {
    private int id; // Assuming there's an ID field
    private String username;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String regDate;
    private String lastLogin;

    public User() {}

    public User(int id, String username, String password, String role, String firstName,
                String lastName, String phone, String email, String regDate, String lastLogin) {
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

    // Implementing methods from IObject
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getAttributesWithIdAsString() {
        // Return attributes as a CSV string including the ID
        return id + "," + username + "," + password + "," + role + "," +
                firstName + "," + lastName + "," + phone + "," + email + "," +
                regDate + "," + lastLogin;
    }

    @Override
    public void LoadFromString(String data) {
        // Parse CSV string to populate fields
        String[] attributes = data.split(",");
        if (attributes.length >= 10) {
            this.id = Integer.parseInt(attributes[0]);
            this.username = attributes[1];
            this.password = attributes[2];
            this.role = attributes[3];
            this.firstName = attributes[4];
            this.lastName = attributes[5];
            this.phone = attributes[6];
            this.email = attributes[7];
            this.regDate = attributes[8];
            this.lastLogin = attributes[9];
        }
    }

    // Getter methods for all attributes
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    // (Add getters for other fields as needed)
}
