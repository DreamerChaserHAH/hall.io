package com.hallio.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedList;

import com.hallio.admin.usermgment;
import com.hallio.bms.BookingManager;
import com.hallio.dms.DatabaseManager;
import com.hallio.dms.FileManager;
import com.hallio.sms.*;
import javafx.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class SalesGUI extends JFrame {

    private JTable salesTable;
    private JComboBox<String> filterComboBox;
    private JButton filterButton;

    public SalesGUI() {
        /*try {
            FileManager.createEnvironment();
            DatabaseManager.createDatabase("users");
            DatabaseManager.createDatabase("sales");
            DatabaseManager.createDatabase("relatedSalesBooking");
            DatabaseManager.createDatabase("booking");

            usermgment.userCreate("customer1", "customer2", "customer", "customer", "customer", "+601123244", "asd@mail.com");
            BookingManager.createBooking(1, 1);
            RelatedSalesBookingManager.addRelatedSalesBooking(new RelatedSalesBooking(1, new int[]{1}));
            RelatedSalesBooking relatedSalesBooking = RelatedSalesBookingManager.getRelatedSalesBooking(1);
            Sales sales = new Sales(1, 1, relatedSalesBooking, SalesStatus.INPROGRESS, 1, null);
            SalesManager.addSales(sales);
        }catch (Exception e){
            e.printStackTrace();
        }*/

        setTitle("Sales Overview");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for the title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("View Sales from Bookings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Panel for the table and sales data
        JPanel salesPanel = new JPanel(new BorderLayout());
        salesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Dummy data for sales (you can replace it with real data)
        String[] columnNames = {"ID", "Halls Booked", "Sales State", "Customer Name", "Date", "Amount (RM)"};

        Sales[] sales;
        ///reading from sales manager and creating the list for displaying
        try {
            sales = SalesManager.getSalesWithinDateRange(null, null);
        } catch (Exception e) {
            sales = new Sales[0];
        }
        Object[][] data = new Object[sales.length][6];
        for(int i = 0; i < sales.length; i++){
            data[i][0] = sales[i].id;
            data[i][1] = Arrays.toString(sales[i].getRelatedBookings().getRelatedBookings());
            data[i][2] = sales[i].getStatus();
            data[i][3] = sales[i].getCustomerId();
            data[i][4] = sales[i].getDate().toString();
            data[i][5] = sales[i].getSaleAmount() + " RM";
        }

        // Create a table to display sales
        salesTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(salesTable);
        salesPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel for the filter options
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel filterLabel = new JLabel("Filter by:");
        filterLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Dropdown for filtering options
        filterComboBox = new JComboBox<>(new String[]{"Per Sale", "Per Customer", "Weekly", "Monthly", "Yearly"});
        filterComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        // Filter button
        filterButton = new JButton("Apply Filter");
        filterButton.setFont(new Font("Arial", Font.BOLD, 14));
        filterButton.setBackground(new Color(58, 115, 255)); // Elegant blue color
        filterButton.setForeground(Color.WHITE);
        filterButton.setFocusPainted(false);
        filterButton.setPreferredSize(new Dimension(120, 40));

        // Add components to the filter panel
        filterPanel.add(filterLabel);
        filterPanel.add(filterComboBox);
        filterPanel.add(filterButton);

        // Add the filter panel to the bottom of the sales panel
        salesPanel.add(filterPanel, BorderLayout.SOUTH);
        add(salesPanel, BorderLayout.CENTER);

        // Action listener for the filter button (you can implement filtering logic)
        filterButton.addActionListener(e -> {
            
                String selectedFilter = (String) filterComboBox.getSelectedItem();
                JOptionPane.showMessageDialog(SalesGUI.this, "Filter applied: " + selectedFilter);
                // Implement filtering logic here
                // For example, filter sales based on the selected filter
                // Update the table with the filtered data
                if(selectedFilter.equals("Per Sale")) {
                    // Filter sales for the current week
                    Sales[] filteredSales = new Sales[0];
                    try {
                        filteredSales = SalesManager.getSalesWithinDateRange(null, null);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    // Update the table with the filtered data
                    Object[][] filteredData = new Object[filteredSales.length][6];
                    for(int i = 0; i < filteredSales.length; i++){
                        filteredData[i][0] = filteredSales[i].id;
                        filteredData[i][1] = Arrays.toString(filteredSales[i].getRelatedBookings().getRelatedBookings());
                        filteredData[i][2] = filteredSales[i].getStatus();
                        filteredData[i][3] = filteredSales[i].getCustomerId();
                        filteredData[i][4] = filteredSales[i].getDate().toString();
                        filteredData[i][5] = filteredSales[i].getSaleAmount() + " RM";
                    }
                    salesTable.setModel(new javax.swing.table.DefaultTableModel(
                            filteredData,
                            columnNames
                    ));
                }
                else if(selectedFilter.equals("Per Customer")){
                    LinkedList<Sales[]> salesByCustomer = new LinkedList<>();
                    try {
                        salesByCustomer = SalesManager.getSalesByCustomer();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    String[] customerColumnNames = {"ID", "Halls Booked", "Customer Name", "Amount (RM)"};
                    Object[][] customerData = new Object[salesByCustomer.size()][4];
                    for(int i = 0; i < salesByCustomer.size(); i++){
                        customerData[i][0] = salesByCustomer.get(i)[0].id;
                        customerData[i][1] = salesByCustomer.get(i).length;
                        customerData[i][2] = salesByCustomer.get(i)[0].getCustomerId();
                        int totalAmount = 0;
                        for(Sales sale : salesByCustomer.get(i)){
                            totalAmount += sale.getSaleAmount();
                        }
                        customerData[i][3] = totalAmount + " RM";
                    }

                    salesTable.setModel(new javax.swing.table.DefaultTableModel(
                            customerData,
                            customerColumnNames
                    ));
                }
                else if(selectedFilter.equals("Weekly")) {
                    LinkedList<Sales[]> salesByWeek = new LinkedList<>();
                    try {
                        salesByWeek = SalesManager.getSalesByWeek(null);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    // Filter sales for the current week
                    String[] weeklyColumnNames = {"Week ID", "Customers", "Halls Booked", "Sales Amount"};
                    Object[][] weeklyData = new Object[salesByWeek.size()][4];
                    for(int i = 0; i < salesByWeek.size(); i++){
                        weeklyData[51 - i][0] = i + 1;
                        weeklyData[51 - i][1] = salesByWeek.get(i).length;
                        int hallsBooked = 0;
                        int salesAmount = 0;
                        for(Sales sale : salesByWeek.get(i)){
                            hallsBooked += sale.getRelatedBookings().getRelatedBookings().length;
                            salesAmount += sale.getSaleAmount();
                        }
                        weeklyData[51 - i][2] = hallsBooked;
                        weeklyData[51 - i][3] = salesAmount + " RM";
                    }

                    // Update the table with the filtered data
                    salesTable.setModel(new javax.swing.table.DefaultTableModel(
                            weeklyData,
                            weeklyColumnNames
                    ));
                }else if(selectedFilter.equals("Monthly")){
                    LinkedList<Sales[]> salesByMonthly = new LinkedList<Sales[]>();
                    try {
                        salesByMonthly = SalesManager.getSalesByMonth(null);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    // Filter sales for the current week
                    String[] monthlyColumnNames = {"Week ID", "Customers", "Halls Booked", "Sales Amount"};
                    Object[][] monthlyData = new Object[salesByMonthly.size()][4];
                    for(int i = 0; i < salesByMonthly.size(); i++){
                        monthlyData[i][0] = i + 1;
                        monthlyData[i][1] = salesByMonthly.get(i).length;
                        int hallsBooked = 0;
                        int salesAmount = 0;
                        for(Sales sale : salesByMonthly.get(i)){
                            hallsBooked += sale.getRelatedBookings().getRelatedBookings().length;
                            salesAmount += sale.getSaleAmount();
                        }
                        monthlyData[i][2] = hallsBooked;
                        monthlyData[i][3] = salesAmount + " RM";
                    }
                    salesTable.setModel(new javax.swing.table.DefaultTableModel(
                            monthlyData,
                            monthlyColumnNames
                    ));
                }
                else if(selectedFilter.equals("Yearly")){
                    LinkedList<Sales[]> salesByYear = new LinkedList<>();
                    try {
                        salesByYear = SalesManager.getSalesByYear(null);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    // Filter sales for the current week
                    String[] yearlyColumnNames = {"Week ID", "Customers", "Halls Booked", "Sales Amount"};
                    Object[][] yearlyData = new Object[salesByYear.size()][4];
                    for(int i = 0; i < salesByYear.size(); i++){
                        yearlyData[i][0] = i + 1;
                        yearlyData[i][1] = salesByYear.get(i).length;
                        int hallsBooked = 0;
                        int salesAmount = 0;
                        for(Sales sale : salesByYear.get(i)){
                            hallsBooked += sale.getRelatedBookings().getRelatedBookings().length;
                            salesAmount += sale.getSaleAmount();
                        }
                        yearlyData[i][2] = hallsBooked;
                        yearlyData[i][3] = salesAmount + " RM";
                    }
                    salesTable.setModel(new javax.swing.table.DefaultTableModel(
                            yearlyData,
                            yearlyColumnNames
                    ));
                }
        });

        // Center the window on the screen
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SalesGUI());
    }
}
