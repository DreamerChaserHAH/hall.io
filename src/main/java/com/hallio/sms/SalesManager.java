package com.hallio.sms;

import com.hallio.admin.User;
import com.hallio.dms.DatabaseManager;
import com.hallio.admin.usermgment;

import java.util.Objects;

public class SalesManager {
    private static final String databaseName = "sales";
    private static final String userDatabaseName = "users";

    private static boolean isUserCustomer(int customerId){
        User user = new User();
        try {
            DatabaseManager.readRecord(userDatabaseName, customerId, user);
        }catch(Exception e){
            //e.printStackTrace();
            return false;
        }
        return Objects.equals(user.getRole(), "customer");
    }

    public static void addSales(Sales sales) {
        // Add sales to the database
        if(!isUserCustomer(sales.getCustomerId())){
            throw new IllegalArgumentException("Customer ID is not a customer");
        }
        DatabaseManager.createRecord(databaseName, sales);
        RelatedSalesBookingManager.addRelatedSalesBooking(sales.relatedBookings);
    }

    public static void removeSales(Sales sales) throws Exception {
        // Remove sales from the database
        DatabaseManager.deleteRecord(databaseName, sales.id);
        RelatedSalesBookingManager.removeRelatedSalesBooking(sales.relatedBookings);
    }

    public static void updateSales(Sales sales) throws Exception {
        if(!isUserCustomer(sales.getCustomerId())){
            throw new IllegalArgumentException("Customer ID is not a customer");
        }
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
