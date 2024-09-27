package com.hallio.hms;

import com.hallio.dms.IObject;

import java.util.LinkedList;
import java.util.List;

public class Hall extends IObject {
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

    public Hall(){
        this.id = 0;
        this.hallType = "";
        this.location = "";
        this.hourlyRate = 0;
        this.totalSeats = 0;
    }

    @Override
    protected LinkedList<String> getAttributes() {
        LinkedList<String> attributes = new LinkedList<>();
        attributes.add(hallType);
        attributes.add(location);
        attributes.add(String.valueOf(hourlyRate));
        attributes.add(String.valueOf(totalSeats));
        return attributes;
    }

    @Override
    protected String getFilePath() {
        return "databases/detailHalls.txt"; // Path to save/load Hall data
    }

    @Override
    protected void loadFromString(List<String> attributes) {
        this.id = Integer.parseInt(attributes.get(0));
        this.hallType = attributes.get(1);
        this.location = attributes.get(2);
        this.hourlyRate = Double.parseDouble(attributes.get(3));
        this.totalSeats = Integer.parseInt(attributes.get(4));
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