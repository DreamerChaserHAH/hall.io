package com.hallio.admin;
import javax.swing.table.DefaultTableModel;
public class usermgment {

    public static void superuserCreate() {
        usermgmentservices.createSuperUser();
    }

    public static void userCreate(String userLogiName, String userPassword, String userRole, String userFirstName, String userLastName, String userPhone, String userEmail) {
        usermgmentservices.createUser(userLogiName, userPassword, userRole, userFirstName, userLastName, userPhone, userEmail);
    }

    public static void loadData(String fileName, DefaultTableModel model) {
        usermgmentservices.loadDataFromFile(fileName, model);
    }
}