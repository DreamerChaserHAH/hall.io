package com.hallio.admin;

import com.hallio.dms.DatabaseManager;
import java.util.List;
import java.util.ArrayList;

public class authenticationservices {
    private DatabaseManager dbManager;
    private static final String DATABASE_NAME = "users";

    public authenticationservices(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        List<String> records = dbManager.readFile(DATABASE_NAME);
        for (String record : records) {
            String[] parts = record.split(",");
            if (parts.length >= 4) {
                User user = new User(parts[1], parts[2], parts[3]);
                user.id = Integer.parseInt(parts[0]);
                users.add(user);
            }
        }
        return users;
    }
}