package com.hallio.bms;

import com.hallio.dms.DatabaseManager;

public class BookingManager
{
    public static void createBooking(int hallId, int saleId)
    {
        Booking booking = new Booking(1, hallId, saleId);
        booking.save();
    }

    public static void readBooking(int bookingId) throws Exception {
        Booking booking = new Booking(bookingId, 0, 0);
        DatabaseManager.readRecord(booking.getFilePath(), bookingId, booking);
    }

    public static void deleteBooking(int bookingId) throws Exception {
        Booking booking = new Booking(bookingId, 0, 0);
        DatabaseManager.deleteRecord(booking.getFilePath(), bookingId);
    }

    public static void updateBooking(int bookingId, int hallId, int saleId) throws Exception {
        Booking booking = new Booking(bookingId, hallId, saleId);
        DatabaseManager.updateRecord(booking.getFilePath(), bookingId, booking);
    }

}
