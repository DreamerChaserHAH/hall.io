// Packages
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

// Class
public class filemanagement {

    // File creation
    public static void fileCreate(String[] fileNames, String pathFolder) {
        for (String fileName : fileNames) {
            createFile(pathFolder + "/" + fileName);
        }
    }
    private static void createFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println(fileName + " created successfully.");
            } else {
                System.out.println(fileName + " already exists.");
            }
        } catch (IOException errorcode) {
            System.out.println("An error occurred while creating " + fileName);
            errorcode.printStackTrace();
        }
    }


    // File Reading
    public static String fileRead(String fileName, String pathFolder) {
        StringBuilder content = new StringBuilder();
        try {
            File file = new File(pathFolder + "/" + fileName);
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


    // File Writing
    public static void fileWrite(String fileName, String content, String pathFolder) {
        try {
            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(content);

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}