package com.lucid.Scheduler;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HallManager hallManager = new HallManager(); // Create HallManager instance
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Add Meeting Room\n2. Add Auditorium\n3. Add Banquet Hall\n4. View Halls\n5. Update Hall\n6. Delete Hall\n7. Exit");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Add a new Meeting Room
                    addHall(scanner, "Meeting Room");
                    break;
                case 2:
                    // Add a new Auditorium
                    addHall(scanner, "Auditorium");
                    break;
                case 3:
                    // Add a new Banquet Hall
                    addHall(scanner, "Banquet Hall");
                    break;
                case 4:
                    // View all halls
                    hallManager.displayHalls();
                    break;
                case 5:
                    // Update an existing hall
                    updateHall(scanner);
                    break;
                case 6:
                    // Delete a hall
                    deleteHall(scanner);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);

        scanner.close(); // Close the scanner
    }

    private static void addHall(Scanner scanner, String hallType) {
        System.out.print("Enter Hall ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter Hall Location: ");
        String location = scanner.next();
        System.out.print("Enter Maintenance Start Time (epoch): ");
        long startTimeEpoch = scanner.nextLong();
        System.out.print("Enter Maintenance End Time (epoch): ");
        long endTimeEpoch = scanner.nextLong();

        Hall newHall;
        switch (hallType) {
            case "Meeting Room":
                newHall = new MeetingRoom(id, location, new Date(startTimeEpoch), new Date(endTimeEpoch));
                break;
            case "Auditorium":
                newHall = new Auditorium(id, location, new Date(startTimeEpoch), new Date(endTimeEpoch));
                break;
            case "Banquet Hall":
                newHall = new BanquetHall(id, location, new Date(startTimeEpoch), new Date(endTimeEpoch));
                break;
            default:
                throw new IllegalArgumentException("Invalid hall type");
        }
        hallManager.addHall(newHall);
    }

    private static void updateHall(Scanner scanner) {
        System.out.print("Enter Hall ID to update: ");
        int updateId = scanner.nextInt();
        System.out.print("Enter New Hall Type: ");
        String newHallType = scanner.next();
        System.out.print("Enter New Hall Location: ");
        String newLocation = scanner.next();
        System.out.print("Enter New Maintenance Start Time (epoch): ");
        long newStartTimeEpoch = scanner.nextLong();
        System.out.print("Enter New Maintenance End Time (epoch): ");
        long newEndTimeEpoch = scanner.nextLong();
        hallManager.updateHall(updateId, newHallType, newLocation, new Date(newStartTimeEpoch), new Date(newEndTimeEpoch));
    }

    private static void deleteHall(Scanner scanner) {
        System.out.print("Enter Hall ID to delete: ");
        int deleteId = scanner.nextInt();
        hallManager.deleteHall(deleteId);
    }
}
