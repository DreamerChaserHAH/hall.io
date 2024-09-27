package com.hallio.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import com.hallio.tms.Issue;
import com.hallio.tms.IssueStatus;
import com.hallio.tms.TicketManager;
import javafx.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class AdminChangeStatusGUI extends JFrame {

    private JList<String> issueList;
    private JComboBox<String> statusComboBox;
    private JButton changeStatusButton;

    // Sample issue data (you can replace this with actual data)
    private DefaultListModel<String> issues;

    public AdminChangeStatusGUI() {
        setTitle("Admin - Change Issue Status");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for the title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Change Status of Customer Issues");
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
        // Panel for displaying issue list
        JPanel issuePanel = new JPanel(new BorderLayout());
        issuePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        issueList = new JList<>(issues);
        issueList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane issueScrollPane = new JScrollPane(issueList);
        issuePanel.add(new JLabel("Select Issue to Change Status:"), BorderLayout.NORTH);
        issuePanel.add(issueScrollPane, BorderLayout.CENTER);
        add(issuePanel, BorderLayout.WEST);

        // Panel for selecting a new status
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel statusLabel = new JLabel("Select New Status:");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        statusComboBox = new JComboBox<>(new String[]{"In Progress", "Resolved", "Closed", "Open"});
        statusComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        statusPanel.add(statusLabel, BorderLayout.NORTH);
        statusPanel.add(statusComboBox, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.CENTER);

        // Panel for change status button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        changeStatusButton = new JButton("Change Status");
        changeStatusButton.setFont(new Font("Arial", Font.BOLD, 14));
        changeStatusButton.setBackground(new Color(58, 115, 255)); // Elegant blue color
        changeStatusButton.setForeground(Color.WHITE);
        changeStatusButton.setFocusPainted(false);
        changeStatusButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(changeStatusButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for changing the issue status
        Issue[] finalIssueArray = issueArray;
        changeStatusButton.addActionListener(e -> {
                int selectedIssueIndex = issueList.getSelectedIndex();
                String newStatus = (String) statusComboBox.getSelectedItem();

                if (selectedIssueIndex != -1 && newStatus != null) {
                    String issue = issueList.getSelectedValue();
                    IssueStatus status;
                    switch(newStatus) {
                        case "In Progress":
                            status = IssueStatus.IN_PROGRESS;
                            break;
                        case "Resolved":
                            status = IssueStatus.RESOLVED;
                            break;
                        case "Closed":
                            status = IssueStatus.CLOSED;
                            break;
                        default:
                            status = IssueStatus.OPEN;
                    }

                    try {
                        TicketManager.updateTicketStatus(finalIssueArray[selectedIssueIndex].id, status);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(AdminChangeStatusGUI.this,
                            "Status of \"" + issue + "\" changed to: " + newStatus,
                            "Status Changed", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(AdminChangeStatusGUI.this,
                            "Please select an issue and a status.",
                            "Incomplete Selection", JOptionPane.WARNING_MESSAGE);
                }
            
        });

        // Center the window on the screen
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminChangeStatusGUI());
    }
}
