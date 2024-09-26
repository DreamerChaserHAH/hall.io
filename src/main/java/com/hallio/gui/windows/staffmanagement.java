package com.hallio.gui.windows;

import com.hallio.gui.windows.registerform;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class staffmanagement extends javax.swing.JFrame {

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton btnaddstaff;

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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnaddstaff = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Username", "First Name", "Last Name", "Email", "Contact Number", "Role", "Registered Date", "Last Active"
                }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnaddstaff.setText("Add Staff");
        btnaddstaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddstaffActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 994, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnaddstaff))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(32, Short.MAX_VALUE)
                                .addComponent(btnaddstaff)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
    }

    private void populateTable() {
        List<String[]> users = UserData.readUsersFromFile("users.txt");
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (String[] user : users) {
            model.addRow(user);
        }
    }

    private void btnaddstaffActionPerformed(java.awt.event.ActionEvent evt) {
        // return staff role
        registerform.main(new String[]{"staff"});
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new staffmanagement().setVisible(true);
            }
        });
    }
}