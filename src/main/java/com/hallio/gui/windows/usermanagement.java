package com.hallio.gui.windows;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class usermanagement extends javax.swing.JFrame {

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton btnAddStaff;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txtFilter;
    private javax.swing.JButton btnFilter;

    public usermanagement() {
        initComponents();
        populateTable("");
    }

    public static class UserData {
        public static List<String[]> readUsersFromFile(String filePath) {
            List<String[]> users = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // Assuming each line is comma-separated
                    String[] userData = line.split(",");
                    // Ensure the user data has the blocked status field
                    if (userData.length < 11) {
                        // Add "No" as default blocked status if missing
                        String[] extendedUserData = new String[11];
                        System.arraycopy(userData, 0, extendedUserData, 0, userData.length);
                        extendedUserData[10] = "No";
                        userData = extendedUserData;
                    }
                    users.add(userData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return users;
        }
    }

    private void initComponents() {
        lblTitle = new javax.swing.JLabel();
        txtFilter = new javax.swing.JTextField(15);
        btnFilter = new javax.swing.JButton("Filter");
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAddStaff = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitle.setFont(new java.awt.Font("Tahoma", Font.BOLD, 18)); // Set font size and style
        lblTitle.setText("User Management");

        // Update table model to include "Edit", "Delete", and "Block" columns
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "ID", "Username", "Password", "Role", "First Name", "Last Name",
                        "Phone Number", "Email", "Registered On", "Last Active", "Blocked", "Edit", "Delete", "Block"
                }
        ));
        jScrollPane1.setViewportView(jTable1);

        // Set up the table to use buttons in the "Edit", "Delete", and "Block" columns
        jTable1.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        jTable1.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox()));
        jTable1.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        jTable1.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox()));
        jTable1.getColumn("Block").setCellRenderer(new ButtonRenderer());
        jTable1.getColumn("Block").setCellEditor(new ButtonEditor(new JCheckBox()));

        btnAddStaff.setText("Add Staff");
        btnAddStaff.addActionListener(evt -> btnAddStaffActionPerformed(evt));

        btnFilter.addActionListener(evt -> btnFilterActionPerformed(evt));

        // Layout adjustments
        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Filter by Username:"));
        filterPanel.add(txtFilter);
        filterPanel.add(btnFilter);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        // Adjust layout to include filterPanel
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTitle, javax.swing.GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnAddStaff)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(filterPanel, javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.CENTER,
                                javax.swing.GroupLayout.PREFERRED_SIZE, 1100, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddStaff)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(filterPanel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 466,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap()
        );

        pack();
    }

    public void refreshTable() {
        populateTable(txtFilter.getText());
    }

    private void populateTable(String usernameFilter) {
        List<String[]> users = UserData.readUsersFromFile("databases\\users.txt");
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear existing data
        for (String[] user : users) {
            String username = user[1];
            if (username.toLowerCase().contains(usernameFilter.toLowerCase())) {
                // Add placeholders for "Edit", "Delete", and "Block" buttons
                Object[] rowData = new Object[user.length + 3]; // +3 for Edit, Delete, and Block buttons
                System.arraycopy(user, 0, rowData, 0, user.length);
                // Ensure the blocked status is displayed as "Yes" or "No"
                rowData[10] = user[10].equalsIgnoreCase("Yes") ? "Yes" : "No";
                rowData[rowData.length - 3] = "Edit";
                rowData[rowData.length - 2] = "Delete";
                rowData[rowData.length - 1] = user[10].equalsIgnoreCase("Yes") ? "Unblock" : "Block";
                model.addRow(rowData);
            }
        }
    }

    private void btnFilterActionPerformed(ActionEvent evt) {
        String filterText = txtFilter.getText();
        populateTable(filterText);
    }

    private void btnAddStaffActionPerformed(java.awt.event.ActionEvent evt) {
        // Open the register form with "staff" role
        registerform.main(new String[]{"staff"});
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new usermanagement().setVisible(true));
    }

    // Custom renderer and editor for the buttons in the table
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);

            button.addActionListener((ActionEvent e) -> {
                fireEditingStopped();
                if ("Edit".equals(label)) {
                    editAction(selectedRow);
                } else if ("Delete".equals(label)) {
                    deleteAction(selectedRow);
                } else if ("Block".equals(label) || "Unblock".equals(label)) {
                    blockAction(selectedRow);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            selectedRow = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }

        private void editAction(int row) {
            // Get the data from the selected row
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

            // Get all user data
            int columnCount = model.getColumnCount();
            String[] userData = new String[columnCount - 3]; // Exclude "Edit", "Delete", and "Block" columns

            for (int col = 0; col < columnCount - 3; col++) {
                userData[col] = (String) model.getValueAt(row, col);
            }

            // Open the EditUserForm
            EditUserForm editForm = new EditUserForm(usermanagement.this, userData);
            editForm.setVisible(true);

            // After editing, refresh the table
            refreshTable();
        }

        private void deleteAction(int row) {
            // Implement your delete functionality here
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete this user?", "Delete",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Get the user ID from the table
                String userId = (String) jTable1.getValueAt(row, 0); // Assuming ID is at index 0

                // Remove from table
                ((DefaultTableModel) jTable1.getModel()).removeRow(row);
                // Also remove the user from the data source (e.g., file or database)
                removeUserFromFile(userId);
            }
        }

        private void blockAction(int row) {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            String userId = (String) model.getValueAt(row, 0); // ID column
            String currentStatus = (String) model.getValueAt(row, 10); // Blocked status column

            String newStatus = currentStatus.equalsIgnoreCase("Yes") ? "No" : "Yes";

            // Update the blocked status in the table
            model.setValueAt(newStatus.equalsIgnoreCase("Yes") ? "Yes" : "No", row, 10);
            model.setValueAt(newStatus.equalsIgnoreCase("Yes") ? "Unblock" : "Block", row, model.getColumnCount() - 1);

            // Update the blocked status in the users.txt file
            updateBlockedStatusInFile(userId, newStatus);

            String action = newStatus.equalsIgnoreCase("Yes") ? "blocked" : "unblocked";
            JOptionPane.showMessageDialog(null, "User has been " + action + ".", "Block User", JOptionPane.INFORMATION_MESSAGE);
        }

        private void updateBlockedStatusInFile(String userId, String newStatus) {
            List<String> lines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader("databases\\users.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] userData = line.split(",");
                    if (userData[0].equals(userId)) {
                        // Ensure userData has enough elements
                        if (userData.length < 11) {
                            String[] extendedUserData = new String[11];
                            System.arraycopy(userData, 0, extendedUserData, 0, userData.length);
                            extendedUserData[10] = newStatus;
                            userData = extendedUserData;
                        } else {
                            userData[10] = newStatus;
                        }
                        line = String.join(",", userData);
                    }
                    lines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Write back to the file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("databases\\users.txt"))) {
                for (String l : lines) {
                    bw.write(l);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void removeUserFromFile(String userId) {
            // Implement logic to remove the user from users.txt
            List<String> lines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader("databases\\users.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < lines.size(); i++) {
                String[] userData = lines.get(i).split(",");
                if (userData[0].equals(userId)) {
                    lines.remove(i);
                    break;
                }
            }

            // Write back to the file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("databases\\users.txt"))) {
                for (String l : lines) {
                    bw.write(l);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
