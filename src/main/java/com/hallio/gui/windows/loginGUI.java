package com.hallio.gui.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.hallio.admin.AuthenticationServices;
import com.hallio.gui.SchedulerDashboard;
import com.hallio.gui.dashboard.CustomerDashboard;
import com.hallio.gui.dashboard.ManagerDashboard;

public class loginGUI {

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new GridLayout(3, 2));

        // Create the components
        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passText = new JPasswordField();
        JButton loginButton = new JButton("Login");

        // Add components to the frame
        frame.add(userLabel);
        frame.add(userText);
        frame.add(passLabel);
        frame.add(passText);
        frame.add(new JLabel()); // Empty cell
        frame.add(loginButton);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passText.getPassword());

                // Use the authentication class to validate login
                AuthenticationServices authService = new AuthenticationServices();
                if (authService.authenticate(username, password)) {
                    frame.dispose();
                    String roleuser = authService.getRole(username); // Get role of user
                    if (roleuser != null) {
                        switch(roleuser) {
                            case "superuser":
                                superadmin.main(args);
                                break;
                            case "manager":
                                ManagerDashboard.main(args);
                                break;
                            case "scheduler":
                                SchedulerDashboard.main(args);
                                break;
                            case "customer":
                                CustomerDashboard.main(args);
                                break;
                            case "admin":
                                admindashboard.main(args);
                                break;
                                default:
                                JOptionPane.showMessageDialog(frame, "Role not found.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Role not found on user.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.");
                }
            }
        });

        // Set the frame visibility
        frame.setVisible(true);
    }
}