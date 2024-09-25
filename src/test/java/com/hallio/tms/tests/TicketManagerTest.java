package com.hallio.tms.tests;

import com.hallio.dms.DatabaseManager;
import com.hallio.dms.FileManager;
import com.hallio.tms.Issue;
import com.hallio.tms.IssueStatus;
import com.hallio.tms.TicketManager;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicketManagerTest {
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
        TicketManager.createTicket(1);
        Issue result = new Issue(0, 0, IssueStatus.OPEN, 0);
        DatabaseManager.readRecord("issues", 1, result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getBookingId());
        assertEquals(IssueStatus.OPEN, result.getStatus());
        assertEquals(1, result.getManagedBy());
    }

    @Test
    public void testUpdateTicketStatus() throws Exception {
        TicketManager.createTicket(1);
        Issue result = new Issue(0, 0, IssueStatus.OPEN, 0);
        DatabaseManager.readRecord("issues", 1, result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getBookingId());
        assertEquals(IssueStatus.OPEN, result.getStatus());
        assertEquals(1, result.getManagedBy());
        TicketManager.updateTicketStatus(1, IssueStatus.RESOLVED);
        Issue updated = new Issue(0, 0, IssueStatus.OPEN, 0);
        DatabaseManager.readRecord("issues", 1, updated);
        assertEquals(1, updated.getId());
        assertEquals(1, updated.getBookingId());
        assertEquals(IssueStatus.RESOLVED, updated.getStatus());
        assertEquals(1, updated.getManagedBy());
    }

    @Test
    public void testUpdateTicketAssignee() throws Exception {
        TicketManager.createTicket(1);
        Issue result = new Issue(0, 0, IssueStatus.OPEN, 0);
        DatabaseManager.readRecord("issues", 1, result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getBookingId());
        assertEquals(IssueStatus.OPEN, result.getStatus());
        assertEquals(1, result.getManagedBy());
        TicketManager.updateTicketAssignee(1, 2);
        Issue updated = new Issue(0, 0, IssueStatus.OPEN, 0);
        DatabaseManager.readRecord("issues", 1, updated);
        assertEquals(1, updated.getId());
        assertEquals(1, updated.getBookingId());
        assertEquals(IssueStatus.OPEN, updated.getStatus());
        assertEquals(2, updated.getManagedBy());
    }

    @Test
    public void testDeleteTicket() throws Exception {
        TicketManager.createTicket(1);
        Issue result = new Issue(0, 0, IssueStatus.OPEN, 0);
        DatabaseManager.readRecord("issues", 1, result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getBookingId());
        assertEquals(IssueStatus.OPEN, result.getStatus());
        assertEquals(1, result.getManagedBy());
        TicketManager.deleteTicket(1);
        Issue deleted = new Issue(0, 0, IssueStatus.OPEN, 0);
        try {
            DatabaseManager.readRecord("issues", 1, deleted);
        }catch (Exception e){
            assertEquals("Record not found", e.getMessage());
        }
    }

    @Test
    public void testGetTicket() throws Exception {
        TicketManager.createTicket(1);
        Issue result = TicketManager.getTicket(1);
        assertEquals(1, result.getId());
        assertEquals(1, result.getBookingId());
        assertEquals(IssueStatus.OPEN, result.getStatus());
        assertEquals(1, result.getManagedBy());
    }

}
