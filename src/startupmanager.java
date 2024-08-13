import java.io.File;
import java.io.IOException;
public class startupmanager {


    public static void checkFreshstart(String[] args) {
        String filePath = "first_run.flag"; 
        File flagFile = new File(filePath);
    
        if (!flagFile.exists()) {
            performFreshStart(args);
            try {
                boolean created = flagFile.createNewFile();
                if (created) {
                    System.out.println("First-time setup complete.");
                } else {
                    System.out.println("Failed to mark first-time setup.");
                }
            } catch (IOException e) {
                System.err.println("Error creating the flag file: " + e.getMessage());
            }
        } else {
            loginGUI.main(args);
        }
    }
    
    
    private static void performFreshStart(String[] args) {
        System.out.println("Performing First time startup lol");
        filecreation();
        administrator.createSuperuser(args);
    }
    
    public static void filecreation() {
        String[] fileNames = {
            "users.txt", "detailHalls.txt", "sales.txt",
            "booking.txt", "staffs.txt", "crm.txt", "crmsg.txt"
        };
        String pathFolder = "textfiles";

        // Ensure the directory exists
        File directory = new File(pathFolder);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Create each file in the directory
        for (String fileName : fileNames) {
            fileio.fileCreate(pathFolder + "/" + fileName);
        }
    }
}
