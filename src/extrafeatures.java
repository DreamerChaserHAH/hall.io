import java.io.File;

public class extrafeatures extends fileio{
    public void fileDelete(String filename){
        File file = new File(filename);
        if (file.delete()) {
            System.out.println(filename + " deleted successfully.");
        } else {
            System.out.println("An error occurred while deleting " + filename);
        }
    }
    public void fileRename(String oldName, String newName){
        File oldFile = new File(oldName);
        File newFile = new File(newName);
        if (oldFile.renameTo(newFile)) {
            System.out.println(oldName + " renamed to " + newName + " enjoy enjoy.");
        } else {
            System.out.println("An error occurred while renaming " + oldName + " to " + newName);
        }
    }
}