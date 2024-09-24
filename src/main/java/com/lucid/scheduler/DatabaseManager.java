package com.lucid.Scheduler;

import Scheduler.Hall;

import java.io.File;
//import java.util.List;

public class DatabaseManager {
    public static void createRecord(String databaseName, Hall hall) {
        // Implement logic to create a record in the database or file
    }

    public static boolean isDatabaseFileExist(String databaseName) {
        File file = new File(databaseName + ".txt");
        return file.exists();
    }

    public static void readRecord(String databaseName, int id, Hall hall) {
        // Implement logic to read a record from the database or file
    }

    public static void updateRecord(String databaseName, int id, Hall hall) {
        // Implement logic to update a record in the database or file
    }

    public static void deleteRecord(String databaseName, String hallID) {
        // Implement logic to delete a record from the database or file
    }
}

