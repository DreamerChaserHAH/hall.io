import java.util.Scanner;

public class Scheduler {
    private HallManager hallManager;

    public Scheduler(HallManager hallManager) {
        this.hallManager = hallManager;
    }

    public boolean login(String username, String password) {
        // For simplicity, this is hardcoded. You can replace it with proper authentication logic.
        return username.equals("scheduler") && password.equals("password");
    }

    public void manageHalls() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Add Hall\n2. View Halls\n3. Edit Hall\n4. Delete Hall\n5. Set Availability\n6. Set Maintenance\n7. Exit");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter Hall ID: ");
                String id = scanner.next();
                System.out.print("Enter Hall Name: ");
                String name = scanner.next();
                System.out.print("Enter Hall Type (1-Auditorium, 2-Banquet, 3-Meeting): ");
                int type = scanner.nextInt();
                
                Hall newHall;
                switch (type) {
                    case 1:
                        newHall = new Auditorium(id, name);
                        break;
                    case 2:
                        newHall = new BanquetHall(id, name);
                        break;
                    case 3:
                        newHall = new MeetingRoom(id, name);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid hall type");
                }
                
                hallManager.addHall(newHall);
                break;
                
            case 2:
                hallManager.viewHalls();
                break;
                
            case 3:
                System.out.print("Enter Hall ID to edit: ");
                String editID = scanner.next();
                System.out.print("Enter New Name: ");
                String newName = scanner.next();
                System.out.print("Enter New Hourly Rate: ");
                double newRate = scanner.nextDouble();
                hallManager.editHall(editID, newName, newRate);
                break;
                
            case 4:
                System.out.print("Enter Hall ID to delete: ");
                String deleteID = scanner.next();
                hallManager.deleteHall(deleteID);
                break;
                
            case 5:
                setAvailability(scanner);
                break;
                
            case 6:
                setMaintenance(scanner);
                break;
                
            case 7:
                System.out.println("Exiting hall management.");
                break;
        }
    }

    // Method to set the availability for a hall
    private void setAvailability(Scanner scanner) {
        System.out.print("Enter Hall ID: ");
        String hallID = scanner.next();
        System.out.print("Enter Start Date and Time (yyyy-MM-ddTHH:mm): ");
        LocalDateTime start = LocalDateTime.parse(scanner.next());
        System.out.print("Enter End Date and Time (yyyy-MM-ddTHH:mm): ");
        LocalDateTime end = LocalDateTime.parse(scanner.next());
        System.out.print("Enter Remarks: ");
        scanner.nextLine();  // Consume newline
        String remarks = scanner.nextLine();
        hallManager.setAvailability(hallID, start, end, remarks);
    }

   // Method to set the maintenance for a hall
    private void setMaintenance(Scanner scanner) {
        System.out.print("Enter Hall ID: ");
        String hallID = scanner.next();
        System.out.print("Enter Start Date and Time (yyyy-MM-ddTHH:mm): ");
        LocalDateTime start = LocalDateTime.parse(scanner.next());
        System.out.print("Enter End Date and Time (yyyy-MM-ddTHH:mm): ");
        LocalDateTime end = LocalDateTime.parse(scanner.next());
        System.out.print("Enter Remarks: ");
        scanner.nextLine();  // Consume newline
        String remarks = scanner.nextLine();
        hallManager.setMaintenance(hallID, start, end, remarks);
    }
}