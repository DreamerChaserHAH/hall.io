package com.hallio.admin;

public class usermgment {

    public static void superuserCreate() {
        usermgmentservices.createSuperUser();
    }

    public static void userCreate(String userLogiName, String userPassword, String userRole, String userFirstName, String userLastName, String userPhone, String userEmail) {
        usermgmentservices.createUser(userLogiName, userPassword, userRole, userFirstName, userLastName, userPhone, userEmail);
    }
}