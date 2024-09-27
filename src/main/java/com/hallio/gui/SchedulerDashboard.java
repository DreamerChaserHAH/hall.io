package com.hallio.gui;

import com.hallio.hms.HallManager;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SchedulerDashboard extends JFrame {
    private final HallManager hallManager;

    public SchedulerDashboard(HallManager hallManager) {
        this.hallManager = hallManager;
        createAndShowDashboard();
    }

    private void createAndShowDashboard() {
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window

        //Set a gradient background

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(128, 0, 128), 0, getHeight(), new Color(75, 0, 130));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridLayout(2, 2, 20, 20)); // 2 rows, 2 columns with gaps
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create buttons with advanced styling
        JButton hallManagementButton = createStyledButton("Hall Management", "icons/manage.png");
        hallManagementButton.addActionListener(_ -> openHallManagement());
        panel.add(hallManagementButton);

        JButton viewAvailableHallsButton = createStyledButton("View Available Halls", "icons/view.png");
        viewAvailableHallsButton.addActionListener(_ -> showAvailableHalls());
        panel.add(viewAvailableHallsButton);

        JButton logoutButton = createStyledButton("Logout", "icons/logout.png");
        logoutButton.addActionListener(_ -> logout());
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(iconPath)); // Set icon
        button.setBackground(new Color(100, 0, 150)); // Darker purple
        button.setForeground(Color.WHITE); // White text
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2)); // Dark border
        button.setPreferredSize(new Dimension(200, 50));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(10); // Space between icon and text

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(120, 0, 180)); // Lighter purple on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 0, 150)); // Original purple
            }
        });
        return button;
    }

    private void openHallManagement() {
        new HallManagementGUI(hallManager, this); // Pass the dashboard reference
    }

    private void showAvailableHalls() {
        // Create a new JFrame for the available halls
        JFrame hallsFrame = new JFrame("Available Halls");
        hallsFrame.setSize(600, 400);
        hallsFrame.setLocationRelativeTo(null); // Center the window

        // Create a main panel with a border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel headerLabel = new JLabel("Available Halls", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28)); // Larger font size
        headerLabel.setForeground(new Color(128, 0, 128));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Create a panel for the hall information
        JPanel hallsPanel = new JPanel();
        hallsPanel.setLayout(new BoxLayout(hallsPanel, BoxLayout.Y_AXIS)); // Vertical layout

        try (BufferedReader br = new BufferedReader(new FileReader("databases/crm.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Assuming CSV format: ID, Name, Date, Time
                if (parts.length == 4) {
                    JPanel hallCard = new JPanel();
                    hallCard.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                    hallCard.setBackground(Color.WHITE);
                    hallCard.setLayout(new GridLayout(1, 5)); // 1 row, 5 columns

                    // Create labels with smaller font
                    JLabel idLabel = new JLabel(parts[0].trim ());
                    idLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font
                    hallCard.add(idLabel);

                    JLabel nameLabel = new JLabel(parts[1].trim ());
                    nameLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font
                    hallCard.add(nameLabel);

                    JLabel dateLabel = new JLabel(parts[2].trim ());
                    dateLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font
                    hallCard.add(dateLabel);

                    JLabel timeLabel = new JLabel(parts[3].trim ());
                    timeLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font
                    hallCard.add(timeLabel);

                    // Booking button
                    JButton bookButton = new JButton("Book");
                    bookButton.addActionListener(_ -> bookHall(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim()));
                    hallCard.add(bookButton);

                    hallsPanel.add(hallCard);
                    hallsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between cards
                }
            }
        } catch (IOException e) {
            hallsPanel.add(new JLabel("Error reading file: " + e.getMessage()));
        }

        // Add the halls panel to the main panel
        mainPanel.add(hallsPanel, BorderLayout.CENTER);

        // Add an OK button at the bottom
        JButton okButton = new JButton("Dashboard");
        okButton.addActionListener(_ -> hallsFrame.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set up the frame
        hallsFrame.add(mainPanel);
        hallsFrame.setVisible(true);
    }

    private void bookHall(String id, String name, String date, String time) {
        String bookingDetails = "ID: " + id + ", Name: " + name + ", Date: " + date + ", Time: " + time + "\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("databases/Booking.txt", true))) {
            writer.write(bookingDetails);
            JOptionPane.showMessageDialog(this, "Hall booked successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving booking: " + e.getMessage());
        }
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, "You have logged out successfully.");
        dispose(); // Close the dashboard
    }

    public static void main(String[] args) {
        HallManager hallManager = new HallManager(); // Create HallManager instance
        new SchedulerDashboard(hallManager); // Launch the dashboard
    }
}