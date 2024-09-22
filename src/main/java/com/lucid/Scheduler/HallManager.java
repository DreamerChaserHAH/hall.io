package com.lucid.scheduler;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//import javax.swing.JButton;
//import javax.swing.JScrollPane;

// Ensure the Hall class is defined in the same package or imported correctly
// If Hall is in a different package, import it like this:
// import path.to.Hall;

public class HallManager {
    private List<Hall> halls; // List to store all hall objects

    // Constructor to initialize the list of halls
    public HallManager() {
        this.halls = new ArrayList<>(); // Initialize the list
        loadHalls(); // Load halls from the database on initialization
    }

    // Load all halls from the database
    private void loadHalls() {
        // TODO: Implement database loading logic here
        halls = new ArrayList<>(); // Initialize with an empty list
    }

    // Add a new hall to the list
    public void addHall(Hall hall) {
        if (hall != null) { // Check for null to avoid NullPointerException
            halls.add(hall); // Add hall to the list
            // TODO: Implement database insertion logic here
        }
    }

    // View and filter halls
    public void viewHalls() {
        for (Hall hall : halls) {
            System.out.println(hall); // Print hall information
        }
    }

    // Find a hall by its ID
    public Hall findHallByID(int hallID) {
        for (Hall hall : halls) {
            if (hall.getId() == hallID) {
                return hall; // Return the hall if found
            }
        }
        return null; // Return null if not found
    }

    // Edit hall information
    public void editHall(int hallID, String newName, double newRate, int newSeats) {
        Hall hall = findHallByID(hallID);
        if (hall != null) {
            hall.setHallType(newName); // Update hall type
            hall.setHourlyRate(newRate); // Update hourly rate
            hall.setTotalSeats(newSeats); // Update total seats
            updateHall(hall); // Update hall in the database
        }
    }

    // Delete a hall from the list
    public void deleteHall(int hallID) {
        Hall hall = findHallByID(hallID);
        if (hall != null) {
            halls.remove(hall); // Remove hall from the list
            // TODO: Implement database deletion logic here
        }
    }

    // Set availability for a hall
    public void setAvailability(int hallID, LocalDateTime start, LocalDateTime end, String remarks) {
        Hall hall = findHallByID(hallID);
        if (hall != null) {
            hall.addAvailabilityPeriod(start, end, remarks); // Add availability period
            updateHall(hall); // Update hall in the database
        }
    }

    // Set maintenance for a hall
    public void setMaintenance(int hallID, LocalDateTime start, LocalDateTime end, String remarks) {
        Hall hall = findHallByID(hallID);
        if (hall != null) {
            hall.addMaintenancePeriod(start, end, remarks); // Add maintenance period
            updateHall(hall); // Update hall in the database
        }
    }

    // Update hall information in the database
    private void updateHall(Hall hall) {
        // TODO: Implement database update logic here
        int index = halls.indexOf(hall);
        if (index != -1) {
            halls.set(index, hall); // Update hall in the list
        }
    }

    // Get a hall by its ID
    public Hall getHallById(int id) {
        return findHallByID(id); // Return hall found by ID
    }

    // Get a list of all halls
    public List<Hall> getHalls() {
        return new ArrayList<>(halls); // Return a copy of the list of halls
    }
}