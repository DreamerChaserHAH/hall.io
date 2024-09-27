package com.hallio.sms;

import com.hallio.admin.User;
import com.hallio.dms.DatabaseManager;
import com.hallio.admin.usermgment;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
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

    public static void addSales(Sales sales) throws Exception {
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
        Sales sales = new Sales(0, 0, null, SalesStatus.INPROGRESS, 0, null);
        DatabaseManager.readRecord(databaseName, id, sales);
        sales.relatedBookings = RelatedSalesBookingManager.getRelatedSalesBooking(sales.id);
        return sales;
    }

    public static Sales[] getSalesWithinDateRange(Date startDate, Date endDate) throws Exception {

        if(startDate == null){
            startDate = new Date(0);
        }

        if(endDate == null){
            endDate = new Date();
        }

        // Retrieve sales from the database within the date range
        Sales[] AllSales = new Sales[DatabaseManager.getAmountOfRecords(databaseName)];
        for(int i = 0; i < AllSales.length; i++){
            AllSales[i] = new Sales(0, 0, null, SalesStatus.INPROGRESS, 0, null);
        }

        Sales[] filteredSales = new Sales[0];
        RelatedSalesBooking[] relatedSalesBookings = new RelatedSalesBooking[0];

        DatabaseManager.readAllRecords(databaseName, AllSales);

        for(Sales sale : AllSales){
            sale.relatedBookings = RelatedSalesBookingManager.getRelatedSalesBooking(sale.id);
            if(sale.getDate().after(startDate) && sale.getDate().before(endDate)){
                if(filteredSales.length == 0){
                    filteredSales = new Sales[1];
                }else {
                    filteredSales = Arrays.copyOf(filteredSales, filteredSales.length + 1);
                }
                filteredSales[filteredSales.length - 1] = sale;
            }
        }
        return filteredSales;
    }

    public static LinkedList<Sales[]> getSalesByWeek(Date endDate) throws Exception {
        LinkedList<Sales[]> salesByWeek = new LinkedList<>();

        if(endDate == null){
            endDate = new Date();
        }
        // start date is one year before the end date
        long yearLong = 604800000L * 52;
        Date currentStartDate = new Date(endDate.getTime() - yearLong);
        // current end date is 1 week after the start date
        Date currentEndDate = new Date(currentStartDate.getTime() + 604800000);

        int i = 0;
        while(currentStartDate.before(endDate)){

            if(i == 50){
                System.out.println("i is 51");
            }
            salesByWeek.add(getSalesWithinDateRange(currentStartDate, currentEndDate));
            currentStartDate = currentEndDate;
            currentEndDate = new Date(currentEndDate.getTime() + 604800000);
            i++;
        }
        return salesByWeek;
    }

    public static LinkedList<Sales[]> getSalesByMonth(Date endDate) throws Exception {
        LinkedList<Sales[]> salesByMonth = new LinkedList<Sales[]>();
        if(endDate == null){
            endDate = new Date();
        }
        // start date is one year before the end date
        long yearLong = 604800000L * 52;
        Date currentStartDate = new Date(endDate.getTime() - yearLong);
        // current end date is 1 month after the start date
        Date currentEndDate = new Date(currentStartDate.getTime() + 604800000 * 4);
        while(currentStartDate.before(endDate)){
            salesByMonth.add(getSalesWithinDateRange(currentStartDate, currentEndDate));
            currentStartDate = currentEndDate;
            currentEndDate = new Date(currentEndDate.getTime() + 604800000L * 4);
        }
        return salesByMonth;
    }

    public static LinkedList<Sales[]> getSalesByYear(Date endDate) throws Exception {
        LinkedList<Sales[]> salesByYear = new LinkedList<>();

        if(endDate == null){
            endDate = new Date();
        }
        // start date is one year before the end date
        long yearLong = 604800000L * 52;
        Date currentStartDate = new Date(endDate.getTime() - yearLong);
        // current end date is 1 week after the start date
        Date currentEndDate = new Date(currentStartDate.getTime() + 604800000);

        while(currentStartDate.before(endDate)){
            salesByYear.add(getSalesWithinDateRange(currentStartDate, currentEndDate));
            currentStartDate = currentEndDate;
            currentEndDate = new Date(currentEndDate.getTime() + 604800000L * 52);
        }

        return salesByYear;
    }

    public static LinkedList<Sales[]> getSalesByCustomer(){
        LinkedList<Sales[]> salesByCustomer = new LinkedList<>();
        Sales[] AllSales = new Sales[0];
        try {
            AllSales = getSalesWithinDateRange(null, null);
        }catch(Exception e) {
            e.printStackTrace();
        }
        LinkedList<Integer> customerIds = new LinkedList<>();
        for(Sales sale : AllSales){
            if(!customerIds.contains(sale.getCustomerId())){
                customerIds.add(sale.getCustomerId());
            }
        }
        for(int customerId : customerIds){
            Sales[] sales = new Sales[0];
            for(Sales sale : AllSales){
                if(sale.getCustomerId() == customerId){
                    if(sales.length == 0){
                        sales = new Sales[1];
                    }else {
                        sales = Arrays.copyOf(sales, sales.length + 1);
                    }
                    sales[sales.length - 1] = sale;
                }
            }
            salesByCustomer.add(sales);
        }
        return salesByCustomer;
    }
}
