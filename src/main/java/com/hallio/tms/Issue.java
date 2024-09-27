package com.hallio.tms;

import com.hallio.dms.IObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Issue extends IObject {
    private int bookingId;
    private int customerId;
    private String shortDescription;
    private IssueStatus status;
    private int managedBy;
    private String[] conversation;

    public Issue(int id, int bookingId, int customerId, String shortDescription, IssueStatus status, int managedBy) {
        this.id = id;
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.shortDescription = shortDescription;
        this.status = status;
        this.managedBy = managedBy;
        this.conversation = new String[0];
    }

    public Issue(){
        this.id = 0;
        this.bookingId = 0;
        this.customerId = 0;
        this.shortDescription = "";
        this.status = IssueStatus.OPEN;
        this.managedBy = 0;
        this.conversation = new String[0];
    }

    public Issue(int id, int bookingId, IssueStatus issueStatus, int managedBy) {
        this.id = id;
        this.bookingId = bookingId;
        this.status = issueStatus;
        this.managedBy = managedBy;
        this.customerId = 0;
        this.shortDescription = "";
        this.conversation = new String[0];
    }

    public int getId() {
        return id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String[] getConversation() {
        return conversation;
    }

    public int getManagedBy() {
        return managedBy;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public void setManagedBy(int managedBy) {
        this.managedBy = managedBy;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setConversation(String[] feedbackText) {
        this.conversation = feedbackText;
    }

    @Override
    protected LinkedList<String> getAttributes() {
        return new LinkedList<String>(
                Arrays.asList(
                        String.valueOf(this.bookingId),
                        this.status.toString(),
                        this.shortDescription,
                        String.valueOf(this.managedBy),
                        String.valueOf(this.customerId)
                )
        );
    }

    @Override
    protected String getFilePath() {
        return "issues.txt";
    }

    @Override
    protected void loadFromString(List<String> list) {
        // Load the issue from the list
        this.id = Integer.parseInt(list.get(0));
        this.bookingId = Integer.parseInt(list.get(1));
        this.status = IssueStatus.valueOf(list.get(2));
        this.shortDescription = list.get(3);
        this.managedBy = Integer.parseInt(list.get(4));
        this.customerId = Integer.parseInt(list.get(5));
    }
}
