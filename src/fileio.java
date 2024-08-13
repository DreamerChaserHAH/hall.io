// Packages
import java.io.*;
import java.util.*;


public class fileio {
    // Create Files
    public static void fileCreate(String filepath) {
        try {
            File file = new File(filepath);
            if (file.createNewFile()) {
                System.out.println("Done creating file" + file.getName());
            }
            else{
                System.out.println("File is already existed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Read content from file
    public List<String> readFile(String filePath) {
        List<String> content = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    // Write Contents to file
    public void writeFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Append content to file
    public void appendToFile(String filePath, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(content);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

