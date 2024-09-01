package com.hallio.dms.tests;

import com.hallio.dms.DatabaseManager;
import com.hallio.dms.FileManager;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseManagerTest {
    @BeforeEach
    public void setUp() {
        FileManager.deleteEnvironment();
        FileManager.createEnvironment();
        DatabaseManager.createDatabase("sample");
    }

    @AfterEach
    public void reset(){
        DatabaseManager.deleteDatabase("sample");
        FileManager.deleteEnvironment();
    }

    @Test
    public void testCreateDatabase() {
        File file = new File("databases/sample.txt");
        assertTrue(file.exists());
    }

    @Test
    public void testDeleteDatabase() {
        DatabaseManager.deleteDatabase("sample");
        File file = new File("databases/sample.txt");
        assertFalse(file.exists());
    }

    @Test
    public void testIsDatabaseFileExist() {
        assertTrue(DatabaseManager.isDatabaseFileExist("sample"));
    }

    @Test
    public void testCreateRecord() {
        SampleObject sampleObject = new SampleObject(1, "John", 20);
        DatabaseManager.createRecord("sample", sampleObject);
        assertEquals("1,John,20", FileManager.readFile("sample.txt").getFirst());
    }

    @Test
    public void testReadRecord() throws Exception {
        SampleObject sampleObject = new SampleObject(1, "John", 20);
        DatabaseManager.createRecord("sample", sampleObject);
        SampleObject sampleObject2 = new SampleObject(0, "", 0);
        SampleObject resultObject = new SampleObject();
        DatabaseManager.readRecord("sample", 1, resultObject);
        assertEquals("1,John,20", resultObject.getAttributesWithIdAsString());
    }

    @Test
    public void testFindRecordIndex() {
        SampleObject sampleObject = new SampleObject(1, "John", 20);
        DatabaseManager.createRecord("sample", sampleObject);
        assertEquals(0, DatabaseManager.findRecordIndex("sample.txt", 1));
    }

    @Test
    public void testUpdateRecord() throws Exception {
        SampleObject sampleObject = new SampleObject(1, "John", 20);
        DatabaseManager.createRecord("sample", sampleObject);
        SampleObject sampleObject2 = new SampleObject(1, "Jane", 21);
        DatabaseManager.updateRecord("sample", 1, sampleObject2);
        assertEquals("1,Jane,21", FileManager.readFile("sample.txt").getFirst());
    }

    @Test
    public void testDeleteRecord() throws Exception {
        SampleObject sampleObject = new SampleObject(1, "John", 20);
        DatabaseManager.createRecord("sample", sampleObject);
        DatabaseManager.deleteRecord("sample", 1);
        assertEquals(0, FileManager.readFile("sample.txt").size());
    }
}
