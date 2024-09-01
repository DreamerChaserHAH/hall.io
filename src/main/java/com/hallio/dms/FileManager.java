package com.hallio.dms;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static String rootPath = "databases/";

    private static String getPath(String filePath){
        return rootPath + filePath;
    }

    public static void createEnvironment(){
        File file = new File(rootPath);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }

    public static void deleteEnvironment(){
        File file = new File(rootPath);
        if (file.exists()) {
            file.delete();
        }
    }

    /// <summary>
    /// Check if a file exists or not
    /// </summary>
    public static boolean isFileExist(String filePath){
        File file = new File(getPath(filePath));
        return file.exists();
    }

    /// <summary>
    /// Create a file @ the given path
    /// </summary>
    public static void createFile(String filePath) {
        try {
            File file = new File(getPath(filePath));
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

    /// <summary>
    /// Read all the contents of a particular file
    /// </summary>
    public static List<String> readFile(String filePath) {
        List<String> content = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(getPath(filePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /// <summary>
    /// Replace and write the entire content of a file
    /// <summary>
    public static void writeFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getPath(filePath)))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /// <summary>
    /// Append a line or content to a file
    /// </summary>
    public static void appendFile(String filePath, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getPath(filePath), true))) {
            bw.write(content);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /// <summary>
    /// Delete a particular file from the file system
    /// </summary>
    public static void deleteFile(String filePath) {
        File file = new File(getPath(filePath));
        if (file.delete()) {
            System.out.println("Deleted the file: " + file.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

    /// <summary>
    /// Delete a particular line from a particular file
    /// <summary>
    public static void deleteLine(String filePath, int lineNumber) {
        List<String> lines = readFile(filePath);
        lines.remove(lineNumber);
        writeFile(filePath, String.join("\n", lines));
    }

    public static String readLine(String filePath, int lineNumber){
        List<String> lines = readFile(filePath);
        return lines.get(lineNumber);
    }

    /// <summary>
    /// Update a particular line inside a particular file
    /// </summary>
    public static void updateLine(String filePath, int lineNumber, String newContent) {
        List<String> lines = readFile(filePath);
        lines.set(lineNumber, newContent);
        writeFile(filePath, String.join("\n", lines));
    }

    /// <summary>
    /// Retrieve the last line inside a file
    /// </summary>
    public static String readLastLine(String filePath) {
        List<String> lines = readFile(filePath);
        return lines.getLast();
    }
}
