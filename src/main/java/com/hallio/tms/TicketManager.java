///<summary>
/// TicketManager class
/// This class is responsible for managing the tickets
/// It will be responsible for creating, updating, deleting and retrieving tickets
///</summary>

package com.hallio.tms;

import com.hallio.dms.DatabaseManager;

public class TicketManager {
    public static void createTicket(int bookingId) {
        // Create a new ticket
        Issue issue = new Issue(1, bookingId, IssueStatus.OPEN, 1);
        DatabaseManager.createRecord(issue.getFilePath(), issue);
    }

    public void updateTicketStatus(int ticketId, IssueStatus status) throws Exception {
        Issue issue = new Issue(0, 0, IssueStatus.OPEN, 0);
        // Load the ticket from the database
        DatabaseManager.readRecord(issue.getFilePath(), ticketId, issue);

        // Update the status
        issue.setStatus(status);

        // Save the updated ticket
        DatabaseManager.updateRecord(issue.getFilePath(), ticketId, issue);
    }

    public void updateTicketAssignee(int ticketId, int assigneeId) throws Exception {
        // Load the ticket from the database
        Issue issue = new Issue(0, 0, IssueStatus.OPEN, 0);
        // Load the ticket from the database
        DatabaseManager.readRecord(issue.getFilePath(), ticketId, issue);

        // Update the assignee
        issue.setManagedBy(assigneeId);

        // Save the updated ticket
        DatabaseManager.updateRecord(issue.getFilePath(), ticketId, issue);
    }

    public void deleteTicket(int ticketId) throws Exception {
        // Load the ticket from the database
        Issue issue = new Issue(0, 0, IssueStatus.OPEN, 0);
        // Load the ticket from the database
        DatabaseManager.readRecord(issue.getFilePath(), ticketId, issue);

        // Delete the ticket
        // This is a placeholder method, the actual implementation will depend on the database
        DatabaseManager.deleteRecord(issue.getFilePath(), ticketId);
    }
}
