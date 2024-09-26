package com.hallio.bms;

import com.hallio.dms.IObject;

import java.util.LinkedList;
import java.util.List;

public class Booking extends IObject {
    private int hallId;
    private int saleid;

    public Booking(int bookingId, int hallId, int saleid) {
        this.id = bookingId;
        this.hallId = hallId;
        this.saleid = saleid;
    }

    public int getHallId() {
        return hallId;
    }

    public int getSaleid() {
        return saleid;
    }

    @Override
    protected LinkedList<String> getAttributes() {
        LinkedList<String> list = new LinkedList<>();
        list.add(String.valueOf(hallId));
        list.add(String.valueOf(saleid));
        return list;
    }

    @Override
    protected String getFilePath() {
        return "booking.txt";
    }

    @Override
    protected void loadFromString(List<String> list) {
        this.hallId = Integer.parseInt(list.get(0));
        this.saleid = Integer.parseInt(list.get(1));
    }
}