package com.lucid.Scheduler;
public class BanquetHall extends Hall {
    // Constructor to initialize BanquetHall with fixed rate
    public BanquetHall(int id, String hallLocation, Date maintenanceStartTime, Date maintenanceEndTime) {
        super(id, "Banquet Hall", hallLocation, maintenanceStartTime, maintenanceEndTime); // Set hall type to "Banquet Hall"
    }

    // Override displayInfo to show specific details for Banquet Hall
    @Override
    public String displayInfo() {
        return "Banquet Hall: " + getHallLocation() + ", Maintenance Start: " + getMaintenanceStartTime() + ", Maintenance End: " + getMaintenanceEndTime();
    }
}
