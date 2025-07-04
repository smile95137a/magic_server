package com.qiyuan.web.util;

public class ValidationUtil {

    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        if (password.length() < 8 || password.length() > 20) return false;
        if (!password.matches(".*[a-z].*")) return false; // 至少一個小寫
        if (!password.matches(".*\\d.*")) return false;   // 至少一個數字
        return true;
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
