package com.example.attendancesystem.entity;

import lombok.Data;

@Data
public class Student {
    private String id;
    private String studentNo;   // 学号
    private String name;        // 姓名
    private String classname;   // 班级
    private Integer age;        // 年龄
    private String gender;      // 性别
    private String phone;       // 电话
    private String email;       // 邮箱
    private String major;       // 专业
    private Integer status = 1; // 状态
}