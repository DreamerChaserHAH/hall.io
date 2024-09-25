package com.hallio.admin;

import com.hallio.dms.FileManager;
import com.hallio.dms.IObject;
import java.util.LinkedList;
import java.util.List;

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

        public User(String username, String password, String role, String firstName, String lastName, String phone, String email, String regDate, String lastLogin) {
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
        protected String getFilePath() {
            return "databases/users.txt";
        }

        @Override
        protected void loadFromString(List<String> attributes) {
            this.username = attributes.get(1);
            this.password = attributes.get(2);
            this.role = attributes.get(3);
            this.firstName = attributes.get(4);
            this.lastName = attributes.get(5);
            this.phone = attributes.get(6);
            this.email = attributes.get(7);
            this.regDate = attributes.get(8);
            this.lastLogin = attributes.get(9);
        }
    }

    public static void createUser(String username, String password, String role, String firstName, String lastName, String phone, String email) {
        String currentDate = java.time.LocalDate.now().toString();
        User user = new User(username, password, role, firstName, lastName, phone, email, currentDate, currentDate);
        FileManager.appendFile("users.txt", user.getAttributesWithIdAsString());
    }

    public static void createSuperUser() {
        createUser("admin", "admin", "superuser", "na", "na", "na", "na");
    }
}