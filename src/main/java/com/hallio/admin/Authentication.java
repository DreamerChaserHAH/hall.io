package com.hallio.admin;

public interface Authentication {

    boolean authenticate(String username, String password);

    String getRole(String username);


}
