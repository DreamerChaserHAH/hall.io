package com.hallio.gui.windows.customers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class CancelBookingGUI extends JFrame {

    private JPanel bookingPanel; // Panel to hold all booking panels
    private ArrayList<JPanel> bookingItemPanels; // List to hold each booking panel

    // Sample bookings data
    private ArrayList<String> bookings;

    public CancelBookingGUI() {
        setTitle("Cancel Booking");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sample data of bookings (could be replaced with actual data)
        bookings = new ArrayList<>();
        bookings.add("Booking 1: Hall A - 10:00 AM to 12:00 PM");
        bookings.add("Booking 2: Hall B - 1:00 PM to 3:00 PM");
        bookings.add("Booking 3: Hall C - 4:00 PM to 6:00 PM");

        // Create a panel to hold all booking panels
        bookingPanel = new JPanel();
        bookingPanel.setLayout(new BoxLayout(bookingPanel, BoxLayout.Y_AXIS)); // Use vertical layout

        // Create a JScrollPane to make the list scrollable
        JScrollPane scrollPane = new JScrollPane(bookingPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Initialize the list to store each booking's JPanel
        bookingItemPanels = new ArrayList<>();

        // Add each booking as a panel with a cancel button
        for (String booking : bookings) {
            addBookingItem(booking);
        }

        setVisible(true);
    }

    // Method to add a booking item panel
    private void addBookingItem(String bookingDetails) {
        JPanel bookingItemPanel = new JPanel();
        bookingItemPanel.setLayout(new BorderLayout());
        bookingItemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Optional border

        // Create a label for the booking details
        JLabel bookingLabel = new JLabel(bookingDetails);
        bookingItemPanel.add(bookingLabel, BorderLayout.CENTER);

        // Create a cancel button for this booking
        JButton cancelButton = new JButton("Cancel");
        bookingItemPanel.add(cancelButton, BorderLayout.EAST);

        // Add an ActionListener to handle the cancellation
        cancelButton.addActionListener(e -> {
                // Confirm cancellation
                int confirm = JOptionPane.showConfirmDialog(CancelBookingGUI.this,
                        "Are you sure you want to cancel this booking?",
                        "Confirm Cancellation",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    bookings.remove(bookingDetails); // Remove booking from list
                    bookingPanel.remove(bookingItemPanel); // Remove booking panel from GUI
                    bookingPanel.revalidate(); // Refresh panel
                    bookingPanel.repaint(); // Redraw panel
                    JOptionPane.showMessageDialog(CancelBookingGUI.this, "Booking cancelled successfully!");
                }
            
        });

        // Add this booking panel to the main panel
        bookingPanel.add(bookingItemPanel);
        bookingItemPanels.add(bookingItemPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CancelBookingGUI());
    }
}