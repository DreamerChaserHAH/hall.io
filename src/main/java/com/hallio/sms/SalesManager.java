package com.hallio.sms;

import com.hallio.dms.DatabaseManager;

public class SalesManager {
    private static final String databaseName = "sales";
    public static void addSales(Sales sales) {
        // Add sales to the database
        DatabaseManager.createRecord(databaseName, sales);
        RelatedSalesBookingManager.addRelatedSalesBooking(sales.relatedBookings);
    }

    public static void removeSales(Sales sales) throws Exception {
        // Remove sales from the database
        DatabaseManager.deleteRecord(databaseName, sales.id);
        RelatedSalesBookingManager.removeRelatedSalesBooking(sales.relatedBookings);
    }

    public static void updateSales(Sales sales) throws Exception {
        // Update sales in the database
        DatabaseManager.updateRecord(databaseName, sales.id, sales);
        RelatedSalesBookingManager.updateRelatedSalesBooking(sales.relatedBookings);
    }

    public static Sales getSales(int id) throws Exception {
        // Retrieve sales from the database
        Sales sales = new Sales(0, 0, null, SalesStatus.INPROGRESS, 0);
        DatabaseManager.readRecord(databaseName, id, sales);
        sales.relatedBookings = RelatedSalesBookingManager.getRelatedSalesBooking(sales.id);
        return sales;
    }
}
