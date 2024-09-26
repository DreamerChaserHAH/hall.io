package com.hallio.admin;

import com.hallio.admin.User;

public class usermgment {

    public static void superuserCreate() {
        usermgmentservices.createSuperUser();
    }

    public static void userCreate(String userLogiName, String userPassword, String userRole, String userFirstName, String userLastName, String userPhone, String userEmail) {
        usermgmentservices.createUser(userLogiName, userPassword, userRole, userFirstName, userLastName, userPhone, userEmail);
    }

    public static void userDelete(String username) {
        usermgmentservices.deleteUser(username);
    }

    public static void userEdit(String username, User updatedUser) {
        usermgmentservices.editUser(username, updatedUser);
    }
}