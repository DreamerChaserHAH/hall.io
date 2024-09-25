package com.hallio.admin;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.hallio.dms.DatabaseManager;
import com.hallio.dms.IObject;

public class authenticationservices {
    public static boolean authenticate(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("databases/users.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    reader.close();
                    return true;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getRole(String username) {
        String fileName = "databases/users.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(username)) {
                    return parts[2];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Role not found";
    }

}
