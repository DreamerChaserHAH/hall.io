package com.hallio.hms;

import com.hallio.dms.DatabaseManager;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HallManager {
    private static List<Hall> hallsStored = new LinkedList<>();
    private static final String databaseName = "detailHalls";

    public static void createDatabase(){
        DatabaseManager.createDatabase(databaseName);
    }

    public static void addHall(Hall hall) {
        hall.id = DatabaseManager.getNext(databaseName);
        DatabaseManager.createRecord(databaseName, hall);
    }

    public static void updateHall(Hall hall) {
        try {
            DatabaseManager.updateRecord(databaseName, hall.getId(), hall);
        } catch (Exception e) {
            System.out.println("Record does not exist");
        }
    }

    public static void deleteHall(int hallId) {
        try {
            DatabaseManager.deleteRecord(databaseName, hallId);
        }catch(Exception e){
            System.out.println("Record does not exist");
        }
    }

    public static List<Hall> loadHalls() {
        Hall[] halls = new Hall[DatabaseManager.getAmountOfRecords(databaseName)];
        for (int i = 0; i < halls.length; i++) {
            halls[i] = new Hall();
        }
        DatabaseManager.readAllRecords(databaseName, halls);
        hallsStored = List.of(halls);
        return hallsStored;
    }

    public static boolean hallExists(int hallId) {
        Hall[] halls = new Hall[DatabaseManager.getAmountOfRecords(databaseName)];
        for (int i = 0; i < halls.length; i++) {
            halls[i] = new Hall();
        }
        DatabaseManager.readAllRecords(databaseName, halls);
        for (Hall hall : halls) {
            if (hall.getId() == hallId) {
                return true;
            }
        }
        return false;
    }

    public static Hall getHallById(int hallId) {
        return hallsStored.stream().filter(hall -> hall.getId() == hallId).findFirst().orElse(null);
    }
}