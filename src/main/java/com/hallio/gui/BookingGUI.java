package com.hallio.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javafx.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BookingGUI {
    
    private static String[] halls = {"Hall A", "Hall B", "Hall C", "Hall D"};
    private static String selectedHall = "";
    
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Hall Booking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Create label and combo box for hall selection
        JLabel selectHallLabel = new JLabel("Select a Hall:");
        JComboBox<String> hallComboBox = new JComboBox<>(halls);

        // Create a proceed button
        JButton proceedButton = new JButton("Proceed to Payment");

        // Add action listener to proceed button
        proceedButton.addActionListener(e -> {
                // Get selected hall
                selectedHall = (String) hallComboBox.getSelectedItem();
                // Open payment window
                openPaymentWindow(frame);
            });
        

        // Layout components
        JPanel hallPanel = new JPanel();
        hallPanel.setLayout(new FlowLayout());
        hallPanel.add(selectHallLabel);
        hallPanel.add(hallComboBox);

        // Add components to the frame
        frame.add(hallPanel, BorderLayout.CENTER);
        frame.add(proceedButton, BorderLayout.SOUTH);

        // Set frame visibility
        frame.setVisible(true);
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
            
                // After payment is done, open the receipt window (new Java file)
                String cardHolderName = cardNameField.getText();
                paymentFrame.dispose();  // Close payment window
                ReceiptGUI.displayReceipt(cardHolderName, selectedHall);  // Open receipt
            
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
        
    
    

