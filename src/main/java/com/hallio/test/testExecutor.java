package com.hallio.test;

import com.hallio.dms.DatabaseManager;
import com.hallio.dms.FileManager;
import com.hallio.gui.AdminChangeStatusGUI;
import com.hallio.gui.TicketResponder;
import com.hallio.tms.TicketManager;
import com.hallio.admin.usermgment;

public class testExecutor {
    public static void main(String[] args) throws Exception {
        FileManager.createEnvironment();
        DatabaseManager.createDatabase("users");
        TicketManager.createDatabases();
        usermgment.userCreate("customer1", "password1", "scheduler", "scheduler1", "scheduler2", "+6012345", "contact@mail.com");
        usermgment.userCreate("customer2", "password1", "scheduler", "scheduler1", "scheduler2", "+6012345", "contact@mail.com");
        TicketManager.createTicket(1, 1, "Test Ticket");
    }
}
