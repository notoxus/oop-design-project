package com.group3.controller;

import com.group3.model.JsonUserDatabase;
import com.group3.model.User;

public class RegisterManager {
    private JsonUserDatabase userDatabase;

    public RegisterManager(JsonUserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public boolean register(User newUser) {
        // Check username existed yet
        if (userDatabase.isUsernameExist(newUser.getUsername())) {
            System.out.println("Registration failed: username already exists: " + newUser.getUsername());
            return false;
        }

        // Save to users.json
        boolean success = userDatabase.addUser(newUser);
        if (success) {
            System.out.println("Registration successful: " + newUser.getUsername());
        } else {
            System.out.println("Registration failed: unable to save account.");
        }
        
        return success;
    }
}
