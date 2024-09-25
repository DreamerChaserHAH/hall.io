package com.hallio.bms;

import com.hallio.dms.DatabaseManager;

public class BookingManager
{
    private static final String databaseName = "booking";
    public static void createBooking(int hallId, int saleId)
    {
        int nextId = DatabaseManager.getNext(databaseName);
        Booking booking = new Booking(nextId, hallId, saleId);
        DatabaseManager.createRecord(databaseName, booking);
    }

    public static Booking readBooking(int bookingId) throws Exception {
        Booking booking = new Booking(bookingId, 0, 0);
        DatabaseManager.readRecord(databaseName, bookingId, booking);
        return booking;
    }

    public static void deleteBooking(int bookingId) throws Exception {
        Booking booking = new Booking(bookingId, 0, 0);
        DatabaseManager.deleteRecord(databaseName, bookingId);
    }

    public static void updateBooking(int bookingId, int hallId, int saleId) throws Exception {
        Booking booking = new Booking(bookingId, hallId, saleId);
        DatabaseManager.updateRecord(databaseName, bookingId, booking);
    }
}
