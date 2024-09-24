package Scheduler;

public class Hall {
    private int id;
    private String hallType;
    private String location;
    private double hourlyRate;
    private int totalSeats;

    public Hall(int id, String hallType, String location, double hourlyRate, int totalSeats) {
        this.id = id;
        this.hallType = hallType;
        this.location = location;
        this.hourlyRate = hourlyRate;
        this.totalSeats = totalSeats;
    }

    public Hall(int id, String name, String location, Object o, Object o1, double hourlyRate, int totalSeats) {
    }

    public int getId() {
        return id;
    }

    public String getHallType() {
        return hallType;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setHallType(String hallType) {
        this.hallType = hallType;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    @Override
    public String toString() {
        return hallType + " (RM " + hourlyRate + ", Seats: " + totalSeats + ")";
    }
}