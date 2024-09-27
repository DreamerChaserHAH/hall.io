package com.hallio.gui.windows.customers;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ReceiptGUI {
    public static void displayReceipt(String cardHolderName, String hallName) {
        // Create a new frame for receipt
        JFrame receiptFrame = new JFrame("Receipt");
        receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        receiptFrame.setSize(400, 200);

        // Receipt information (mockup)
        JTextArea receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setText("Receipt\n"
                + "-----------------------\n"
                + "Cardholder: " + cardHolderName + "\n"
                + "Hall Booked: " + hallName + "\n"
                + "Amount: RM 1000\n"
                + "-----------------------\n"
                + "Thank you for your payment!");

        // Add receipt to the frame
        receiptFrame.add(new JScrollPane(receiptArea), BorderLayout.CENTER);

        // Set visibility of receipt window
        receiptFrame.setVisible(true);
    }
}
