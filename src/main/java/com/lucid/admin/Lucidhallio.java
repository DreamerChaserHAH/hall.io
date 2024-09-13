package com.lucid.admin;

public class Lucidhallio {
    public static void main(String[] args) {
        // Check if it is a fresh start
        if (startupmanager.checkFreshStart()) {
            // Start the application
            System.out.println("Starting the application...");
            com.lucid.gui.loginGUI.main(args);
        }
        else {
            startupmanager.performFreshStart(args);            
        }

    }
}
