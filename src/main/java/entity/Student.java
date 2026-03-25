package com.example.attendancesystem.entity;

import lombok.Data;

@Data
public class Student {
    private String id;        // 学号
    private String name;      // 姓名
    private String classname; // 班级
    private Integer age;      // 年龄
    private String gender;    // 性别
    private String phone;     // 电话
}