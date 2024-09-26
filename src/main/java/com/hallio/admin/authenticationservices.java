package com.hallio.admin;

import com.hallio.dms.FileManager;

import java.util.ArrayList;
import java.util.List;

public class authenticationservices {

    public static boolean authenticate(String username, String password) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static String getRole(String username) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user.getRole();
            }
        }
        return "Role not found";
    }

    private static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        List<String> lines = FileManager.readFile("users.txt");
        for (String line : lines) {
            User user = new User();
            user.LoadFromString(line);
            users.add(user);
        }
        return users;
    }
}
