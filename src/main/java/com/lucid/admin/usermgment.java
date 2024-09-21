package com.lucid.admin;

import com.lucid.fileio.FileManager;

public class usermgment {

    public class staff {
        public String userLogiName;
        public String userPassword;
        public String userRole;
        public String userFirstName;
        public String userLastName;
        public String userPhone;
        public String userEmail;
        public String userRegDate;
        public String userLastLogin;
    }

    public static void superuserCreate() {
        String currentDate = java.time.LocalDate.now().toString();
        String details = "admin,admin,superuser,na,na," + currentDate + "," + currentDate;
        FileManager.appendFile("users.txt", details);
    }

    public static void userCreate(String userLogiName, String userPassword, String userRole, String userFirstName,String userLastName ,String userPhone, String userEmail) {
        String currentDate = java.time.LocalDate.now().toString();
        String details = userLogiName + "," + userPassword + "," + userRole + "," + userFirstName + "," +userLastName+ "," + userPhone + "," + userEmail + "," + currentDate + "," + currentDate;
        FileManager.appendFile("users.txt", details);
    }

    


}
