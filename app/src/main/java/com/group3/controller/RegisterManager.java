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
            System.out.println("Lỗi: Tên đăng nhập '" + newUser.getUsername() + " đã tồn tại!");
            return false;
        }

        // Save to users.json
        boolean success = userDatabase.addUser(newUser);
        if (success) {
            System.out.println("Đăng ký thành công cho tài khoản: " + newUser.getUsername());
        } else {
            System.out.println("Lỗi: Không thể lưu tài khoản!");
        }
        
        return success;
    }
}