package com.hallio.sms;

import com.hallio.bms.Booking;
import com.hallio.dms.DatabaseManager;

public class RelatedSalesBookingManager {
    private static final String databaseName = "relatedSalesBooking";

    public static void testReadBooking(RelatedSalesBooking relatedSalesBooking) throws Exception {
        for (int bookingId : relatedSalesBooking.getRelatedBookings()) {
            Booking booking = new Booking(0, 0, 0);
            DatabaseManager.readRecord("booking", bookingId, booking);
        }
    }

    public static void addRelatedSalesBooking(RelatedSalesBooking relatedSalesBooking) throws Exception {
        // Add related sales booking to the database
        testReadBooking(relatedSalesBooking);
        DatabaseManager.createRecord(databaseName, relatedSalesBooking);
    }

    public static void removeRelatedSalesBooking(RelatedSalesBooking relatedSalesBooking) throws Exception {
        // Remove related sales booking from the database
        DatabaseManager.deleteRecord(databaseName, relatedSalesBooking.id);
    }

    public static void updateRelatedSalesBooking(RelatedSalesBooking relatedSalesBooking) throws Exception {
        // Update related sales booking in the database
        testReadBooking(relatedSalesBooking);
        DatabaseManager.updateRecord(databaseName, relatedSalesBooking.id, relatedSalesBooking);
    }

    public static RelatedSalesBooking getRelatedSalesBooking(int id) throws Exception {
        // Retrieve related sales booking from the database
        RelatedSalesBooking relatedSalesBooking = new RelatedSalesBooking(0, new int[0]);
        DatabaseManager.readRecord(databaseName, id, relatedSalesBooking);
        return relatedSalesBooking;
    }

    public static RelatedSalesBooking[] readAllRelatedSalesBooking(){
        RelatedSalesBooking[] relatedSalesBookings = new RelatedSalesBooking[DatabaseManager.getAmountOfRecords(databaseName)];
        DatabaseManager.readAllRecords(databaseName, relatedSalesBookings);
        return relatedSalesBookings;
    }
}
