import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDate; 

public class administrator {

    static class SchedulerStaff {
        String username;
        String password;
        String email;
        String phonenumber;
        String role;
        LocalDate Lastregistered;
        LocalDate Lastlogin;

        public SchedulerStaff(String username, String password, String email, String phonenumber, String role) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.phonenumber = phonenumber;
            this.role = role;
            this.Lastregistered = LocalDate.now();
            this.Lastlogin = LocalDate.now();
        }
    }

    public static void createSuperuser(String[] args) {
        String defaultUsername = "admin";
        String defaultPassword = "admin";
        String defaultEmail = "NA";
        String defaultPhone = "NA";
        String defaultRole = "superuser";

        SchedulerStaff superuser = new SchedulerStaff(defaultUsername, defaultPassword, defaultEmail, defaultPhone, defaultRole);

        List<String> superuserDetails = Arrays.asList(
            superuser.username,
            superuser.password,
            superuser.email,
            superuser.phonenumber,
            superuser.role,
            superuser.Lastregistered.toString(),
            superuser.Lastlogin.toString()
        );

        String superuserDetailsString = String.join(",", superuserDetails);

        fileio fileio = new fileio();
        fileio.writeFile("textfiles/users.txt", superuserDetailsString);
    }

    public static void createStaffscheduler(String username, String password, String email, String phonenumber, String role) {
        SchedulerStaff staff = new SchedulerStaff(username, password, email, phonenumber, role);

        List<String> staffDetails = Arrays.asList(
            staff.username,
            staff.password,
            staff.email,
            staff.phonenumber,
            staff.role,
            staff.Lastregistered.toString(),
            staff.Lastlogin.toString()
        );

        String staffDetailsString = String.join(",", staffDetails);

        fileio fileio = new fileio();
        fileio.appendToFile("textfiles/users.txt", staffDetailsString);
    }
    
    // View Scheduler staff
    public static List<SchedulerStaff> viewSchedulerStaff() {
        List<SchedulerStaff> staffs = new ArrayList<>();
        fileio fileio = new fileio();
        List<String> staffDetails = fileio.readFile("textfiles/users.txt");

        for (String staffDetail : staffDetails) {
            String[] staffDetailArray = staffDetail.split(",");
            SchedulerStaff staff = new SchedulerStaff(
                staffDetailArray[0],
                staffDetailArray[1],
                staffDetailArray[2],
                staffDetailArray[3],
                staffDetailArray[4]
            );
            staffs.add(staff);
        }

        return staffs;
    }
}

