package com.hallio.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import com.hallio.admin.User;
import com.hallio.tms.TicketManager;
import com.hallio.tms.Issue;

public class AdminAssignSchedulerGUI extends JFrame {

    private JList<String> issueList;
    private JComboBox<String> schedulerComboBox;
    private JButton assignSchedulerButton;

    // Sample issue data (you can replace this with actual data)
    private DefaultListModel<String> issues;

    // Sample scheduler (staff) data
    private String[] schedulers = {};

    public AdminAssignSchedulerGUI() {
        setTitle("Admin - Assign Scheduler");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for the title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Assign Scheduler to Fix Issues");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Sample issue data
        issues = new DefaultListModel<>();
        Issue[] issueArray = new Issue[0];
        try {
            issueArray = TicketManager.getAllTickets();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(Issue issue: issueArray){
            issues.addElement("Issue "+issue.getId()+": "+issue.getShortDescription());
        }

        User[] schedulerUserList = TicketManager.getAllSchedulers();
        for(User user: schedulerUserList){
            schedulers = Arrays.copyOf(schedulers, schedulers.length + 1);
            schedulers[schedulers.length - 1] = user.getUsername() + " " + user.getRole();
        }

        // Panel for displaying issue list
        JPanel issuePanel = new JPanel(new BorderLayout());
        issuePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        issueList = new JList<>(issues);
        issueList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane issueScrollPane = new JScrollPane(issueList);
        issuePanel.add(new JLabel("Select Issue to Assign:"), BorderLayout.NORTH);
        issuePanel.add(issueScrollPane, BorderLayout.CENTER);
        add(issuePanel, BorderLayout.WEST);

        // Panel for selecting a scheduler
        JPanel schedulerPanel = new JPanel(new BorderLayout());
        schedulerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel schedulerLabel = new JLabel("Select Scheduler:");
        schedulerLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        schedulerComboBox = new JComboBox<>(schedulers);
        schedulerComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        schedulerPanel.add(schedulerLabel, BorderLayout.NORTH);
        schedulerPanel.add(schedulerComboBox, BorderLayout.CENTER);
        add(schedulerPanel, BorderLayout.CENTER);

        // Panel for assign button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        assignSchedulerButton = new JButton("Assign Scheduler");
        assignSchedulerButton.setFont(new Font("Arial", Font.BOLD, 14));
        assignSchedulerButton.setBackground(new Color(58, 115, 255)); // Elegant blue color
        assignSchedulerButton.setForeground(Color.WHITE);
        assignSchedulerButton.setFocusPainted(false);
        assignSchedulerButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(assignSchedulerButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for assigning the scheduler
        Issue[] finalIssueArray = issueArray;
        assignSchedulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIssueIndex = issueList.getSelectedIndex();
                int selectedSchedulerIndex = schedulerComboBox.getSelectedIndex();

                String selectedScheduler = (String) schedulerComboBox.getSelectedItem();

                if (selectedIssueIndex != -1 && selectedScheduler != null) {

                    try {
                        TicketManager.updateTicketAssignee(finalIssueArray[selectedIssueIndex].getId(), schedulerUserList[selectedSchedulerIndex].id);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(AdminAssignSchedulerGUI.this,
                            "Scheduler " + selectedScheduler + " assigned to: " + finalIssueArray[selectedIssueIndex].getId(),
                            "Scheduler Assigned", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(AdminAssignSchedulerGUI.this,
                            "Please select an issue and a scheduler.",
                            "Incomplete Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Center the window on the screen
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminAssignSchedulerGUI());
    }
}
