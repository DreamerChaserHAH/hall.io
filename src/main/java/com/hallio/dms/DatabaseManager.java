package com.hallio.dms;

import java.util.ArrayList;
import java.util.List;

///<summary>
/// the class that uses file manager to deal with csv related operations
///</summary>
public class DatabaseManager {

    /// <summary>
    /// get the particular file path of the database
    /// </summary>
    private static String getFilePath(String databaseName){
        return databaseName + ".txt";
    }

    /// <summary>
    /// Create a "database" text file with the given name
    /// </summary>
    public static void createDatabase(String databaseName){
        FileManager.createFile(getFilePath(databaseName));
    }

    public static boolean isDatabaseFileExist(String databaseName){
        return FileManager.isFileExist(getFilePath(databaseName));
    }

    /// <summary>
    /// Create a record inside the "database" using the given object
    /// throws an error if the record already exists
    /// </summary>
    public static void createRecord(String databaseName, IObject object){
        FileManager.appendFile(getFilePath(databaseName), object.getAttributesWithIdAsString());
    }

    /// <summary>
    /// Read a record from the "database" using the id and store it in the given object
    /// throws an error if the record does not exist
    /// </summary>
    public static void readRecord(String databaseName, int id, IObject object) throws Exception {
        int recordIndex = findRecordIndex(getFilePath(databaseName), id);
        if(recordIndex == -1){
            throw new Exception("Record not found");
        }

        String relevantContent = FileManager.readLine(getFilePath(databaseName), recordIndex);
        object.LoadFromString(relevantContent);
    }

    ///<summary>
    ///get amount of records
    ///</summary>
    public static int getAmountOfRecords(String databaseName){
        List<String> content = FileManager.readFile(getFilePath(databaseName));
        return content.size();
    }

    ///<summary>
    ///read all records
    ///</summary>
    public static void readAllRecords(String databaseName, IObject[] objects){
        List<String> content = FileManager.readFile(getFilePath(databaseName));
        for(String record : content){
            System.out.println(record);
        }
        for(int i = 0; i < content.size(); i++) {
            objects[i].LoadFromString(content.get(i));
        }
    }

    /// <summary>
    /// find the line number of the record with the given id
    /// </summary>
    public static int findRecordIndex(String databasePath, int id){
        List<String> content = FileManager.readFile(databasePath);
        for(int i = 0; i < content.size(); i++){
            String[] record = content.get(i).split(",");
            if(Integer.parseInt(record[0]) == id){
                return i;
            }
        }
        return -1;
    }

    public static int getNext(String databaseName){
        List<String> content = FileManager.readFile(getFilePath(databaseName));
        if(content.size() == 0){
            return 1;
        }
        String[] record = content.get(content.size() - 1).split(",");
        return Integer.parseInt(record[0]) + 1;
    }

    /// <summary>
    /// Update a record inside the "database" using the id in the given object
    /// </summary>
    public static void updateRecord(String databaseName, int id, IObject value) throws Exception {
        /// Process
        /// 1. Find the line with the following id on index 0
        /// 2. Replace the line with the new value
        /// 3. Save the file
        int recordIndex = findRecordIndex(getFilePath(databaseName), id);
        if(recordIndex == -1){
            throw new Exception("Record not found");
        }

        FileManager.updateLine(getFilePath(databaseName), recordIndex, value.getAttributesWithIdAsString());
    }

    /// <summary>
    /// Delete a record inside the "database" using the id in the given object
    /// </summary>
    public static void deleteRecord(String databaseName, int id) throws Exception {
        /// Process
        /// 1. Find the line with the following id on index 0
        /// 2. Delete the line
        /// 3. Save the file
        int recordIndex = findRecordIndex(getFilePath(databaseName), id);
        if(recordIndex == -1){
            throw new Exception("Record not found");
        }
        FileManager.deleteLine(getFilePath(databaseName), recordIndex);
    }

    /// <summary>
    /// Delete the database file from the file system
    /// <summary>
    public static void deleteDatabase(String databaseName){
        FileManager.deleteFile(getFilePath(databaseName));
    }

    public List<String> readFile(String databaseName) {
        // Implement the logic to read from the file
        return new ArrayList<>();
    }
}
