package com.lucid.gui;

// ChatGPT GUI Please dont Refer
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



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
                if (com.lucid.admin.authentication.authenticate(username, password)) {
                    frame.dispose();
                    String roleuser = com.lucid.admin.authentication.getRole(username); // Get role of user
                    if (roleuser != null) {
                        if (roleuser.equals("superuser")) {
                            superadmin superadmin = new superadmin();
                            superadmin.setVisible(true);
                        } else if (roleuser.equals("admin")) {
                            administrator admin = new administrator();
                            admin.setVisible(true);
                        } else if (roleuser.equals("user")) {
                            System.out.println("User");
                        }
                    }
                    else {
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