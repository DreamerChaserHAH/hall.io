package com.hallio.admin;

import com.hallio.dms.IObject;
import java.util.LinkedList;
import java.util.List;

public class User extends IObject {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
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

    @Override
    protected LinkedList<String> getAttributes() {
        LinkedList<String> attributes = new LinkedList<>();
        attributes.add(username);
        attributes.add(password);
        attributes.add(role);
        return attributes;
    }

    @Override
    protected String getFilePath() {
        return "databases/users.txt";
    }

    @Override
    protected void loadFromString(List<String> attributes) {
        this.id = Integer.parseInt(attributes.get(0));
        this.username = attributes.get(1);
        this.password = attributes.get(2);
        this.role = attributes.get(3);
    }
}