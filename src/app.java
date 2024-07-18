import java.io.File;
import java.io.IOException;
public class app {
	public static void main(String[] args) {
        checkFreshstart(args);
    }

    public static void filecreation(String[] args) {
        String [] fileNames = {
            "users.txt", "detailHalls.txt", "sales.txt",
            "booking.txt", "staffs.txt", "crm.txt", "crmsg.txt"
        };
        String pathFolder = "textfiles";
        filemanagement.fileCreate(fileNames,pathFolder);
    }

    public static void checkFreshstart(String[] args) {
    String filePath = "first_run.flag"; 
    File flagFile = new File(filePath);

    if (!flagFile.exists()) {
        
        System.out.println("Running first-time setup...");
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
        System.out.println("Welcome back!");
    }
    }

    private static void performFreshStart(String[] args) {
        System.out.println("Performing First time startup lol");
        filecreation(args);
    }
}
