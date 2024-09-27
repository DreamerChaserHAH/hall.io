package com.hallio.gui.windows.customers;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.hallio.bms.Booking;
import com.hallio.bms.BookingManager;
import com.hallio.dms.DatabaseManager;
import com.hallio.hms.Hall;
import com.hallio.hms.HallManager;
import com.hallio.hms.schedule.Schedule;
import com.hallio.hms.schedule.ScheduleManager;
import com.hallio.sms.*;

public class BookingGUI {

    private static String[] hallNames = {"Hall A", "Hall B", "Hall C", "Hall D"};
    private static HashMap<String, String[]> hallTimes = new HashMap<>();
    
    private static String selectedHall = "";
    private static String selectedTime = "";
    
    public static void main(String[] args) {

        //get the halls from HallManager.loadHalls() and convert it into and Array
        Hall[] halls = new Hall[DatabaseManager.getAmountOfRecords("detailHalls")];
        for(int i = 0; i < halls.length; i++){
            halls[i] = new Hall();
        }
        DatabaseManager.readAllRecords("detailHalls", halls);

        HashMap<String, Hall> hallMap = new HashMap<>();
        hallNames = new String[halls.length];
        for(int i = 0; i < halls.length; i++){
            hallNames[i] = "Hall" + halls[i].getId();
            hallMap.put(hallNames[i], halls[i]);
        }

        Schedule[] schedules = new Schedule[DatabaseManager.getAmountOfRecords("schedule")];
        for(int i = 0; i < schedules.length; i++){
            schedules[i] = new Schedule();
        }
        DatabaseManager.readAllRecords("schedule", schedules);

        // Group Schedule by Hall ID
        HashMap<Integer, ArrayList<Schedule>> hallSchedules = new HashMap<>();
        for(Schedule schedule : schedules){
            if(hallSchedules.containsKey(schedule.getHallId())){
                hallSchedules.get(schedule.getHallId()).add(schedule);
            } else {
                ArrayList<Schedule> newSchedule = new ArrayList<>();
                newSchedule.add(schedule);
                hallSchedules.put(schedule.getHallId(), newSchedule);
            }
        }

        // Set available times for each hall
        for(int i = 0; i < hallNames.length; i++){
            ArrayList<Schedule> hallSchedule = hallSchedules.get(halls[i].id);
            if(hallSchedule != null) {
                String[] times = new String[hallSchedule.size()];
                for (int j = 0; j < hallSchedule.size(); j++) {
                    times[j] = hallSchedule.get(j).getStartTime().toString() + " - " + hallSchedule.get(j).getEndTime();
                }
                hallTimes.put(hallNames[i], times);
            }
        }

        // Create the main frame
        JFrame frame = new JFrame("Hall Booking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Create a panel for hall selection and time selection
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        
        // Hall selection label and combo box
        JLabel selectHallLabel = new JLabel("Select a Hall:");
        JComboBox<String> hallComboBox = new JComboBox<>(hallNames);
        
        // Time selection label and combo box
        JLabel selectTimeLabel = new JLabel("Available Times:");
        JComboBox<String> timeComboBox = new JComboBox<>();
        
        // Set initial available times for Hall A
        updateAvailableTimes(timeComboBox, hallNames[0]);

        // Update available times when a hall is selected
        hallComboBox.addActionListener(e -> {
            selectedHall = (String) hallComboBox.getSelectedItem();
            updateAvailableTimes(timeComboBox, selectedHall); // Update times dynamically
        });

        // "Proceed to Payment" button
        JButton proceedButton = new JButton("Proceed to Payment");

        // Add action listener to the proceed button
        proceedButton.addActionListener(e -> {
            selectedTime = (String) timeComboBox.getSelectedItem(); // Get selected time
            openPaymentWindow(
                    frame,
                    hallMap.get(selectedHall),
                    hallSchedules.get(hallMap.get(selectedHall).id).get(timeComboBox.getSelectedIndex())
            ); // Proceed to payment window
        });

        // Layout the components
        gbc.gridx = 0;
        gbc.gridy = 0;
        selectionPanel.add(selectHallLabel, gbc);
        
        gbc.gridx = 1;
        selectionPanel.add(hallComboBox, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        selectionPanel.add(selectTimeLabel, gbc);
        
        gbc.gridx = 1;
        selectionPanel.add(timeComboBox, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        selectionPanel.add(proceedButton, gbc);

        // Add the selection panel to the frame
        frame.add(selectionPanel, BorderLayout.CENTER);

        // Set frame visibility
        frame.setVisible(true);
    }

    // Function to update the available times based on the selected hall
    private static void updateAvailableTimes(JComboBox<String> timeComboBox, String hall) {
        timeComboBox.removeAllItems(); // Clear previous items
        if(hallTimes.get(hall) != null) {
            for (String time : hallTimes.get(hall)) {
                timeComboBox.addItem(time); // Add new available times for selected hall
            }
        }
    }

    // Function to open the payment window
    private static void openPaymentWindow(JFrame parentFrame, Hall hall, Schedule schedule) {
        // Create a new frame for payment
        JFrame paymentFrame = new JFrame("Payment");
        paymentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        paymentFrame.setSize(400, 200);

        // Payment details (mockup)
        JLabel paymentLabel = new JLabel("Enter Payment Details:");

        // Payment text fields
        JTextField cardNumberField = new JTextField(15);
        JTextField cardNameField = new JTextField(15);

        // Payment submit button
        JButton payButton = new JButton("Pay Now");

        // Add action listener to the pay button
        payButton.addActionListener(e -> {
            String cardHolderName = cardNameField.getText();
            paymentFrame.dispose(); // Close payment window

            int SalesID = DatabaseManager.getNext("sales");

            Booking newBooking = new Booking(DatabaseManager.getNext("booking"), hall.id, SalesID);
            BookingManager.createBooking(hall.id, SalesID);

            //create an int array with one element
            int[] newBookingIDList = new int[1];
            newBookingIDList[0] = newBooking.id;
            RelatedSalesBooking newRelatedSalesBooking = new RelatedSalesBooking(SalesID, newBookingIDList);
            try {
                RelatedSalesBookingManager.addRelatedSalesBooking(newRelatedSalesBooking);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            double Price = 100;
            try {
                SalesManager.addSales(
                        new Sales(
                                SalesID,
                                6,
                                newRelatedSalesBooking,
                                SalesStatus.PAID,
                                (int) Price,
                                new Date()
                        )
                ); // Add sales record
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            ReceiptGUI.displayReceipt(selectedHall, selectedTime, (int) Price);  // Open receipt window
        });

        // Layout components in the payment frame
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new GridLayout(3, 2, 10, 10));
        paymentPanel.add(new JLabel("Card Number:"));
        paymentPanel.add(cardNumberField);
        paymentPanel.add(new JLabel("Cardholder Name:"));
        paymentPanel.add(cardNameField);
        paymentPanel.add(new JLabel());
        paymentPanel.add(payButton);

        // Add components to the payment frame
        paymentFrame.add(paymentLabel, BorderLayout.NORTH);
        paymentFrame.add(paymentPanel, BorderLayout.CENTER);

        // Set visibility of payment window
        paymentFrame.setVisible(true);
    }
}

        
    
    

