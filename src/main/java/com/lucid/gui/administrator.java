package com.lucid.gui;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class administrator extends javax.swing.JFrame {

    private CardLayout cardLayout;

    public administrator() {
        initComponents();
        cardLayout = (CardLayout) dashboard.getLayout(); // Initialize the CardLayout
        btnExit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                System.exit(0); // Exit the application
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        panelSidebar = new javax.swing.JPanel();
        Title = new javax.swing.JTextField();
        btndashboard = new javax.swing.JLabel();
        btnExit = new javax.swing.JLabel();
        btnusermgment = new javax.swing.JLabel();
        btnstaffmgment = new javax.swing.JLabel();
        btnmyprofile = new javax.swing.JLabel();
        dashboard = new javax.swing.JPanel(); // This will hold the different panels

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelSidebar.setBackground(new java.awt.Color(255, 255, 255));

        Title.setEditable(false);
        Title.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        Title.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Title.setText("Hall IO Admin Management");
        Title.setBorder(null);

        btndashboard.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btndashboard.setIcon(new ImageIcon(getClass().getResource("/com/lucid/guidesign/Icons/dashboard.png"))); // NOI18N
        btndashboard.setText(" Dashboard");
        btndashboard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                cardLayout.show(dashboard, "dashboardPanel"); // Show Dashboard panel
            }
        });

        btnusermgment.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnusermgment.setIcon(new ImageIcon(getClass().getResource("/com/lucid/guidesign/Icons/usermanagement.png"))); // NOI18N
        btnusermgment.setText("User Management");
        btnusermgment.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                cardLayout.show(dashboard, "userManagementPanel"); // Show User Management panel
            }
        });

        btnstaffmgment.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnstaffmgment.setIcon(new ImageIcon(getClass().getResource("/com/lucid/guidesign/Icons/staff.png"))); // NOI18N
        btnstaffmgment.setText(" Staff Management");
        btnstaffmgment.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                cardLayout.show(dashboard, "staffManagementPanel"); // Show Staff Management panel
            }
        });

        btnmyprofile.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnmyprofile.setIcon(new ImageIcon(getClass().getResource("/com/lucid/guidesign/Icons/userprofile.png"))); // NOI18N
        btnmyprofile.setText(" My Profile");
        btnmyprofile.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                cardLayout.show(dashboard, "myProfilePanel"); // Show My Profile panel
            }
        });

        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnExit.setIcon(new ImageIcon(getClass().getResource("/com/lucid/guidesign/Icons/Exit.png"))); // NOI18N
        btnExit.setText(" Exit");

        // Sidebar Layout
        javax.swing.GroupLayout panelSidebarLayout = new javax.swing.GroupLayout(panelSidebar);
        panelSidebar.setLayout(panelSidebarLayout);
        panelSidebarLayout.setHorizontalGroup(
            panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSidebarLayout.createSequentialGroup()
                .addGroup(panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSidebarLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btndashboard)
                            .addComponent(btnusermgment)
                            .addComponent(btnstaffmgment)
                            .addComponent(btnmyprofile)))
                    .addComponent(Title, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelSidebarLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(btnExit)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelSidebarLayout.setVerticalGroup(
            panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSidebarLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(Title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btndashboard)
                .addGap(26, 26, 26)
                .addComponent(btnusermgment)
                .addGap(26, 26, 26)
                .addComponent(btnstaffmgment)
                .addGap(26, 26, 26)
                .addComponent(btnmyprofile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExit)
                .addContainerGap())
        );

        // Main dashboard panel using CardLayout
        dashboard.setLayout(new java.awt.CardLayout());

        // Create separate panels for Dashboard, User Management, Staff Management, and My Profile
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setBackground(new java.awt.Color(209, 233, 241));
        dashboard.add(dashboardPanel, "dashboardPanel"); // Add Dashboard panel

        JPanel userManagementPanel = new JPanel();
        userManagementPanel.setBackground(new java.awt.Color(255, 204, 204));
        dashboard.add(userManagementPanel, "userManagementPanel"); // Add User Management panel

        JPanel staffManagementPanel = new JPanel();
        staffManagementPanel.setBackground(new java.awt.Color(204, 255, 204));
        dashboard.add(staffManagementPanel, "staffManagementPanel"); // Add Staff Management panel

        JPanel myProfilePanel = new JPanel();
        myProfilePanel.setBackground(new java.awt.Color(204, 204, 255));
        dashboard.add(myProfilePanel, "myProfilePanel"); // Add My Profile panel

        // Main layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, 1300, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new administrator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JTextField Title;
    private javax.swing.JLabel btnExit;
    private javax.swing.JLabel btndashboard;
    private javax.swing.JLabel btnmyprofile;
    private javax.swing.JLabel btnstaffmgment;
    private javax.swing.JLabel btnusermgment;
    private javax.swing.JPanel dashboard;
    private javax.swing.JPanel panelSidebar;
    // End of variables declaration                   
}
