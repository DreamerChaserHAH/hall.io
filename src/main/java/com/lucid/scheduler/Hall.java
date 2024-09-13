package com.lucid.scheduler;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Hall {
    private int id; // Unique identifier for the hall
    private String hallType; // Type of the hall (e.g., Auditorium, Banquet)
    private String hallLocation; // Location of the hall
    private Date maintenanceStartTime; // Start time of maintenance
    private Date maintenanceEndTime; // End time of maintenance

    // Constructor to create a new hall
    public Hall(int id, String hallType, String hallLocation, Date maintenanceStartTime, Date maintenanceEndTime) {
        this.id = id;
        this.hallType = hallType;
        this.hallLocation = hallLocation;
        this.maintenanceStartTime = maintenanceStartTime;
        this.maintenanceEndTime = maintenanceEndTime;
    }

    // Default constructor
    public Hall() {
        this.id = -1; // Default ID
        this.hallType = "";
        this.hallLocation = "";
        this.maintenanceStartTime = new Date();
        this.maintenanceEndTime = new Date();
    }

    public LinkedList<String> getAttributes() {
        return new LinkedList<>(List.of(
            String.valueOf(this.id),
            this.hallType,
            this.hallLocation,
            String.valueOf(this.maintenanceStartTime.getTime()),
            String.valueOf(this.maintenanceEndTime.getTime())
        ));
    }

    public String getFilePath() {
        return "halls.txt"; // File path for hall records
    }

    public void loadFromString(List<String> attributes) {
        this.id = Integer.parseInt(attributes.get(0));
        this.hallType = attributes.get(1);
        this.hallLocation = attributes.get(2);
        this.maintenanceStartTime = new Date(Long.parseLong(attributes.get(3)));
        this.maintenanceEndTime = new Date(Long.parseLong(attributes.get(4)));
    }

    // Method to create a hall
    public static Hall createHall(int id, String hallType, String hallLocation, Date maintenanceStartTime, Date maintenanceEndTime) {
        return new Hall(id, hallType, hallLocation, maintenanceStartTime, maintenanceEndTime);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getHallType() {
        return hallType;
    }

    public void setHallType(String newHallType) {
        this.hallType = newHallType;
    }

    public String getHallLocation() {
        return hallLocation;
    }

    public void setHallLocation(String newLocation) {
        this.hallLocation = newLocation;
    }

    public Date getMaintenanceStartTime() {
        return maintenanceStartTime;
    }

    public Date getMaintenanceEndTime() {
        return maintenanceEndTime;
    }

    public void setMaintenanceSchedule(Date startTime, Date endTime) {
        this.maintenanceStartTime = startTime;
        this.maintenanceEndTime = endTime;
    }

    // Method to display hall information
    public String displayInfo() {
        return "Hall ID: " + id + ", Type: " + hallType + ", Location: " + hallLocation +
               ", Maintenance Start: " + maintenanceStartTime + ", Maintenance End: " + maintenanceEndTime;
    }
}