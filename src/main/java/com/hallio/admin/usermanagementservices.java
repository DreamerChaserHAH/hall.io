package com.hallio.admin;

import com.hallio.dms.DatabaseManager;
import com.hallio.dms.IObject;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class usermanagementservices extends IObject {
    private DatabaseManager dbManager;
    private static final String DATABASE_NAME = "users";

    public usermanagementservices(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public class staff {
        public String userLogiName;
        public String userPassword;
        public String userRole;
        public String userFirstName;
        public String userLastName;
        public String userPhone;
        public String userEmail;
        public String userRegDate;
        public String userLastLogin;
    }

    public static void superuserCreate() {
        String currentDate = java.time.LocalDate.now().toString();
        String details = "admin,admin,superuser,na,na," + currentDate + "," + currentDate;
        FileManager.appendFile("users.txt", details);
    }

    public static void userCreate(String userLogiName, String userPassword, String userRole, String userFirstName, String userLastName, String userPhone, String userEmail) {
        String currentDate = java.time.LocalDate.now().toString();
        String details = userLogiName + "," + userPassword + "," + userRole + "," + userFirstName + "," + userLastName + "," + userPhone + "," + userEmail + "," + currentDate + "," + currentDate;
        FileManager.appendFile("users.txt", details);
    }

    @Override
    protected LinkedList<String> getAttributes() {
        LinkedList<String> attributes = new LinkedList<>();
        // Add attributes as needed
        return attributes;
    }

    @Override
    protected String getFilePath() {
        return "databases/users.txt";
    }

    @Override
    protected void loadFromString(List<String> attributes) {
        // Implement loading logic
    }

    public List<staff> getUsers() {
        List<staff> users = new ArrayList<>();
        List<String> records = dbManager.readFile(DATABASE_NAME);
        for (String record : records) {
            String[] parts = record.split(",");
            if (parts.length >= 9) {
                staff user = new staff();
                user.userLogiName = parts[0];
                user.userPassword = parts[1];
                user.userRole = parts[2];
                user.userFirstName = parts[3];
                user.userLastName = parts[4];
                user.userPhone = parts[5];
                user.userEmail = parts[6];
                user.userRegDate = parts[7];
                user.userLastLogin = parts[8];
                users.add(user);
            }
        }
        return users;
    }

    public boolean addUser(staff user) {
        String record = user.userLogiName + "," + user.userPassword + "," + user.userRole + "," + user.userFirstName + "," + user.userLastName + "," + user.userPhone + "," + user.userEmail + "," + user.userRegDate + "," + user.userLastLogin;
        return dbManager.writeFile(DATABASE_NAME, record);
    }

    public boolean removeUser(String userLogiName) {
        List<String> records = dbManager.readFile(DATABASE_NAME);
        List<String> updatedRecords = new ArrayList<>();
        boolean userRemoved = false;
        for (String record : records) {
            String[] parts = record.split(",");
            if (!parts[0].equals(userLogiName)) {
                updatedRecords.add(record);
            } else {
                userRemoved = true;
            }
        }
        if (userRemoved) {
            dbManager.writeFile(DATABASE_NAME, updatedRecords);
        }
        return userRemoved;
    }
}