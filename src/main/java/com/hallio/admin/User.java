package com.hallio.admin;
import com.hallio.dms.IObject;
import java.util.LinkedList;
import java.util.List;

public class User extends IObject {
    private String username;
    private String password; // Store plain text password
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
        this.id = id; // `id` inherited from IObject
        this.username = username;
        this.password = password; // Store password directly
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.regDate = regDate;
        this.lastLogin = lastLogin;
    }


    @Override
    public LinkedList<String> getAttributes() {
        LinkedList<String> attributes = new LinkedList<>();
        attributes.add(username);
        attributes.add(password); // Use plain text password
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
    protected void loadFromString(List<String> attributes) {
        if (attributes.size() >= 10) {
            try {
                this.id = Integer.parseInt(attributes.get(0));
            } catch (NumberFormatException e) {
                System.err.println("Invalid ID format: " + attributes.get(0));
            }
            this.username = attributes.get(1);
            this.password = attributes.get(2); // Read plain text password
            this.role = attributes.get(3);
            this.firstName = attributes.get(4);
            this.lastName = attributes.get(5);
            this.phone = attributes.get(6);
            this.email = attributes.get(7);
            this.regDate = attributes.get(8);
            this.lastLogin = attributes.get(9);
        } else {
            System.err.println("Insufficient attributes to load User object.");
        }
    }

    @Override
    public String getFilePath() {
        return "databases/users.txt"; // Adjust the path as necessary
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password; // Provide getter for password
    }

    public String getRole() {
        return role;
    }

    // Add other getters as needed

    // You may add setter methods if necessary
}
