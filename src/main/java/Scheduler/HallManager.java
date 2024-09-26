package Scheduler;

import com.lucid.fileio.FileManager;
import com.lucid.fileio.IObject;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HallManager extends IObject {
    private List<Hall> halls;

    public HallManager() {
        halls = new ArrayList<>();
        loadHalls();
    }

    public void addHall(Hall hall) {
        halls.add(hall);
        hall.save(); // Save the hall using the IObject method
    }

    public void updateHall(Hall hall) {
        // Logic to update hall in the list
        hall.save(); // Save the updated hall
    }

    public void deleteHall(int hallId) {
        halls.removeIf(hall -> hall.getId() == hallId);
        Hall hallToDelete = new Hall(hallId, "", "", 0, 0); // Temporary Hall object for deletion
        hallToDelete.delete(hallId); // Delete the hall using the IObject method
    }

    public void loadHalls() {
        try (BufferedReader br = new BufferedReader(new FileReader("databases/detailHalls.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Hall hall = new Hall(0, "", "", 0, 0); // Temporary Hall object
                hall.LoadFromString(line); // Load from string
                halls.add(hall);
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

    @Override
    protected LinkedList<String> getAttributes() {
        LinkedList<String> attributes = new LinkedList<>();
        for (Hall hall : halls) {
            attributes.add(hall.getAttributesAsString());
        }
        return attributes;
    }

    @Override
    protected String getFilePath() {
        return "databases/hallManager.txt"; // Path to save/load HallManager data
    }

    @Override
    protected void loadFromString(List<String> attributes) {
        // Logic to load HallManager state from a string (if needed)
    }

    @Override
    public void save() {
        // Save the HallManager state
        FileManager.writeFile(this.getFilePath(), String.join("\n", getAttributes()));
    }
}