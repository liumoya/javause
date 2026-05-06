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

    // ========== 添加首页 ==========
    @GetMapping("/")
    public String home() {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head><title>班级考勤管理系统</title></head>" +
                "<body style='font-family: Arial, sans-serif; text-align: center; margin-top: 50px;'>" +
                "<h1>欢迎来到班级考勤管理系统</h1>" +
                "<p>你好，" + getCurrentUser() + "！</p>" +
                "<br/>" +
                "<h3>功能菜单：</h3>" +
                "<ul style='list-style: none; padding: 0;'>" +
                "<li><a href='/student/classes'>📋 查看班级列表</a></li>" +
                "<li><a href='/student/page?page=0&size=5'>📄 分页查询学生</a></li>" +
                "<li><a href='/student/list'>📊 学生列表</a></li>" +
                "<li><a href='/student/courses'>📚 课程列表</a></li>" +
                "<li><a href='/logout'>🚪 退出登录</a></li>" +
                "</ul>" +
                "</body>" +
                "</html>";
    }

    // 获取当前登录用户（可选）
    private String getCurrentUser() {
        // 简单返回默认用户名，实际可以从 SecurityContext 获取
        return "用户";
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