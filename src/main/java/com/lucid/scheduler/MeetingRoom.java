package com.lucid.scheduler;
import java.util.Date;
public class MeetingRoom extends Hall {
    // Constructor to initialize MeetingRoom with fixed rate
    public MeetingRoom(int id, String hallLocation, Date maintenanceStartTime, Date maintenanceEndTime) {
        super(id, "Meeting Room", hallLocation, maintenanceStartTime, maintenanceEndTime); // Set hall type to "Meeting Room"
    }

    // Override displayInfo to show specific details for Meeting Room
    @Override
    public String displayInfo() {
        return "Meeting Room: " + getHallLocation() + ", Maintenance Start: " + getMaintenanceStartTime() + ", Maintenance End: " + getMaintenanceEndTime();
    }
}
