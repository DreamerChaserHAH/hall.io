package com.lucid.scheduler;
import java.util.Date;
public class Auditorium extends Hall {
    // Constructor to initialize Auditorium with fixed rate
    public Auditorium(int id, String hallLocation, Date maintenanceStartTime, Date maintenanceEndTime) {
        super(id, "Auditorium", hallLocation, maintenanceStartTime, maintenanceEndTime); // Set hall type to "Auditorium"
    }

    // Override displayInfo to show specific details for Auditorium
    @Override
    public String displayInfo() {
        return "Auditorium: " + getHallLocation() + ", Maintenance Start: " + getMaintenanceStartTime() + ", Maintenance End: " + getMaintenanceEndTime();
    }
}
