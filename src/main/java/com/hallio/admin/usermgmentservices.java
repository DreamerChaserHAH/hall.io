package com.hallio.admin;
import com.hallio.dms.DatabaseManager;
import com.hallio.dms.FileManager;
import com.hallio.dms.IObject;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class usermgmentservices {

    public static class User extends IObject {
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
            this.id = id; // `id` inherited from IObject
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

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public String getRegDate() {
            return regDate;
        }

        public String getLastLogin() {
            return lastLogin;
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
        public String getAttributesWithIdAsString() {
            LinkedList<String> attributes = getAttributes();
            attributes.addFirst(String.valueOf(id));
            return String.join(",", attributes);
        }

        @Override
        protected String getFilePath() {
            return "databases\\users.txt";
        }

        @Override
        protected void loadFromString(List<String> attributes) {
            if (attributes.size() >= 10) {
                this.id = Integer.parseInt(attributes.get(0));
                this.username = attributes.get(1);
                this.password = attributes.get(2);
                this.role = attributes.get(3);
                this.firstName = attributes.get(4);
                this.lastName = attributes.get(5);
                this.phone = attributes.get(6);
                this.email = attributes.get(7);
                this.regDate = attributes.get(8);
                this.lastLogin = attributes.get(9);
            } else {
                // Handle error or log warning
                System.err.println("Insufficient data to load User: " + attributes);
            }
        }
    }

    public static void createUser(String username, String password, String role, String firstName, String lastName, String phone, String email) {
        String currentDate = java.time.LocalDate.now().toString();
        int newId = getNextId();
        User user = new User(newId, username, password, role, firstName, lastName, phone, email, currentDate, currentDate);
        FileManager.appendFile("users.txt", user.getAttributesWithIdAsString());
    }

    public static void createSuperUser() {
        createUser("admin", "admin", "superuser", "N/A", "N/A", "N/A", "N/A");
    }

    // New method to get the next available ID
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

    // Method to retrieve all users from the file
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

    // Method to delete a user by username
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
            // Write the updated users list back to the file
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

    // Method to edit a user's details
    public static void editUser(String username, User updatedUser) {
        List<User> users = getAllUsers();
        boolean found = false;
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUsername().equals(username)) {
                // Keep the same ID and registration date
                updatedUser.id = user.id;
                updatedUser.regDate = user.regDate;
                updatedUser.lastLogin = user.lastLogin;
                users.set(i, updatedUser);
                found = true;
                break;
            }
        }
        if (found) {
            // Write the updated users list back to the file
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
