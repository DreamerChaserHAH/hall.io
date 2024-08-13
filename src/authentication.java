import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class authentication {
    public static boolean login(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("textfiles/users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length >= 2) {
                    String fileUsername = credentials[0].trim();
                    String filePassword = credentials[1].trim();
                    if (fileUsername.equals(username) && filePassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Retrieving the role from login to determine the access level
    public static String getRole(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("textfiles/users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length >= 2) {
                    String fileUsername = credentials[0].trim();
                    String fileRole = credentials[2].trim();
                    if (fileUsername.equals(username)) {
                        return fileRole;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


