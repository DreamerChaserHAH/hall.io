package com.hallio;
import com.hallio.dms.DatabaseManager;
import com.hallio.dms.FileManager;
import com.hallio.admin.usermgment;
public class startupmanager {

    // Check for flag file
    public static boolean checkFreshStart() {
        return com.hallio.dms.FileManager.isFileExist("start.flag");
    }

    // Perform Fresh Start
    public static void performFreshStart(String[] args) {
        String[] fileNames = {
                "users.txt", "detailHalls.txt", "sales.txt",
                "booking.txt", "staffs.txt", "crm.txt", "crmsg.txt"
        };
        try {
            if (args.length == 0) {
                FileManager.createEnvironment(); // Create the databases folder diu
                for (String fileName : fileNames) {         // Create Databases FIle
                    FileManager.createFile(fileName);
                }
                // Create a start.flag
                FileManager.createFile("start.flag");
                usermgment.superuserCreate(); // Create SuperUser
                System.out.println("Fresh startup completed successfully.");
                System.exit(0);
            }
            String databaseName = args[0];
            if (DatabaseManager.isDatabaseFileExist(databaseName)) {
                System.out.println("Database already exists. Please provide a different name.");
                System.exit(0);
            }
            DatabaseManager.createDatabase(databaseName);
            System.out.println("Database created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}






