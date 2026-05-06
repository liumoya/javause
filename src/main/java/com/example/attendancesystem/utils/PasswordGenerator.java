package com.example.attendancesystem.utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // 生成不同密码的加密结果
        System.out.println("密码 '123456' 加密后: " + encoder.encode("123456"));
        System.out.println("密码 'admin123' 加密后: " + encoder.encode("admin123"));
        System.out.println("密码 '123' 加密后: " + encoder.encode("123"));
    }
}
