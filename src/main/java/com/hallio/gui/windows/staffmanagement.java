package com.hallio.gui.windows;

import com.hallio.gui.windows.registerform;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class staffmanagement extends javax.swing.JFrame {

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton btnaddstaff;
    private javax.swing.JLabel lblTitle;

    public staffmanagement() {
        initComponents();
        populateTable();
    }

    public static class UserData {
        public static List<String[]> readUsersFromFile(String filePath) {
            List<String[]> users = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // Assuming each line is comma-separated
                    String[] userData = line.split(",");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnaddstaff = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitle.setFont(new java.awt.Font("Tahoma", Font.BOLD, 18)); // Set font size and style
        lblTitle.setText("Staff Management");

        // Update table model to include "Edit" and "Delete" columns
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "ID", "Username", "Password", "Role", "First Name", "Last Name", "Phone Number", "Email", "Registered On", "Last Active", "Edit", "Delete"
                }
        ));
        jScrollPane1.setViewportView(jTable1);

        // Set up the table to use buttons in the "Edit" and "Delete" columns
        jTable1.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        jTable1.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox()));
        jTable1.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        jTable1.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox()));

        btnaddstaff.setText("Add Staff");
        btnaddstaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddstaffActionPerformed(evt);
            }
        });

        // Layout adjustments
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTitle, javax.swing.GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnaddstaff)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 994, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnaddstaff)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap()
        );

        pack();
    }

    private void populateTable() {
        List<String[]> users = UserData.readUsersFromFile("databases\\users.txt");
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (String[] user : users) {
            // Add placeholders for "Edit" and "Delete" buttons
            Object[] rowData = new Object[user.length + 2]; // +2 for Edit and Delete buttons
            System.arraycopy(user, 0, rowData, 0, user.length);
            rowData[rowData.length - 2] = "Edit";
            rowData[rowData.length - 1] = "Delete";
            model.addRow(rowData);
        }
    }

    private void btnaddstaffActionPerformed(java.awt.event.ActionEvent evt) {
        // return staff role
        registerform.main(new String[]{"staff"});
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new staffmanagement().setVisible(true));
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
        private boolean isPushed;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);

            button.addActionListener((ActionEvent e) -> {
                fireEditingStopped();
                if (label.equals("Edit")) {
                    editAction(selectedRow);
                } else if (label.equals("Delete")) {
                    deleteAction(selectedRow);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            selectedRow = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }

        private void editAction(int row) {
            // Implement your edit functionality here
            // For example, open a new window with the user's data pre-filled
            JOptionPane.showMessageDialog(null, "Edit action on row " + row);
        }

        private void deleteAction(int row) {
            // Implement your delete functionality here
            // For example, remove the user from the data source and refresh the table
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", "Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Remove from table
                ((DefaultTableModel) jTable1.getModel()).removeRow(row);
                // Also remove the user from the data source (e.g., file or database)
                // For example, call a method to update the users.txt file
                removeUserFromFile(row);
                // Refresh the table if necessary
            }
        }

        private void removeUserFromFile(int row) {
            // Implement logic to remove the user from users.txt
            // This method should read the file, remove the specified line, and write back to the file
        }
    }
}
