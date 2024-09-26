package com.hallio.sms.tests;

import com.hallio.dms.DatabaseManager;
import com.hallio.dms.FileManager;
import com.hallio.admin.usermgment;
import com.hallio.admin.User;
import com.hallio.sms.*;
import com.hallio.bms.Booking;
import com.hallio.bms.BookingManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SalesManagerTest {
    @BeforeEach
    public void setUp() {
        // Create a new database for sales
        FileManager.deleteEnvironment();
        FileManager.createEnvironment();
        DatabaseManager.createDatabase("users");
        DatabaseManager.createDatabase("booking");
        DatabaseManager.createDatabase("sales");
        DatabaseManager.createDatabase("relatedSalesBooking");
    }

    @AfterEach
    public void reset(){
        // Delete the database for sales
        DatabaseManager.deleteDatabase("users");
        DatabaseManager.deleteDatabase("booking");
        DatabaseManager.deleteDatabase("sales");
        DatabaseManager.deleteDatabase("relatedSalesBooking");
        FileManager.deleteEnvironment();
    }

    @Test
    public void testAddSales() throws Exception {
        // Add sales with related booking to the database
        usermgment.userCreate("customer1", "customer2", "customer", "customer", "customer", "+601123244", "apspace@apmail.com");
        BookingManager.createBooking(1, 1);

        RelatedSalesBooking relatedSalesBooking = new RelatedSalesBooking(1, new int[]{1});
        Sales sales = new Sales(1, 1, relatedSalesBooking, SalesStatus.INPROGRESS, 1, null);
        SalesManager.addSales(sales);
        Sales result = new Sales(0, 0, null, SalesStatus.INPROGRESS, 0, null);
        DatabaseManager.readRecord("sales", 1, result);

        RelatedSalesBooking readRelatedSalesBooking = new RelatedSalesBooking(0, new int[0]);
        DatabaseManager.readRecord("relatedSalesBooking", 1, readRelatedSalesBooking);
        assertEquals(1, result.id);
        assertEquals(1, result.getCustomerId());


        assertArrayEquals(relatedSalesBooking.getRelatedBookings(), readRelatedSalesBooking.getRelatedBookings());
        assertEquals(SalesStatus.INPROGRESS, result.getStatus());
        assertEquals(1, result.getSaleAmount());
    }

    @Test
    public void testRemoveSales() throws Exception {
        // Add sales with related booking to the database
        usermgment.userCreate("customer1", "customer2", "customer", "customer", "customer", "+601123244", "apspace@apmail.com");
        BookingManager.createBooking(1, 1);
        RelatedSalesBooking relatedSalesBooking = new RelatedSalesBooking(1, new int[]{1});
        Sales sales = new Sales(1, 1, relatedSalesBooking, SalesStatus.INPROGRESS, 1, null);
        SalesManager.addSales(sales);
        SalesManager.removeSales(sales);
        Sales result = new Sales(0, 0, null, SalesStatus.INPROGRESS, 0, null);
        try {
            DatabaseManager.readRecord("sales", 1, result);
        }catch (Exception e){
            assertEquals("Record not found", e.getMessage());
        }
    }

    @Test
    public void testUpdateSales() throws Exception {
        // Add sales with related booking to the database
        usermgment.userCreate("customer1", "customer2", "customer", "customer", "customer", "+601123244", "apspace@apmail.com");
        usermgment.userCreate("customer2", "customer2", "customer", "customer", "customer", "+601123244", "apspace@apmail.com");
        BookingManager.createBooking(1, 1);
        RelatedSalesBooking relatedSalesBooking = new RelatedSalesBooking(1, new int[]{1});
        Sales sales = new Sales(1, 1, relatedSalesBooking, SalesStatus.INPROGRESS, 1, null);
        SalesManager.addSales(sales);
        Sales updatedSales = new Sales(1, 2, relatedSalesBooking, SalesStatus.CANCELLED, 2, null);
        SalesManager.updateSales(updatedSales);
        Sales result = new Sales(0, 0, null, SalesStatus.INPROGRESS, 0, null);
        DatabaseManager.readRecord("sales", 1, result);
        RelatedSalesBooking readRelatedSalesBooking = new RelatedSalesBooking(0, new int[0]);
        DatabaseManager.readRecord("relatedSalesBooking", 1, readRelatedSalesBooking);
        assertEquals(1, result.id);
        assertEquals(2, result.getCustomerId());
        assertArrayEquals(relatedSalesBooking.getRelatedBookings(), readRelatedSalesBooking.getRelatedBookings());
        assertEquals(SalesStatus.CANCELLED, result.getStatus());
        assertEquals(2, result.getSaleAmount());
    }

    @Test
    public void testGetSales() throws Exception {
        // Add sales with related booking to the database
        usermgment.userCreate("customer1", "customer2", "customer", "customer", "customer", "+601123244", "apspace@apmail.com");
        BookingManager.createBooking(1, 1);
        RelatedSalesBooking relatedSalesBooking = new RelatedSalesBooking(1, new int[]{1});
        Sales sales = new Sales(1, 1, relatedSalesBooking, SalesStatus.INPROGRESS, 1, null);
        SalesManager.addSales(sales);
        Sales result = SalesManager.getSales(1);
        assertEquals(1, result.id);
        assertEquals(1, result.getCustomerId());
        assertArrayEquals(relatedSalesBooking.getRelatedBookings(), result.getRelatedBookings().getRelatedBookings());
        assertEquals(SalesStatus.INPROGRESS, result.getStatus());
        assertEquals(1, result.getSaleAmount());
    }

    @Test
    public void testGetSalesWithinDateRange() throws Exception {
        // Add sales with related booking to the database
        usermgment.userCreate("customer1", "customer2", "customer", "customer", "customer", "+601123244", "asd@mail.com");
        BookingManager.createBooking(1, 1);
        RelatedSalesBookingManager.addRelatedSalesBooking(new RelatedSalesBooking(1, new int[]{1}));
        RelatedSalesBooking relatedSalesBooking = RelatedSalesBookingManager.getRelatedSalesBooking(1);
        Sales sales = new Sales(1, 1, relatedSalesBooking, SalesStatus.INPROGRESS, 1, null);
        SalesManager.addSales(sales);
        Sales[] result = SalesManager.getSalesWithinDateRange(new Date(0), null);
        assertEquals(1, result.length);
        assertEquals(1, result[0].id);
        assertEquals(1, result[0].getCustomerId());
        assertArrayEquals(relatedSalesBooking.getRelatedBookings(), result[0].getRelatedBookings().getRelatedBookings());
        assertEquals(SalesStatus.INPROGRESS, result[0].getStatus());
        assertEquals(1, result[0].getSaleAmount());
    }
}
