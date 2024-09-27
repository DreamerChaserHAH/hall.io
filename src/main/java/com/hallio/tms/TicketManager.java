///<summary>
/// TicketManager class
/// This class is responsible for managing the tickets
/// It will be responsible for creating, updating, deleting and retrieving tickets
///</summary>

package com.hallio.tms;

import com.hallio.admin.User;
import com.hallio.dms.DatabaseManager;

public class TicketManager {
    private static final String issueFileName = "issues";

    public static void createDatabases(){
        try {
            DatabaseManager.createDatabase("issues");
            DatabaseManager.createDatabase("conversations");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTicket(int bookingId, int customerId, String shortDescription) throws Exception {
        // Create a new ticket
        int newId = DatabaseManager.getNext(issueFileName);
        Issue issue = new Issue(newId, bookingId, customerId, shortDescription, IssueStatus.OPEN, 0);
        DatabaseManager.createRecord(issueFileName, issue);
        Conversation conversation = new Conversation(newId);
        DatabaseManager.createRecord("conversations", conversation);
    }

    public static Issue getTicket(int ticketId) throws Exception {
        Issue issue = new Issue();
        // Load the ticket from the database
        DatabaseManager.readRecord(issueFileName, ticketId, issue);

        Conversation conversation = new Conversation(ticketId);
        DatabaseManager.readRecord("conversations", ticketId, conversation);
        issue.setConversation(conversation.getConversation());

        return issue;
    }

    public static void updateTicketStatus(int ticketId, IssueStatus status) throws Exception {
        Issue issue = new Issue();
        // Load the ticket from the database
        DatabaseManager.readRecord(issueFileName, ticketId, issue);

        // Update the status
        issue.setStatus(status);

        // Save the updated ticket
        DatabaseManager.updateRecord(issueFileName, ticketId, issue);
    }

    public static void updateTicketAssignee(int ticketId, int assigneeId) throws Exception {
        // Load the ticket from the database
        Issue issue = new Issue();
        // Load the ticket from the database
        DatabaseManager.readRecord(issueFileName, ticketId, issue);

        // Update the assignee
        issue.setManagedBy(assigneeId);

        // Save the updated ticket
        DatabaseManager.updateRecord(issueFileName, ticketId, issue);
    }

    public static void addConversationMessage(int ticketId, String newMessage){
        Conversation conversation = new Conversation();
        try {
            DatabaseManager.readRecord("conversations", ticketId, conversation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        conversation.addMessage(newMessage);
        try {
            DatabaseManager.updateRecord("conversations", ticketId, conversation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] getConversationMessages(int ticketId){
        Conversation conversation = new Conversation();
        try {
            DatabaseManager.readRecord("conversations", ticketId, conversation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conversation.getConversation();
    }

    public static void deleteTicket(int ticketId) throws Exception {
        // Load the ticket from the database
        Issue issue = new Issue();
        // Load the ticket from the database
        try {
            DatabaseManager.readRecord(issueFileName, ticketId, issue);
            // Delete the ticket
            // This is a placeholder method, the actual implementation will depend on the database
            DatabaseManager.deleteRecord(issueFileName, ticketId);
        }catch(Exception e){
            System.out.println("Ticket not found");
        }
    }

    public static Issue[] getAllTickets() throws Exception {
        // Get all tickets from the database
        // This is a placeholder method, the actual implementation will depend on the database
        Issue[] issues = new Issue[DatabaseManager.getAmountOfRecords(issueFileName)];
        for(int i = 0; i < issues.length; i++){
            issues[i] = new Issue();
        }
        System.out.println(issues.length);
        DatabaseManager.readAllRecords(issueFileName, issues);
        return issues;
    }

    public static User[] getAllSchedulers(){
        User[] users = new User[DatabaseManager.getAmountOfRecords("users")];
        for(int i = 0; i < users.length; i++){
            users[i] = new User();
        }
        DatabaseManager.readAllRecords("users", users);

        User[] schedulers = new User[0];
        for (User user : users) {
            if (user.getRole().equals("scheduler")) {
                User[] temp = schedulers;
                schedulers = new User[schedulers.length + 1];
                System.arraycopy(temp, 0, schedulers, 0, temp.length);
                schedulers[schedulers.length - 1] = user;
            }
        }
        return schedulers;
    }
}
