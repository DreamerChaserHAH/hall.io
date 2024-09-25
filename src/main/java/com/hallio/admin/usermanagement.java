package com.hallio.admin;

import com.hallio.dms.DatabaseManager;
import com.hallio.dms.IObject;
import java.util.LinkedList;
import java.util.List;

public class usermanagement extends IObject {
    private DatabaseManager dbManager;
    private static final String DATABASE_NAME = "users";

    public usermanagement(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void save() {
        // Implement save logic
    }

    @Override
    public void delete() {
        // Implement delete logic
    }

    public List<usermanagementservices.staff> getUsers() {
        List<usermanagementservices.staff> users = new ArrayList<>();
        List<String> records = dbManager.readFile(DATABASE_NAME);
        for (String record : records) {
            String[] parts = record.split(",");
            if (parts.length >= 9) {
                usermanagementservices.staff user = new usermanagementservices.staff();
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

    public boolean addUser(usermanagementservices.staff user) {
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