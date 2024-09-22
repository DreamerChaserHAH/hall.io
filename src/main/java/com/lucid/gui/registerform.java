package com.lucid.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import com.lucid.admin.usermgment; 
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.formdev.flatlaf.FlatLightLaf;

/**
 *
 * @author yeohp
 */
public class registerform extends javax.swing.JFrame {

    public registerform(String userRole) {
        // Set FlatLaf Cupertino Light theme
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        initComponents(userRole);
        addPlaceholders();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents(String userRole) {

        signuplabel = new javax.swing.JLabel();
        userLoginName = new javax.swing.JTextField();
        userFirstname = new javax.swing.JTextField();
        userLastName = new javax.swing.JTextField();
        userEmail = new javax.swing.JTextField();
        userContactNum = new javax.swing.JTextField();
        userSignupbtn = new javax.swing.JButton();
        userpassword = new javax.swing.JPasswordField();
        userpasswordconfirm = new javax.swing.JPasswordField();

        // Initialize the dropdown without "Admin"
        userRoleDropdown = new JComboBox<>(new String[] {"Scheduler", "Customer", "Manager"});

        // Conditionally add "Admin" if the user role is "superuser"
        if ("superuser".equals(userRole)) {
            userRoleDropdown.addItem("Admin");
        }

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        signuplabel.setFont(new java.awt.Font("Calibri", 0, 36)); // NOI18N
        signuplabel.setText("SIGN UP");

        userLoginName.setText("Username");

        userFirstname.setText("First Name");

        userLastName.setText("Last Name");

        userEmail.setText("Email");

        userContactNum.setText("Contact Number");

        userSignupbtn.setText("Sign Up");

        userpassword.setText("Password");

        userpasswordconfirm.setText("Confirm Password");

        // Add action listener to the sign-up button
        userSignupbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userSignupbtnActionPerformed(e);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(signuplabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(userSignupbtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(userLoginName)
                            .addComponent(userFirstname)
                            .addComponent(userLastName)
                            .addComponent(userEmail)
                            .addComponent(userContactNum, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(userpassword)
                            .addComponent(userpasswordconfirm)
                            .addComponent(userRoleDropdown, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(signuplabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userLoginName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(userFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(userLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(userEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(userContactNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(userpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(userpasswordconfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(userRoleDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(userSignupbtn)
                .addGap(75, 75, 75))
        );

        pack();
    }// </editor-fold>                        

    // Adding focus listeners for placeholder functionality
    private void addPlaceholders() {
        addPlaceholder(userLoginName, "Username");
        addPlaceholder(userFirstname, "First Name");
        addPlaceholder(userLastName, "Last Name");
        addPlaceholder(userEmail, "Email");
        addPlaceholder(userContactNum, "Contact Number");
        addPasswordPlaceholder(userpassword, "Password");
        addPasswordPlaceholder(userpasswordconfirm, "Confirm Password");
    }

    private void addPlaceholder(JTextField field, String placeholderText) {
        field.setForeground(Color.GRAY);
        field.setText(placeholderText);

        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholderText)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholderText);
                }
            }
        });
    }

    private void addPasswordPlaceholder(JPasswordField field, String placeholderText) {
        field.setEchoChar((char) 0);  // Show characters initially
        field.setForeground(Color.GRAY);
        field.setText(placeholderText);

        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(field.getPassword()).equals(placeholderText)) {
                    field.setText("");
                    field.setEchoChar('*');  // Start masking input with '*'
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(field.getPassword()).isEmpty()) {
                    field.setEchoChar((char) 0);  // Show characters again
                    field.setForeground(Color.GRAY);
                    field.setText(placeholderText);
                }
            }
        });
    }

    private void userSignupbtnActionPerformed(java.awt.event.ActionEvent evt) {
        // Collect input data
        String userLogiName = userLoginName.getText();
        String userPassword = new String(userpassword.getPassword());
        String userRole = (String) userRoleDropdown.getSelectedItem();
        String userfirstname = userFirstname.getText();
        String userlastname =  userLastName.getText();
        String userPhone = userContactNum.getText();
        String useremail = userEmail.getText();

        // Validate email and password before proceeding
        if (!validateEmail(useremail)) {
            this.userEmail.setBackground(Color.red); // Highlight email field in red if invalid
            javax.swing.JOptionPane.showMessageDialog(this, "Invalid email address.");
            return; // Exit if validation fails
        } else {
            this.userEmail.setBackground(Color.white);
        }

        if (!validatePassword(userPassword)) {
            userpassword.setBackground(Color.red); // Highlight password field in red if invalid
            javax.swing.JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long and contain at least one digit, one special character, and one uppercase letter.");
            return; // Exit if validation fails
        } else {
            userpassword.setBackground(Color.white);
        }

        // Username must be > 8 characters
        if (userLogiName.length() < 8) {
            userLoginName.setBackground(Color.red); // Highlight username field in red if invalid
            javax.swing.JOptionPane.showMessageDialog(this, "Username must be at least 8 characters long.");
            return; // Exit if validation fails
        } else {
            userLoginName.setBackground(Color.white);

            // Call the userCreate method
            usermgment.userCreate(userLogiName, userPassword, userRole, userfirstname, userlastname, userPhone, useremail);

            // Pop-up message
            javax.swing.JOptionPane.showMessageDialog(this, "User created successfully.");
            this.dispose();
        }
    }

    private boolean validateEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailPattern);
    }

    private boolean validatePassword(String password) {
        return password.length() >= 8 && password.matches(".*\\d.*") && password.matches(".*\\W.*") && password.matches(".*[A-Z].*");
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registerform("superuser").setVisible(true); // Pass the user role here
            }
        });
    }

    private javax.swing.JLabel signuplabel;
    private javax.swing.JTextField userContactNum;
    private javax.swing.JTextField userEmail;
    private javax.swing.JTextField userFirstname;
    private javax.swing.JTextField userLastName;
    private javax.swing.JTextField userLoginName;
    private javax.swing.JButton userSignupbtn;
    private javax.swing.JPasswordField userpassword;
    private javax.swing.JPasswordField userpasswordconfirm;
    private javax.swing.JComboBox<String> userRoleDropdown;
}
