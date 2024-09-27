package com.hallio.sms;

import com.hallio.dms.IObject;

import java.util.LinkedList;
import java.util.List;

public class RelatedSalesBooking extends IObject {
    private int[] relatedBookings;
    public RelatedSalesBooking(int id, int[] relatedBookings) {
        this.id = id;
        this.relatedBookings = relatedBookings;
    }

    public int[] getRelatedBookings() {
        return this.relatedBookings;
    }

    public void setRelatedBookings(int[] relatedBookings) {
        this.relatedBookings = relatedBookings;
    }

    @Override
    protected LinkedList<String> getAttributes() {
        List<String> relatedBookingsList = new LinkedList<String>();
        for (int bookingId : relatedBookings) {
            relatedBookingsList.add(String.valueOf(bookingId));
        }
        return new LinkedList<String>(
                relatedBookingsList
        );
    }

    @Override
    protected String getFilePath() {
        return "RelatedSalesBooking.txt";
    }

    @Override
    protected void loadFromString(List<String> list) {
        id = Integer.parseInt(list.get(0));
        if(list.size() == 1) {
            relatedBookings = new int[0];
            return;
        }
        relatedBookings = new int[list.size() - 1];
        for (int i = 1; i < list.size(); i++) {
            relatedBookings[i - 1] = Integer.parseInt(list.get(i));
        }
    }
}
