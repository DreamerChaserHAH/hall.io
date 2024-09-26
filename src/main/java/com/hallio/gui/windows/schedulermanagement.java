package com.hallio.gui.windows;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class schedulermanagement extends JFrame {

    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JButton btnAddScheduler;
    private JLabel lblTitle;
    private JTextField txtFilter;
    private JButton btnFilter;

    public schedulermanagement() {
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
                    users.add(userData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return users;
        }
    }

    private void initComponents() {
        lblTitle = new JLabel();
        txtFilter = new JTextField(15);
        btnFilter = new JButton();
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        btnAddScheduler = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18)); // Set font size and style
        lblTitle.setText("Scheduler Management");

        // Update table model to include "Edit" and "Delete" columns
        jTable1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "ID", "Username", "Password", "Role", "First Name", "Last Name",
                        "Phone Number", "Email", "Registered On", "Last Active", "Edit", "Delete"
                }
        ));
        jScrollPane1.setViewportView(jTable1);

        // Set up the table to use buttons in the "Edit" and "Delete" columns
        jTable1.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        jTable1.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox()));
        jTable1.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        jTable1.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox()));

        btnAddScheduler.setText("Add Scheduler");
        btnAddScheduler.addActionListener(evt -> btnAddSchedulerActionPerformed(evt));

        btnFilter.setText("Filter");
        btnFilter.addActionListener(evt -> btnFilterActionPerformed(evt));

        // Layout adjustments
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        // Create a panel for the filter components
        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Filter by Username:"));
        filterPanel.add(txtFilter);
        filterPanel.add(btnFilter);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblTitle, GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnAddScheduler)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(filterPanel, GroupLayout.Alignment.CENTER)
                        .addComponent(jScrollPane1, GroupLayout.Alignment.CENTER, GroupLayout.PREFERRED_SIZE, 994, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitle)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddScheduler)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(filterPanel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 466, GroupLayout.PREFERRED_SIZE)
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
            if (user.length > 3 && user[3].equalsIgnoreCase("scheduler")) {
                String username = user[1];
                if (username.toLowerCase().contains(usernameFilter.toLowerCase())) {
                    // Add placeholders for "Edit" and "Delete" buttons
                    Object[] rowData = new Object[user.length + 2]; // +2 for Edit and Delete buttons
                    System.arraycopy(user, 0, rowData, 0, user.length);
                    rowData[rowData.length - 2] = "Edit";
                    rowData[rowData.length - 1] = "Delete";
                    model.addRow(rowData);
                }
            }
        }
    }

    private void btnAddSchedulerActionPerformed(ActionEvent evt) {
        // Open the register form with "scheduler" role
        registerform.main(new String[]{"scheduler"});
    }

    private void btnFilterActionPerformed(ActionEvent evt) {
        String filterText = txtFilter.getText();
        populateTable(filterText);
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(() -> new schedulermanagement().setVisible(true));
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
            String[] userData = new String[columnCount - 2]; // Exclude "Edit" and "Delete" columns

            for (int col = 0; col < columnCount - 2; col++) {
                userData[col] = (String) model.getValueAt(row, col);
            }

            // Open the EditUserForm, passing 'this' as the parent
            EditUserForm editForm = new EditUserForm(schedulermanagement.this, userData);
            editForm.setVisible(true);

            // After editing, refresh the table
            refreshTable();
        }

        private void deleteAction(int row) {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", "Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Get the user ID from the table
                String userId = (String) jTable1.getValueAt(row, 0); // Assuming ID is at index 0

                // Remove from table
                ((DefaultTableModel) jTable1.getModel()).removeRow(row);
                // Also remove the user from the data source (e.g., file or database)
                removeUserFromFile(userId);
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
