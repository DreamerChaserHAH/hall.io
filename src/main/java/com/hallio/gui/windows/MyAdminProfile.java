package com.hallio.gui.windows;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyAdminProfile extends JDialog {

    private JTextField txtID;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtRole;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtPhoneNumber;
    private JTextField txtEmail;
    private JTextField txtRegisteredOn;
    private JTextField txtLastActive;
    private JButton btnSave;
    private JButton btnCancel;

    private String userId;

    public MyAdminProfile(Frame parent, String userId) {
        super(parent, "My Profile", true);
        this.userId = userId;
        initComponents();
        loadUserData();
    }

    private void initComponents() {
        // Create labels and text fields
        JLabel lblID = new JLabel("ID:");
        txtID = new JTextField(20);
        txtID.setEditable(false);

        JLabel lblUsername = new JLabel("Username:");
        txtUsername = new JTextField(20);

        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(20);

        JLabel lblRole = new JLabel("Role:");
        txtRole = new JTextField(20);
        txtRole.setEditable(false);

        JLabel lblFirstName = new JLabel("First Name:");
        txtFirstName = new JTextField(20);

        JLabel lblLastName = new JLabel("Last Name:");
        txtLastName = new JTextField(20);

        JLabel lblPhoneNumber = new JLabel("Phone Number:");
        txtPhoneNumber = new JTextField(20);

        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField(20);

        JLabel lblRegisteredOn = new JLabel("Registered On:");
        txtRegisteredOn = new JTextField(20);
        txtRegisteredOn.setEditable(false);

        JLabel lblLastActive = new JLabel("Last Active:");
        txtLastActive = new JTextField(20);
        txtLastActive.setEditable(false);

        // Create buttons
        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");

        // Add action listeners
        btnSave.addActionListener(e -> saveUserData());
        btnCancel.addActionListener(e -> dispose());

        // Arrange components in the dialog
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        // Row 0 - ID
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblID, gbc);
        gbc.gridx = 1;
        panel.add(txtID, gbc);

        // Row 1 - Username
        gbc.gridx = 0; gbc.gridy++;
        panel.add(lblUsername, gbc);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        // Row 2 - Password
        gbc.gridx = 0; gbc.gridy++;
        panel.add(lblPassword, gbc);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        // Row 3 - Role
        gbc.gridx = 0; gbc.gridy++;
        panel.add(lblRole, gbc);
        gbc.gridx = 1;
        panel.add(txtRole, gbc);

        // Row 4 - First Name
        gbc.gridx = 0; gbc.gridy++;
        panel.add(lblFirstName, gbc);
        gbc.gridx = 1;
        panel.add(txtFirstName, gbc);

        // Row 5 - Last Name
        gbc.gridx = 0; gbc.gridy++;
        panel.add(lblLastName, gbc);
        gbc.gridx = 1;
        panel.add(txtLastName, gbc);

        // Row 6 - Phone Number
        gbc.gridx = 0; gbc.gridy++;
        panel.add(lblPhoneNumber, gbc);
        gbc.gridx = 1;
        panel.add(txtPhoneNumber, gbc);

        // Row 7 - Email
        gbc.gridx = 0; gbc.gridy++;
        panel.add(lblEmail, gbc);
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);

        // Row 8 - Registered On
        gbc.gridx = 0; gbc.gridy++;
        panel.add(lblRegisteredOn, gbc);
        gbc.gridx = 1;
        panel.add(txtRegisteredOn, gbc);

        // Row 9 - Last Active
        gbc.gridx = 0; gbc.gridy++;
        panel.add(lblLastActive, gbc);
        gbc.gridx = 1;
        panel.add(txtLastActive, gbc);

        // Row 10 - Buttons
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnPanel, gbc);

        add(panel);
        pack();
        setLocationRelativeTo(getParent());
    }

    private void loadUserData() {
        // Load the user's data from users.txt
        List<String[]> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("databases\\users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[0].equals(userId)) {
                    // Populate fields
                    txtID.setText(userData[0]);
                    txtUsername.setText(userData[1]);
                    txtPassword.setText(userData[2]);
                    txtRole.setText(userData[3]);
                    txtFirstName.setText(userData[4]);
                    txtLastName.setText(userData[5]);
                    txtPhoneNumber.setText(userData[6]);
                    txtEmail.setText(userData[7]);
                    txtRegisteredOn.setText(userData[8]);
                    txtLastActive.setText(userData[9]);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading user data", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void saveUserData() {
        // Validate and save data
        String newUsername = txtUsername.getText().trim();
        String newPassword = new String(txtPassword.getPassword()).trim();
        String newFirstName = txtFirstName.getText().trim();
        String newLastName = txtLastName.getText().trim();
        String newPhoneNumber = txtPhoneNumber.getText().trim();
        String newEmail = txtEmail.getText().trim();

        // Basic validation
        if (newUsername.isEmpty() || newPassword.isEmpty() || newFirstName.isEmpty() || newLastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update the user's data in the users.txt file
        List<String> lines = new ArrayList<>();
        boolean userFound = false;
        try (BufferedReader br = new BufferedReader(new FileReader("databases\\users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[0].equals(userId)) {
                    // Update this user's data
                    userData[1] = newUsername;
                    userData[2] = newPassword;
                    userData[4] = newFirstName;
                    userData[5] = newLastName;
                    userData[6] = newPhoneNumber;
                    userData[7] = newEmail;
                    line = String.join(",", userData);
                    userFound = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading users file", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!userFound) {
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Write back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("databases\\users.txt"))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error writing users file", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Notify the user and close the dialog
        JOptionPane.showMessageDialog(this, "Profile updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}