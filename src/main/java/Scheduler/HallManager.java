package Scheduler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HallManager {
    private List<Hall> halls;

    public HallManager() {
        halls = new ArrayList<>();
        loadHalls("databases/detailHalls.txt");
    }

    public void addHall(Hall hall) {
        halls.add(hall);
        saveHalls("databases/detailHalls.txt");
    }

    public void updateHall(Hall hall) {
        // Logic to update hall in the list
        saveHalls("databases/detailHalls.txt");
    }

    public void deleteHall(int hallId) {
        halls.removeIf(hall -> hall.getId() == hallId);
        saveHalls("databases/detailHalls.txt");
    }

    public List<Hall> loadHalls(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim()); // Hall ID
                String name = parts[1].trim(); // Hall name
                double hourlyRate = Double.parseDouble(parts[2].trim()); // Hourly rate
                int totalSeats = Integer.parseInt(parts[3].trim()); // Total seats
                halls.add(new Hall(id, name, "Location", hourlyRate, totalSeats));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return halls;
    }

    private void saveHalls(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Hall hall : halls) {
                bw.write(hall.getId() + "," + hall.getHallType() + "," + hall.getHourlyRate() + "," + hall.getTotalSeats());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hallExists(int hallId) {
        return halls.stream().anyMatch(hall -> hall.getId() == hallId);
    }

    public Hall getHallById(int hallId) {
        return halls.stream().filter(hall -> hall.getId() == hallId).findFirst().orElse(null);
    }
}