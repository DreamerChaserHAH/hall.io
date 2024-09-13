package com.lucid.scheduler;
import com.lucid.fileio.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.time.LocalDateTime;

//list to store all halls 
public class HallManager {
    private List<Hall> halls; // List to store all halls
    private final String databaseName = "halls"; // Database name for hall records

//constructor to initialize the list of halls
    public HallManager() {
        this.halls = new ArrayList<>();
        loadHalls(); // Load halls from the database on initialization
    }

    // Add new hall
    public void addHall(Hall hall) {
        DatabaseManager.createRecord(databaseName, hall); // Use DatabaseManager to create a record
        halls.add(hall); // Add to the local list
    }

    // View and filter halls
    public void viewHalls() {
        for (Hall hall : halls) {
            hall.displayInfo();
        }
    }

    // Find hall by ID
    public Hall findHallByID(String hallID) {
        for (Hall hall : halls) {
            if (hall.hallID.equals(hallID)) {
                return hall;
            }
        }
        return null;
    }

    // Edit hall information
    public void editHall(String hallID, String newName, double newRate) {
        Hall hall = findHallByID(hallID);
        if (hall != null) {
            hall.editInfo(newName, newRate);
        }
    }

    // Delete hall
    public void deleteHall(String hallID) {
        Hall hall = findHallByID(hallID);
        if (hall != null) {
            halls.remove(hall);
        }
    }

    // Set availability
    public void setAvailability(String hallID, LocalDateTime start, LocalDateTime end, String remarks) {
        Hall hall = findHallByID(hallID);
        if (hall != null) {
            Hall.addAvailabilityPeriod(start, end, remarks); //it will add the availability period to the hall
        }
    }

    // Set maintenance
    public void setMaintenance(String hallID, LocalDateTime start, LocalDateTime end, String remarks) {
        Hall hall = findHallByID(hallID);
        if (hall != null) {
            Hall.addMaintenancePeriod(start, end, remarks); //add the maintenance period to the hall
        }
    }

    // Load all halls from the database
    public void loadHalls() {
        if (DatabaseManager.isDatabaseFileExist(databaseName)) {
            // Load each hall record from the database
            for (int id = 1; id <= getMaxId(); id++) { // Assuming IDs are sequential
                Hall hall = new Hall(); // Create a new Hall object
                try {
                    DatabaseManager.readRecord(databaseName, id, hall); // Read hall data
                    halls.add(hall); // Add to the local list
                } catch (Exception e) {
                    // Handle exception (e.g., log it)
                }
            }
        }
    }

    // Update hall information
    public void updateHall(int id, String newHallType, String newLocation, Date startTime, Date endTime) {
        Hall hall = getHallById(id);
        if (hall != null) {
            hall.setHallType(newHallType);
            hall.setHallLocation(newLocation);
            hall.setMaintenanceSchedule(startTime, endTime);
            DatabaseManager.updateRecord(databaseName, id, hall); // Update the record in the database
        }
    }

    // Delete a hall by ID
    public void deleteHall(int id) {
        halls.removeIf(hall -> hall.getId() == id); // Remove from local list
        DatabaseManager.deleteRecord(databaseName, id); // Delete the record from the database
    }

    // Get a hall by ID
    public Hall getHallById(int id) {
        for (Hall hall : halls) {
            if (hall.getId() == id) {
                return hall; // Return the hall if found
            }
        }
        return null; // Return null if not found
    }

    // Display all halls
    public void displayHalls() {
        for (Hall hall : halls) {
            System.out.println(hall.displayInfo()); // Display information for each hall
        }
    }

    // Helper method to get the maximum ID (for loading halls)
    private int getMaxId() {
        // Logic to determine the maximum ID from the database
        // This can be implemented based on your specific requirements
        return 100; // Placeholder value
    }
}