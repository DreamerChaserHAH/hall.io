package com.hallio.gui.windows.customers;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class BookingGUI {

    private static String[] halls = {"Hall A", "Hall B", "Hall C", "Hall D"};
    private static HashMap<String, String[]> hallTimes = new HashMap<>();
    
    private static String selectedHall = "";
    private static String selectedTime = "";
    
    public static void main(String[] args) {
        // Set available times for each hall
        hallTimes.put("Hall A", new String[]{"10:00 AM - 12:00 PM", "1:00 PM - 3:00 PM", "4:00 PM - 6:00 PM"});
        hallTimes.put("Hall B", new String[]{"9:00 AM - 11:00 AM", "12:00 PM - 2:00 PM", "3:00 PM - 5:00 PM"});
        hallTimes.put("Hall C", new String[]{"11:00 AM - 1:00 PM", "2:00 PM - 4:00 PM", "5:00 PM - 7:00 PM"});
        hallTimes.put("Hall D", new String[]{"8:00 AM - 10:00 AM", "10:30 AM - 12:30 PM", "1:00 PM - 3:00 PM"});

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
        JComboBox<String> hallComboBox = new JComboBox<>(halls);
        
        // Time selection label and combo box
        JLabel selectTimeLabel = new JLabel("Available Times:");
        JComboBox<String> timeComboBox = new JComboBox<>();
        
        // Set initial available times for Hall A
        updateAvailableTimes(timeComboBox, "Hall A");

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
            openPaymentWindow(frame); // Proceed to payment window
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
        for (String time : hallTimes.get(hall)) {
            timeComboBox.addItem(time); // Add new available times for selected hall
        }
    }

    // Function to open the payment window
    private static void openPaymentWindow(JFrame parentFrame) {
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
            ReceiptGUI.displayReceipt(selectedHall, selectedTime);  // Open receipt window
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

        
    
    

