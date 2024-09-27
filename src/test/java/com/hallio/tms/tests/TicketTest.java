package com.hallio.tms.tests;

import com.hallio.dms.DatabaseManager;
import com.hallio.dms.FileManager;
import com.hallio.tms.Issue;
import com.hallio.tms.IssueStatus;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {
    @BeforeEach
    public void setUp() {
        Issue issue = new Issue(1, 1, IssueStatus.OPEN, 1);
        FileManager.deleteEnvironment();
        FileManager.createEnvironment();
        DatabaseManager.createDatabase("issues");
    }

    @AfterEach
    public void reset(){
        DatabaseManager.deleteDatabase("issues");
        FileManager.deleteEnvironment();
    }

    @Test
    public void testCreateTicket() throws Exception {
        Issue issue = new Issue(1, 1, IssueStatus.OPEN, 1);
        DatabaseManager.createRecord("issues", issue);
        Issue result = new Issue(0, 0, IssueStatus.OPEN, 0);
        DatabaseManager.readRecord("issues", 1, result);
        assertEquals(issue.getId(), result.getId());
        assertEquals(issue.getBookingId(), result.getBookingId());
        assertEquals(issue.getStatus(), result.getStatus());
        assertEquals(issue.getManagedBy(), result.getManagedBy());
    }

    @Test
    public void testUpdateTicketStatus() throws Exception {
        Issue issue = new Issue(1, 1, IssueStatus.OPEN, 1);
        DatabaseManager.createRecord("issues", issue);
        Issue result = new Issue(0, 0, IssueStatus.OPEN, 0);
        DatabaseManager.readRecord("issues", 1, result);
        assertEquals(issue.getId(), result.getId());
        assertEquals(issue.getBookingId(), result.getBookingId());
        assertEquals(issue.getStatus(), result.getStatus());
        assertEquals(issue.getManagedBy(), result.getManagedBy());
        result.setStatus(IssueStatus.RESOLVED);
        DatabaseManager.updateRecord("issues", 1, result);
        Issue updated = new Issue(0, 0, IssueStatus.OPEN, 0);
        DatabaseManager.readRecord("issues", 1, updated);
        assertEquals(result.getId(), updated.getId());
        assertEquals(result.getBookingId(), updated.getBookingId());
        assertEquals(result.getStatus(), updated.getStatus());
        assertEquals(result.getManagedBy(), updated.getManagedBy());
    }

    @Test
    public void testUpdateTicketAssignee() throws Exception {
        Issue issue = new Issue(1, 1, IssueStatus.OPEN, 1);
        DatabaseManager.createRecord("issues", issue);
        Issue result = new Issue(0, 0, IssueStatus.OPEN, 0);
        DatabaseManager.readRecord("issues", 1, result);
        assertEquals(issue.getId(), result.getId());
        assertEquals(issue.getBookingId(), result.getBookingId());
        assertEquals(issue.getStatus(), result.getStatus());
        assertEquals(issue.getManagedBy(), result.getManagedBy());
        result.setManagedBy(2);
        DatabaseManager.updateRecord("issues", 1, result);
        Issue updated = new Issue(0, 0, IssueStatus.OPEN, 0);
        DatabaseManager.readRecord("issues", 1, updated);
        assertEquals(result.getId(), updated.getId());
        assertEquals(result.getBookingId(), updated.getBookingId());
        assertEquals(result.getStatus(), updated.getStatus());
        assertEquals(result.getManagedBy(), updated.getManagedBy());
    }

    @Test
    public void testDeleteTicket() throws Exception {
        Issue issue = new Issue(1, 1, IssueStatus.OPEN, 1);
        DatabaseManager.createRecord("issues", issue);
        Issue result = new Issue(0, 0, IssueStatus.OPEN, 0);
        DatabaseManager.readRecord("issues", 1, result);
        assertEquals(issue.getId(), result.getId());
        assertEquals(issue.getBookingId(), result.getBookingId());
        assertEquals(issue.getStatus(), result.getStatus());
        assertEquals(issue.getManagedBy(), result.getManagedBy());
        DatabaseManager.deleteRecord("issues", 1);
        Issue deleted = new Issue(0, 0, IssueStatus.OPEN, 0);
        try {
            DatabaseManager.readRecord("issues", 1, deleted);
        } catch (Exception e) {
            assertEquals("Record not found", e.getMessage());
        }
    }
}
