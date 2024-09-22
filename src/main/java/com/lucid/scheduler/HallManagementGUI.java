package com.lucid.scheduler;
import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.util.List;
import java.time.LocalDateTime;
import java.io.*;
import java.util.ArrayList;

public class HallManagementGUI {
   private HallManager hallManager;
    private JTextField nameField;
    private JTextField rateField;
    private JTextField seatsField;
    private JTextField availabilityStartField; // Moved outside
    private JTextField availabilityEndField; // Moved outside
    private JTextField availabilityRemarksField; // Moved outside
    private JTextField maintenanceStartField; // Moved outside
    private JTextField maintenanceEndField; // Moved outside
    private JTextField maintenanceRemarksField; // Moved outside
    private JList<Hall> hallList;
    private DefaultListModel<Hall> hallListModel;

    public HallManagementGUI(HallManager hallManager) {
        this.hallManager = hallManager;
        createAndShowGUI();
    }

    public HallManagementGUI(JTextField availabilityEndField, JTextField availabilityRemarksField, JTextField availabilityStartField, JList<Hall> hallList, DefaultListModel<Hall> hallListModel, HallManager hallManager, JTextField maintenanceEndField, JTextField maintenanceRemarksField, JTextField maintenanceStartField, JTextField nameField, JTextField rateField, JTextField seatsField) {
        this.availabilityEndField = availabilityEndField;
        this.availabilityRemarksField = availabilityRemarksField;
        this.availabilityStartField = availabilityStartField;
        this.hallList = hallList;
        this.hallListModel = hallListModel;
        this.hallManager = hallManager;
        this.maintenanceEndField = maintenanceEndField;
        this.maintenanceRemarksField = maintenanceRemarksField;
        this.maintenanceStartField = maintenanceStartField;
        this.nameField = nameField;
        this.rateField = rateField;
        this.seatsField = seatsField;
    }

    public HallManagementGUI(JTextField availabilityStartField) {
        this.availabilityStartField = availabilityStartField;
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Hall Booking Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Panel for managing halls
        JPanel managePanel = new JPanel();
        managePanel.setLayout(new GridLayout(10, 2)); // Adjusted row count

        managePanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        managePanel.add(nameField);

        managePanel.add(new JLabel("Hourly Rate (RM):"));
        rateField = new JTextField();
        managePanel.add(rateField);

        managePanel.add(new JLabel("Total Seats:"));
        seatsField = new JTextField();
        managePanel.add(seatsField);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> createHall());
        managePanel.add(createButton);

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> editHall());
        managePanel.add(editButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteHall());
        managePanel.add(deleteButton);

        // Add fields for availability and maintenance
        managePanel.add(new JLabel("Availability Start:"));
        availabilityStartField = new JTextField();
        managePanel.add(availabilityStartField);

        managePanel.add(new JLabel("Availability End:"));
        availabilityEndField = new JTextField();
        managePanel.add(availabilityEndField);

        managePanel.add(new JLabel("Availability Remarks:"));
        availabilityRemarksField = new JTextField();
        managePanel.add(availabilityRemarksField);

        JButton setAvailabilityButton = new JButton("Set Availability");
        setAvailabilityButton.addActionListener(e -> setAvailability());
        managePanel.add(setAvailabilityButton);

        managePanel.add(new JLabel("Maintenance Start:"));
        maintenanceStartField = new JTextField();
        managePanel.add(maintenanceStartField);

        managePanel.add(new JLabel("Maintenance End:"));
        maintenanceEndField = new JTextField();
        managePanel.add(maintenanceEndField);

        managePanel.add(new JLabel("Maintenance Remarks:"));
        maintenanceRemarksField = new JTextField();
        managePanel.add(maintenanceRemarksField);

        JButton setMaintenanceButton = new JButton("Set Maintenance");
        setMaintenanceButton.addActionListener(e -> setMaintenance());
        managePanel.add(setMaintenanceButton);

        frame.add(managePanel, BorderLayout.NORTH);

        // List to display halls
        hallListModel = new DefaultListModel<>();
        hallList = new JList<>(hallListModel);
        hallList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(hallList);
        frame.add(listScrollPane, BorderLayout.CENTER);

        // Load existing halls into the list
        loadHallsFromFile();

        frame.setVisible(true);
    }

    private void loadHallsFromFile() {
        List<Hall> halls = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/textfiles/detailHalls.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                String hallType = details[0];
                int totalSeats = Integer.parseInt(details[1]);
                double hourlyRate = Double.parseDouble(details[2]);
                LocalDateTime start = LocalDateTime.parse(details[3] + "T00:00");
                LocalDateTime end = LocalDateTime.parse(details[4] + "T00:00");
                String remarks = details[5];

                Hall hall = new Hall(hallType, hourlyRate, totalSeats); // Assuming constructor exists
                hall.addAvailabilityPeriod(start, end, remarks); // Assuming this method exists
                halls.add(hall);
            }
        } catch (IOException e) {
        }
        hallListModel.clear();
        for (Hall hall : halls) {
            hallListModel.addElement(hall);
        }
    }

    private void loadHalls() {
        hallListModel.clear();
        List<Hall> halls = hallManager.getHalls();
        for (Hall hall : halls) {
            hallListModel.addElement(hall);
        }
    }

    private void createHall() {
        String name = nameField.getText();
        double rate = Double.parseDouble(rateField.getText());
        int seats = Integer.parseInt(seatsField.getText());
        Hall newHall = new Hall(name, rate, seats); // Assuming Hall has a constructor for these parameters
        hallManager.addHall(newHall);
        loadHalls();
        clearFields();
    }

    private void editHall() {
        Hall selectedHall = hallList.getSelectedValue();
        if (selectedHall != null) {
            selectedHall.setHallType(nameField.getText());
            selectedHall.setHourlyRate(Double.parseDouble(rateField.getText()));
            selectedHall.setTotalSeats(Integer.parseInt(seatsField.getText()));
            hallManager.updateHall(selectedHall); // Assuming updateHall method exists
            loadHalls();
            clearFields();
        }
    }

    private void deleteHall() {
        Hall selectedHall = hallList.getSelectedValue();
        if (selectedHall != null) {
            hallManager.deleteHall(selectedHall.getId()); // Pass the ID directly without conversion
            loadHalls();
            clearFields();
        }
    }

    private void clearFields() {
        nameField.setText("");
        rateField.setText("");
        seatsField.setText("");
    }

    private void setAvailability() {
        Hall selectedHall = hallList.getSelectedValue();
        if (selectedHall != null) {
            LocalDateTime start = LocalDateTime.parse(availabilityStartField.getText());
            LocalDateTime end = LocalDateTime.parse(availabilityEndField.getText());
            String remarks = availabilityRemarksField.getText();
            hallManager.setAvailability(selectedHall.getId(), start, end, remarks);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a hall to set availability.");
        }
    }

    private void setMaintenance() {
        Hall selectedHall = hallList.getSelectedValue();
        if (selectedHall != null) {
            LocalDateTime start = LocalDateTime.parse(maintenanceStartField.getText());
            LocalDateTime end = LocalDateTime.parse(maintenanceEndField.getText());
            String remarks = maintenanceRemarksField.getText();
            hallManager.setMaintenance(String.valueOf(selectedHall.getId()), start, end, remarks);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a hall to set maintenance.");
        }
    }

    public static void main(String[] args) {
        HallManager hallManager = new HallManager(); // Initialize HallManager
        new HallManagementGUI(hallManager); // Create and show the GUI
    }
}