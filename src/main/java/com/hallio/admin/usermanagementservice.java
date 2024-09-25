package com.hallio.admin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserManagementService {
    private static final String USER_FILE = "databases/users.txt";

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    users.add(new User(parts[0], parts[1], parts[2], parts[3], Boolean.parseBoolean(parts[4])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    // New method to deactivate a user
    public boolean deactivateUser(String username) {
        // Implementation to deactivate user
        return true;
    }
}