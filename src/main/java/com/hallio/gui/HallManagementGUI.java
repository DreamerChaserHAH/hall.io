package com.hallio.gui;

import com.hallio.dms.DatabaseManager;
import com.hallio.hms.Hall;
import com.hallio.hms.HallManager;
import com.hallio.hms.schedule.Schedule;
import com.hallio.hms.schedule.ScheduleManager;
import com.hallio.hms.schedule.ScheduleType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HallManagementGUI extends JFrame {
    private SchedulerDashboard dashboard; // Reference to the Dashboard
    private JTextField idField; // Hall ID field
    private JTextField nameField;
    private JTextField rateField;
    private JTextField seatsField;
    private JList<Hall> hallList;
    private DefaultListModel<Hall> hallListModel;

    // Schedule fields
    private JTextField periodTitleField;
    private JTextField hallIdField; // Hall ID input as JTextField
    private JTextField dateField;
    private JComboBox<String> typeComboBox;
    private JSpinner startTimeSpinner;
    private JSpinner endTimeSpinner;
    private JTable scheduleTable;
    private DefaultTableModel scheduleTableModel;

    public HallManagementGUI(HallManager hallManager, SchedulerDashboard dashboard) {
        //this.hallManager = hallManager;
        this.dashboard = dashboard; // Store the dashboard reference
        createAndShowGUI();
    }

    public HallManagementGUI(HallManager hallManager) {
    }

    private void createAndShowGUI() {
        setTitle("Hall Booking Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window

        // Create a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(128, 0, 128)); // Purple background for tabs

        // Create the Hall Management panel
        JPanel hallPanel = createHallManagementPanel();
        tabbedPane.addTab("Manage Halls", hallPanel);

        // Create the Schedule Management panel
        JPanel schedulePanel = createScheduleManagementPanel();
        tabbedPane.addTab("Manage Schedule", schedulePanel);

        // Add a back button to return to the dashboard
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> goBackToDashboard());
        backButton.setBackground(new Color(75, 0, 130)); // Darker purple
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        backButton.setPreferredSize(new Dimension(150, 30)); // Set button size

        // Position the back button in the corner
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.NORTH);

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createHallManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchHallById(searchField.getText()));
        searchPanel.add(new JLabel("Search by Hall ID:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        panel.add(searchPanel, BorderLayout.NORTH); // Add search panel to the top

        // Hall Details Section
        JPanel hallDetailsPanel = createSectionPanel("Hall Details");
        addLabelAndField(hallDetailsPanel, "Hall ID:", idField = new JTextField(10)); // Hall ID field
        addLabelAndField(hallDetailsPanel, "Name:", nameField = new JTextField(20));
        addLabelAndField(hallDetailsPanel, "Hourly Rate (RM):", rateField = new JTextField(10));
        addLabelAndField(hallDetailsPanel, "Total Seats:", seatsField = new JTextField(10));

        panel.add(hallDetailsPanel, BorderLayout.WEST);

        // Hall List
        hallListModel = new DefaultListModel<>();
        hallList = new JList<>(hallListModel);
        hallList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(hallList);
        listScrollPane.setBorder(BorderFactory.createTitledBorder("Hall List"));

        panel.add(listScrollPane, BorderLayout.CENTER);

        // Action Buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionPanel.add(createButton("Create", e -> createHall()));
        actionPanel.add(createButton("Edit", e -> editHall()));
        actionPanel.add(createButton("Delete", e -> deleteHall()));

        panel.add(actionPanel, BorderLayout.SOUTH);

        // Load existing halls into the list
        loadHallsFromFile();

        return panel;
    }

    private JPanel createScheduleManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Schedule Details Section
        JPanel scheduleDetailsPanel = createSectionPanel("Manage Schedule");
        addLabelAndField(scheduleDetailsPanel, "Period Title:", periodTitleField = new JTextField(20));

        // Hall ID TextField
        hallIdField = new JTextField(10); // Change to JTextField
        addLabelAndField(scheduleDetailsPanel, "Hall ID:", hallIdField);

        // Date Field
        dateField = new JTextField("Sep 2, 2024");
        addLabelAndField(scheduleDetailsPanel, "Date:", dateField);

        // Type ComboBox
        typeComboBox = new JComboBox<>(new String[]{ "AVAILABLE", "MAINTENANCE"});
        addLabelAndField(scheduleDetailsPanel, "Type:", typeComboBox);

        // Start Time Spinner
        startTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
        startTimeSpinner.setEditor(startEditor);
        addLabelAndField(scheduleDetailsPanel, "Start Time:", startTimeSpinner);

        // End Time Spinner
        endTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
        endTimeSpinner.setEditor(endEditor);
        addLabelAndField(scheduleDetailsPanel, "End Time:", endTimeSpinner);

        panel.add(scheduleDetailsPanel, BorderLayout.WEST);

        // Action Buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionPanel.add(createButton("Create", e -> createSchedule())); // Pass hallIdField
        actionPanel.add(createButton("Edit", e -> editSchedule()));
        actionPanel.add(createButton("Delete", e -> deleteSchedule()));

        panel.add(actionPanel, BorderLayout.SOUTH);

        // Schedule Table
        String[] columnNames = {"Title", "Hall ID", "Start Time", "End Time", "Type"};

        Schedule[] schedules = ScheduleManager.loadSchedules();

        scheduleTableModel = new DefaultTableModel(columnNames, 0);
        for (Schedule schedule : schedules) {
            scheduleTableModel.addRow(new Object[]{"UNKNOWN", schedule.getHallId(), schedule.getStartTime(), schedule.getEndTime(), schedule.getScheduleType()});
        }

        scheduleTable = new JTable(scheduleTableModel);
        JScrollPane tableScrollPane = new JScrollPane(scheduleTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Scheduled Events"));

        panel.add(tableScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void createHall() {
        try {
            int id = Integer.parseInt(idField.getText()); // Get Hall ID
            String name = nameField.getText();
            double hourlyRate = Double.parseDouble(rateField.getText());
            int totalSeats = Integer.parseInt(seatsField.getText());

            // Check if the hall ID already exists in the manager
            if (HallManager.hallExists(id)) {
                JOptionPane.showMessageDialog(null, "Hall ID " + id + " already exists.");
                return;
            }

            Hall newHall = new Hall(id, name, "Location", hourlyRate, totalSeats);
            HallManager.addHall(newHall);
            hallListModel.addElement(newHall); // Add to the list model

            loadHallsFromFile(); // Refresh hall list
            JOptionPane.showMessageDialog(null, "Hall created successfully!");
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid numbers for ID, Rate, and Seats.");
        }
    }

    private void editHall() {
        Hall selectedHall = hallList.getSelectedValue();
        if (selectedHall != null) {
            // Show dialog to edit hall details
            // Implement your editing logic here
        } else {
            JOptionPane.showMessageDialog(null, "Please select a hall to edit.");
        }
    }

    private void deleteHall() {
        Hall selectedHall = hallList.getSelectedValue();
        if (selectedHall != null) {
            HallManager.deleteHall(selectedHall.id);
            loadHallsFromFile(); // Refresh hall list
            JOptionPane.showMessageDialog(null, "Hall deleted successfully!");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a hall to delete.");
        }
    }

    private void createSchedule() {
        String title = periodTitleField.getText();
        String hallIdStr = hallIdField.getText(); // Get Hall ID from the text field
        String date = dateField.getText();
        String type = (String) typeComboBox.getSelectedItem();

        // Check if the Hall ID exists
        int hallId;
        try {
            hallId = Integer.parseInt(hallIdStr);
            if (!HallManager.hallExists(hallId)) {
                JOptionPane.showMessageDialog(null, "Hall ID " + hallId + " does not exist.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid Hall ID.");
            return;
        }

        // Format the date and time correctly
        String startTimeString = date + " " + new SimpleDateFormat("HH:mm").format(startTimeSpinner.getValue());
        String endTimeString = date + " " + new SimpleDateFormat("HH:mm").format(endTimeSpinner.getValue());

        try {
            // Parse start and end times
            LocalDateTime startTime = LocalDateTime.parse(startTimeString, DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm", Locale.ENGLISH));
            LocalDateTime endTime = LocalDateTime.parse(endTimeString, DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm", Locale.ENGLISH));

            // Add schedule to the table
            scheduleTableModel.addRow(new Object[]{title, hallId, startTime, endTime, type});
            ScheduleManager.addSchedule(new Schedule(DatabaseManager.getNext("schedule"), hallId, 0, new Date(startTime.toEpochSecond(ZoneOffset.UTC)), new Date(endTime.toEpochSecond(ZoneOffset.UTC)), ScheduleType.valueOf(type)));
            saveSchedulesToFile(); // Save schedules after creating
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid date and time format.");
        }
    }

    private void saveSchedulesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter ("databases/crmsg.txt"))) {
            for (int i = 0; i < scheduleTableModel.getRowCount(); i++) {
                String title = (String) scheduleTableModel.getValueAt(i, 0);
                int hallId = (int) scheduleTableModel.getValueAt(i, 1);
                LocalDateTime startTime = (LocalDateTime) scheduleTableModel.getValueAt(i, 2);
                LocalDateTime endTime = (LocalDateTime) scheduleTableModel.getValueAt(i, 3);
                String type = (String) scheduleTableModel.getValueAt(i, 4);

                // Write to file in the format: Title,HallID,StartTime,EndTime,Type
                writer.write(title + "," + hallId + "," + startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "," + endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "," + type);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Schedules saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving schedules: " + e.getMessage());
        }
    }

    private void editSchedule() {
        int selectedRow = scheduleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a schedule to edit.");
            return;
        }

        // Retrieve current values from the selected row
        String currentTitle = (String) scheduleTableModel.getValueAt(selectedRow, 0);
        int currentHallId = (int) scheduleTableModel.getValueAt(selectedRow, 1);
        LocalDateTime currentStartTime = (LocalDateTime) scheduleTableModel.getValueAt(selectedRow, 2);
        LocalDateTime currentEndTime = (LocalDateTime) scheduleTableModel.getValueAt(selectedRow, 3);
        String currentType = (String) scheduleTableModel.getValueAt(selectedRow, 4);

        // Create input fields for editing
        JTextField titleField = new JTextField(currentTitle);
        JTextField hallIdField = new JTextField(String.valueOf(currentHallId));
        JTextField startTimeField = new JTextField(currentStartTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        JTextField endTimeField = new JTextField(currentEndTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"AVAILABLE", "MAINTENANCE"});
        typeComboBox.setSelectedItem(currentType);

        // Show dialog for editing
        Object[] message = {
                "Title:", titleField,
                "Hall ID:", hallIdField,
                "Start Time (yyyy-MM-dd HH:mm):", startTimeField,
                "End Time (yyyy-MM-dd HH:mm):", endTimeField,
                "Type:", typeComboBox
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Edit Schedule", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Validate Hall ID
            int hallId;
            try {
                hallId = Integer.parseInt(hallIdField.getText());
                if (!HallManager.hallExists(hallId)) {
                    JOptionPane.showMessageDialog(null, "Hall ID " + hallId + " does not exist.");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid Hall ID.");
                return;
            }

            // Update the schedule in the table
            scheduleTableModel.setValueAt(titleField.getText(), selectedRow, 0);
            scheduleTableModel.setValueAt(hallId, selectedRow, 1);
            scheduleTableModel.setValueAt(LocalDateTime.parse(startTimeField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), selectedRow, 2);
            scheduleTableModel.setValueAt(LocalDateTime.parse(endTimeField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), selectedRow, 3);
            scheduleTableModel.setValueAt(typeComboBox.getSelectedItem(), selectedRow, 4);

            saveSchedulesToFile(); // Save schedules after editing
            JOptionPane.showMessageDialog(null, "Schedule updated successfully!");
        }
    }

    private void deleteSchedule() {
        int selectedRow = scheduleTable.getSelectedRow();
        if (selectedRow != -1) {
            scheduleTableModel.removeRow(selectedRow);
            saveSchedulesToFile(); // Save schedules after deletion
        }
    }

    private void loadHallsFromFile() {
        hallListModel.clear(); // Clear the existing list
        List<Hall> halls = HallManager.loadHalls(); // Load halls from the manager
        for (Hall hall : halls) {
            hallListModel.addElement(hall); // Add each hall to the list model
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        rateField.setText("");
        seatsField.setText("");
        periodTitleField.setText("");
        hallIdField.setText(""); // Clear Hall ID field
        dateField.setText("Sep 2, 2024");
        startTimeSpinner.setValue(new java.util.Date());
        endTimeSpinner.setValue(new java.util.Date());
    }

    private void goBackToDashboard() {
        dashboard.setVisible(true); // Show the dashboard
        dispose(); // Close the hall management window
    }

    public static void main(String[] args) {
        HallManager hallManager = new HallManager();
        SchedulerDashboard dashboard = new SchedulerDashboard(hallManager); // Create Dashboard instance
        new HallManagementGUI(hallManager, dashboard); // Pass dashboard to HallManagementGUI
    }

    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setBackground(Color.WHITE); // Set background color for section
        return panel;
    }

    private void addLabelAndField(JPanel panel, String labelText, JComponent field) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 2, 2, 2);

        gbc.gridx = 0;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setBackground(new Color(128, 0, 128)); // Purple button background
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY)); // Dark border
        button.setPreferredSize(new Dimension(100, 30)); // Set button size

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(75, 0, 130)); // Darker purple on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(128, 0, 128)); // Original purple
            }
        });

        return button;
    }

    private void searchHallById(String hallIdStr) {
        try {
            int hallId = Integer.parseInt(hallIdStr);
            Hall hall = HallManager.getHallById(hallId);
            if (hall != null) {
                JOptionPane.showMessageDialog(null, "Hall found: " + hall);
            } else {
                JOptionPane.showMessageDialog(null, "Hall ID " + hallId + " not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid Hall ID.");
        }
    }

    private void showAvailableHalls() {
        StringBuilder availableHalls = new StringBuilder("<html><body style='font-family: Arial;'>");
        availableHalls.append("<h2>Available Halls:</h2>");
        try (BufferedReader br = new BufferedReader(new FileReader("databases/crm.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Assuming CSV format: ID, Name, Date, Time
                if (parts.length == 4) {
                    availableHalls.append("<div style='color: blue;'>")
                            .append("ID: ").append(parts[0].trim())
                            .append(", Name: ").append(parts[1].trim())
                            .append(", Date: ").append(parts[2].trim())
                            .append(", Time: ").append(parts[3].trim())
                            .append("</div>");
                }
            }
        } catch (IOException e) {
            availableHalls.append("<div style='color: red;'>Error reading file: ").append(e.getMessage()).append("</div>");
        }
        availableHalls.append("</body></html>");

        JOptionPane.showMessageDialog(this, availableHalls.toString(), "Available Halls", JOptionPane.INFORMATION_MESSAGE);
    }
}