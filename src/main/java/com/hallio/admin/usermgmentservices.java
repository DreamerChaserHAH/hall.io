package com.hallio.admin;

import com.hallio.dms.DatabaseManager;
import com.hallio.dms.FileManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class usermgmentservices {

    public static void createUser(String username, String password, String role, String firstName, String lastName, String phone, String email) {
        String currentDate = java.time.LocalDate.now().toString();
        int newId = getNextId();
        User user = new User(newId, username, password, role, firstName, lastName, phone, email, currentDate, currentDate);
        FileManager.appendFile("users.txt", user.getAttributesWithIdAsString());
    }

    public static void createSuperUser() {
        createUser("admin", "admin", "superuser", "N/A", "N/A", "N/A", "N/A");
    }

    private static int getNextId() {
        List<User> users = getAllUsers();
        int maxId = 0;
        for (User user : users) {
            if (DatabaseManager.getNext("users") > maxId) {
                maxId = DatabaseManager.getNext("users");
            }
        }
        return maxId + 1;
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        List<String> lines = FileManager.readFile("users.txt");
        for (String line : lines) {
            User user = new User();
            user.loadFromString(Arrays.asList(line.split(",")));
            users.add(user);
        }
        return users;
    }

    public static void deleteUser(String username) {
        List<User> users = getAllUsers();
        Iterator<User> iterator = users.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUsername().equals(username)) {
                iterator.remove();
                found = true;
                break;
            }
        }
        if (found) {
            List<String> lines = new ArrayList<>();
            for (User user : users) {
                lines.add(user.getAttributesWithIdAsString());
            }
            FileManager.writeFile("users.txt", String.join(",", lines));
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    public static void editUser(String username, User updatedUser) {
        List<User> users = getAllUsers();
        boolean found = false;
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUsername().equals(username)) {
                updatedUser.setId(user.getId());
                updatedUser.setRegDate(user.getRegDate());
                updatedUser.setLastLogin(user.getLastLogin());
                users.set(i, updatedUser);
                found = true;
                break;
            }
        }
        if (found) {
            List<String> lines = new ArrayList<>();
            for (User user : users) {
                lines.add(user.getAttributesWithIdAsString());
            }
            FileManager.writeFile("users.txt", String.join(",", lines));
            System.out.println("User updated successfully.");
        } else {
            System.out.println("User not found.");
        }
    }
}