package com.hallio.dms.tests;

import com.hallio.dms.FileManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {

    @BeforeEach
    public void setUp() {
        FileManager.deleteEnvironment();
        FileManager.createEnvironment();
        FileManager.createFile("sample.txt");
    }

    @AfterEach
    public void reset(){
        FileManager.deleteEnvironment();
    }
    @Test
    public void testCreateFile() {
        File file = new File("databases/sample.txt");
        assertTrue(file.exists());
    }

    @Test
    public void testIsFileExist() {
        assertTrue(FileManager.isFileExist("sample.txt"));
    }

    @Test
    public void testReadFile() {
        FileManager.writeFile("sample.txt", "");
        FileManager.appendFile("sample.txt", "1,John,20");
        assertEquals("1,John,20", FileManager.readFile("sample.txt").getFirst());
    }

    @Test
    public void testAppendFile() {
        FileManager.appendFile("sample.txt", "1,John,20");
        FileManager.appendFile("sample.txt", "2,John,20");
        assertEquals("2,John,20", FileManager.readFile("sample.txt").getLast());
    }

    @Test
    public void testReadLine() {
        FileManager.writeFile("sample.txt", "");
        FileManager.appendFile("sample.txt", "1,John,20");
        assertEquals("1,John,20", FileManager.readLine("sample.txt", 0));
    }

    @Test
    public void testWriteFile() {
        FileManager.writeFile("sample.txt", "1,John,20");
        assertEquals("1,John,20", FileManager.readFile("sample.txt").getFirst());
    }

    @Test
    public void testDeleteFile() {
        FileManager.deleteFile("sample.txt");
        assertFalse(FileManager.isFileExist("sample.txt"));
    }
}
