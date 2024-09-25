package com.hallio.admin;

import java.util.List;

public class authentication {
    private authenticationservices authServices;

    public authentication(authenticationservices authServices) {
        this.authServices = authServices;
    }

    public boolean authenticate(String username, String password) {
        List<User> users = authServices.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public String getRole(String username) {
        List<User> users = authServices.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user.getRole();
            }
        }
        return "Role not found";
    }

    public boolean addUser(User user) {
        return authServices.addUser(user);
    }

    public boolean removeUser(int id) {
        return authServices.removeUser(id);
    }
}