package com.hallio.admin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AuthenticationService {
    private static final String USER_FILE = "databases/users.txt";

    public boolean authenticate(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getRole(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(username)) {
                    return parts[2];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Role not found";
    }

    // New method to reset password
    public boolean resetPassword(String username, String newPassword) {
        // Implementation to reset password
        return true;
    }
}