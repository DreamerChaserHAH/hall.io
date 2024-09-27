package com.hallio.hms.schedule;

import com.hallio.dms.IObject;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Schedule extends IObject {
    private int hallId;
    private int bookingId;
    private Date startTime;
    private Date endTime;
    private ScheduleType scheduleType;

    public Schedule(int id, int hallId, int bookingId, Date startTime, Date endTime, ScheduleType scheduleType) {
        this.id = id;
        this.hallId = hallId;
        this.bookingId = bookingId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.scheduleType = scheduleType;
    }

    public Schedule() {
        this.id = 0;
        this.hallId = 0;
        this.bookingId = 0;
        this.startTime = new Date();
        this.endTime = new Date();
        this.scheduleType = ScheduleType.AVAILABLE;
    }

    public int getHallId() {
        return hallId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public ScheduleType getScheduleType() {
        return scheduleType;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setScheduleType(ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }

    @Override
    protected LinkedList<String> getAttributes() {
        LinkedList<String> attributes = new LinkedList<>();
        attributes.add(String.valueOf(hallId));
        attributes.add(String.valueOf(bookingId));
        attributes.add(String.valueOf(startTime.getTime()));
        attributes.add(String.valueOf(endTime.getTime()));
        attributes.add(scheduleType.name());
        return attributes;
    }

    @Override
    protected String getFilePath() {
        return "";
    }

    @Override
    protected void loadFromString(List<String> list) {
        this.id = Integer.parseInt(list.get(0));
        this.hallId = Integer.parseInt(list.get(1));
        this.bookingId = Integer.parseInt(list.get(2));
        this.startTime = new Date(Long.parseLong(list.get(3)));
        this.endTime = new Date(Long.parseLong(list.get(4)));
        this.scheduleType = ScheduleType.valueOf(list.get(5));
    }
}
