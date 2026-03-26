package com.example.attendancesystem.controller;
import com.example.attendancesystem.entity.Student;
import com.example.attendancesystem.service.StudentService;
import com.example.attendancesystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    // 模拟数据库数据
    private static List<Student> studentList = new ArrayList<>();

    static {
        Student s1 = new Student();
        s1.setId("42411157");
        s1.setName("刘默雅");
        s1.setClassname("计算机科学与技术3班");
        s1.setAge(20);
        s1.setGender("女");
        s1.setPhone("13800138001");

        Student s2 = new Student();
        s2.setId("2023002");
        s2.setName("李四");
        s2.setClassname("计算机1班");
        s2.setAge(21);
        s2.setGender("女");
        s2.setPhone("13800138002");

        Student s3 = new Student();
        s3.setId("2023003");
        s3.setName("王五");
        s3.setClassname("软件工程2班");
        s3.setAge(20);
        s3.setGender("男");
        s3.setPhone("13800138003");

        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);
    }

    /**
     * 任务一：学生信息查询接口（路径参数）
     * 根据学号查询学生信息
     */
    @GetMapping("/info/{studentId}")
    public Result<List<Student>> getStudentInfo(@PathVariable String studentId) {
        System.out.println("查询学号: " + studentId);

        for (Student student : studentList) {
            if (student.getId().equals(studentId)) {
                List<Student> result = new ArrayList<>();
                result.add(student);
                return Result.success(result);
            }
        }
        return Result.error("未找到学号为 " + studentId + " 的学生");
    }

    /**
     * 任务二：学生列表查询接口（查询参数）
     * URL示例: /student/list?classname=计算机1班&page=1&size=10
     */
    @GetMapping("/list")
    public Result<List<Student>> getStudentList(
            @RequestParam(required = false) String classname,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        System.out.println("查询参数: classname=" + classname + ", page=" + page + ", size=" + size);

        // 1. 按班级筛选
        List<Student> filteredList = new ArrayList<>();
        for (Student student : studentList) {
            if (classname == null || classname.isEmpty() || student.getClassname().equals(classname)) {
                filteredList.add(student);
            }
        }

        // 2. 分页处理
        int start = (page - 1) * size;
        int end = Math.min(start + size, filteredList.size());

        List<Student> result = new ArrayList<>();
        if (start < filteredList.size()) {
            result = filteredList.subList(start, end);
        }

        if (result.isEmpty()) {
            return Result.error("未找到符合条件的学生");
        }

        return Result.success(result);
    }

    /**
     * 额外接口1：POST接口，接收学号并返回打卡成功消息
     */
    @PostMapping("/attendance")
    public Result<String> studentAttendance(@RequestBody AttendanceParam param) {
        if (param.getStudentId() == null || param.getStudentId().isEmpty()) {
            return Result.error("学号不能为空");
        }
        return Result.success("学号 " + param.getStudentId() + " 打卡成功！");
    }

    /**
     * 额外接口2：GET接口，返回课程列表
     */
    @GetMapping("/courses")
    public Result<List<String>> getStudentCourses() {
        List<String> courses = Arrays.asList(
                "Javaee开发实践",
                "机器学习与数据挖掘",
                "Spring Boot框架",
                "数据库原理"
        );
        return Result.success(courses);
    }
}

/**
 * 考勤参数类
 */
class AttendanceParam {
    private String studentId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}