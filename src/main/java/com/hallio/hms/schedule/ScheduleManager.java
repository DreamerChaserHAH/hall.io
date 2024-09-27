package com.hallio.hms.schedule;

import com.hallio.dms.DatabaseManager;
import com.hallio.hms.HallManager;

public class ScheduleManager {
    private static final String databaseName = "schedule";

    public static void createDatabase(){
        // Create the database
        DatabaseManager.createDatabase(databaseName);
    }

    public static void addSchedule(Schedule schedule) {
        // Add the schedule to the database
        int hallId = schedule.getHallId();
        if(HallManager.hallExists(hallId)) {
            DatabaseManager.createRecord(databaseName, schedule);
        }
    }

    public static Schedule[] loadSchedules() {
        // Load all schedules from the database
        Schedule[] schedules = new Schedule[DatabaseManager.getAmountOfRecords(databaseName)];
        for (int i = 0; i < schedules.length; i++) {
            schedules[i] = new Schedule();
        }
        DatabaseManager.readAllRecords(databaseName, schedules);
        return schedules;
    }

    public static void removeSchedule(Schedule schedule) {
        // Remove the schedule from the database
        try{
            DatabaseManager.deleteRecord(databaseName, schedule.id);
        } catch (Exception e) {
            System.out.println("Record does not exist");
        }
    }

    public static void updateSchedule(Schedule schedule) {
        // Update the schedule in the database
        try{
            DatabaseManager.updateRecord(databaseName, schedule.id, schedule);
        } catch (Exception e) {
            System.out.println("Record does not exist");
        }
    }

    public static Schedule getSchedule(int id) {
        // Retrieve the schedule from the database
        Schedule schedule = new Schedule();
        try{
            DatabaseManager.readRecord(databaseName, id, schedule);
            return schedule;
        } catch (Exception e) {
            System.out.println("Record does not exist");
        }
        return null;
    }
}
