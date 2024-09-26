package com.hallio.admin;

import com.hallio.dms.IObject;
import java.util.LinkedList;
import java.util.List;

public class User extends IObject {
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

    public int getId() {
        return id;
    }

    @Override
    public LinkedList<String> getAttributes() {
        LinkedList<String> attributes = new LinkedList<>();
        attributes.add(username);
        attributes.add(password);
        attributes.add(role);
        attributes.add(firstName);
        attributes.add(lastName);
        attributes.add(phone);
        attributes.add(email);
        attributes.add(regDate);
        attributes.add(lastLogin);
        return attributes;
    }

    @Override
    public void LoadFromString(String data) {
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
        } else {
            // Handle error or log warning
        }
    }

    @Override
    public String getFilePath() {
        return "users.txt"; // Adjust as necessary
    }

    @Override
    protected void loadFromString(List<String> list) {

    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    // Add other getters as needed
}
