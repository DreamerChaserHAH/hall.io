package com.hallio.gui;

import static java.awt.AWTEventMulticaster.add;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import com.hallio.tms.Issue;
import com.hallio.tms.TicketManager;

import javax.swing.*;

public class TicketResponder extends JFrame {

    private JList<String> feedbackList;
    private JTextArea messagesTextArea;
    private JTextArea responseTextArea;
    private JButton sendResponseButton;

    // Sample feedback data (you can replace this with actual data)
    private DefaultListModel<String> feedbacks;

    public TicketResponder() {
        setTitle("Customer Relationship Management - Respond to User Feedback");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for the title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Respond to User Feedback");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Sample feedback data
        feedbacks = new DefaultListModel<>();
        Issue[] issueArray = new Issue[0];
        try {
            issueArray = TicketManager.getAllTickets();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(Issue issue: issueArray){
            feedbacks.addElement("Issue "+issue.getId()+": "+issue.getShortDescription());
        }

        // Panel for displaying feedback list
        JPanel feedbackPanel = new JPanel(new BorderLayout());
        feedbackPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        feedbackList = new JList<>(feedbacks);
        feedbackList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane feedbackScrollPane = new JScrollPane(feedbackList);
        feedbackPanel.add(new JLabel("Select Feedback:"), BorderLayout.NORTH);
        feedbackPanel.add(feedbackScrollPane, BorderLayout.CENTER);
        add(feedbackPanel, BorderLayout.WEST);

        // Panel for response area
        JPanel responsePanel = new JPanel();
        GroupLayout responsePanelLayout = new GroupLayout(responsePanel);
        responsePanel.setLayout(responsePanelLayout);
        responsePanelLayout.setAutoCreateGaps(true);
        responsePanelLayout.setAutoCreateContainerGaps(true);

        //responsePanel.setLayout(new BoxLayout(responsePanel, BoxLayout.Y_AXIS));
        responsePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel messagesLabel = new JLabel("Messages:");
        JLabel responseLabel = new JLabel("Type Response:");
        responseLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        messagesTextArea = new JTextArea(10, 30);
        messagesTextArea.setLineWrap(true);
        messagesTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        messagesTextArea.setEditable(false);

        Issue[] finalIssueArray = issueArray;
        String[] messages = TicketManager.getConversationMessages(finalIssueArray[0].getId());
        StringBuilder conversation = new StringBuilder();
        for (String message : messages) {
            conversation.append(message).append("\n");
        }
        messagesTextArea.setText(conversation.toString());

        // Text area for typing response
        responseTextArea = new JTextArea(1, 30);
        responseTextArea.setLineWrap(true);
        responseTextArea.setWrapStyleWord(true);
        responseTextArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JViewport viewPort = new JViewport();
        viewPort.add(messagesTextArea);
        JScrollPane messagesScrollPane = new JScrollPane(messagesTextArea);

        responsePanelLayout.setHorizontalGroup(
                responsePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)  // Align components on the left
                        .addComponent(messagesLabel)
                        .addComponent(messagesScrollPane)
                        .addComponent(responseLabel)
                        .addComponent(responseTextArea)
        );
        // Vertical grouping
        responsePanelLayout.setVerticalGroup(
                responsePanelLayout.createSequentialGroup()  // Arrange components in sequence, top to bottom
                        .addComponent(messagesLabel)
                        .addComponent(messagesScrollPane)  // Large messages area
                        .addComponent(responseLabel)
                        .addComponent(responseTextArea)  // Smaller input area
        );

        //responsePanel.add(messagesTextArea, BorderLayout.CENTER);
        //responsePanel.add(messagesLabel);            // Messages label
        //responsePanel.add(new JScrollPane(messagesTextArea)); // Large uneditable text field wrapped in a scroll pane
        //responsePanel.add(responseLabel);            // Input label
        //responsePanel.add(responseTextArea);
        //responsePanel.add(messagesTextArea, BorderLayout.CENTER);
        //responsePanel.add(responseTextArea, BorderLayout.CENTER);
        add(responsePanel, BorderLayout.CENTER);

        // Panel for send button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        sendResponseButton = new JButton("Send Response");
        sendResponseButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendResponseButton.setBackground(new Color(58, 115, 255)); // Elegant blue color
        sendResponseButton.setForeground(Color.WHITE);
        sendResponseButton.setFocusPainted(false);
        sendResponseButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(sendResponseButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for sending response
        sendResponseButton.addActionListener(e -> {
                int selectedFeedbackIndex = feedbackList.getSelectedIndex();
                if (selectedFeedbackIndex != -1) {
                    String feedback = feedbackList.getSelectedValue();
                    String response = responseTextArea.getText();
                    
                    if (!response.trim().isEmpty()) {
                        // Here, you can handle sending the response logic
                        JOptionPane.showMessageDialog(TicketResponder.this,
                                "Response sent for: " + feedback,
                                "Response Sent", JOptionPane.INFORMATION_MESSAGE);
                        TicketManager.addConversationMessage(finalIssueArray[selectedFeedbackIndex].getId(), response);
                        String[] messages2 = TicketManager.getConversationMessages(finalIssueArray[selectedFeedbackIndex].getId());
                        StringBuilder conversation2 = new StringBuilder();
                        for (String message : messages2) {
                            conversation2.append(message).append("\n");
                        }
                        messagesTextArea.setText(conversation2.toString());// Clear response area after sending
                    } else {
                        JOptionPane.showMessageDialog(TicketResponder.this,
                                "Please type a response before sending.",
                                "Empty Response", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(TicketResponder.this,
                            "Please select an issue to message to.",
                            "No Issue Selected", JOptionPane.WARNING_MESSAGE);
                }
            
        });

        // Center the window on the screen
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicketResponder());
    }
}
