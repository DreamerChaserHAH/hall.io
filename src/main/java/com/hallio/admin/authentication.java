package com.hallio.admin;

public class authentication {
    private AuthenticationService authService = new AuthenticationService();

    public boolean authenticate(String username, String password) {
        return authService.authenticate(username, password);
    }

    public String getRole(String username) {
        return authService.getRole(username);
    }

    // New method to reset password
    public boolean resetPassword(String username, String newPassword) {
        return authService.resetPassword(username, newPassword);
    }
}