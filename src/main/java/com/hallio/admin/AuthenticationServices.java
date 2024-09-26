package com.hallio.admin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AuthenticationServices implements Authentication {

    @Override
    public boolean authenticate(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("databases\\users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(username) && parts[2].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getRole(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("databases\\users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[1].equals(username)) {
                    return parts[3]; // Assuming the role is the third element
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Role not found";
    }
}