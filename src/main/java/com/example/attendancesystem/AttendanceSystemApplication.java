package com.example.attendancesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AttendanceSystemApplication {

    public static void main(String[] args) {
        // 解决 SSL/TLS 问题：允许使用 TLS 1.0
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2,TLSv1.1,TLSv1.0");
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,TLSv1.0");

        SpringApplication.run(AttendanceSystemApplication.class, args);
    }

    @GetMapping("/about")
    public String about() {
        return "姓名：刘默雅，专业：计算机科学与技术";
    }

    @GetMapping("/hello")
    public String hello() {
        return "欢迎来到班级考勤管理系统！";
    }
}