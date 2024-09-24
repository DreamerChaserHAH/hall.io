package com.hallio.tms;

import com.hallio.dms.IObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Issue extends IObject {
    private int bookingId;
    private IssueStatus status;
    private int managedBy;

    public Issue(int id, int bookingId, IssueStatus status, int managedBy) {
        this.id = id;
        this.bookingId = bookingId;
        this.status = status;
        this.managedBy = managedBy;
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

    public int getManagedBy() {
        return managedBy;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public void setManagedBy(int managedBy) {
        this.managedBy = managedBy;
    }

    @Override
    protected LinkedList<String> getAttributes() {
        return new LinkedList<String>(
                Arrays.asList(
                        String.valueOf(this.bookingId),
                        this.status.toString(),
                        String.valueOf(this.managedBy)
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
        this.managedBy = Integer.parseInt(list.get(3));
    }
}
