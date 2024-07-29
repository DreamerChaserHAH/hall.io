//packages
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public abstract class fileio {
    public static void main(String filename) {
        try {
            File file = new File(filename);
            if (file.createNewFile()) {
                System.out.println(filename + " created successfully.");
            }
            else {
                System.out.println(filename + " already exists.");
            }
        } catch (Exception e) {
            System.out.println("An error has occured while craeting " + filename);
            e.printStackTrace();
        }
    }
    // Read from the bloody file yo
    public String readFile(String fileName) {
        StringBuilder content = new StringBuilder();
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
    public void writeFile(String filename, String content){
        try {
            File file = new File(filename);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("RIP Program lol");
            e.printStackTrace();
        }
    }
}
