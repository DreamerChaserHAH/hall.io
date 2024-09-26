package com.hallio.admin;

public class Authentication {

    public static boolean authenticate(String username, String password) {
        return authenticationservices.authenticate(username, password);
    }

    public static String getRole(String username) {
        return authenticationservices.getRole(username);
    }
}
