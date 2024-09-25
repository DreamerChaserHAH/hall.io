package com.hallio.admin;

import com.hallio.dms.DatabaseManager;
import java.util.List;
import java.util.ArrayList;

public class authenticationservices implements IObject {
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
            if (parts.length >= 3) {
                users.add(new User(parts[0], parts[1], parts[2]));
            }
        }
        return users;
    }

    @Override
    public void save() {
        // Implement save logic
    }

    @Override
    public void delete() {
        // Implement delete logic
    }
}