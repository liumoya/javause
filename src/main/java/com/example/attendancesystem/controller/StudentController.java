package com.example.attendancesystem.controller;

import com.example.attendancesystem.entity.Student;
import com.example.attendancesystem.utils.Result;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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

    // ========== 你原有的代码 ==========

    @GetMapping("/info/{studentId}")
    public Result<List<Student>> getStudentInfo(@PathVariable String studentId) {
        for (Student student : studentList) {
            if (student.getId().equals(studentId)) {
                return Result.success(Arrays.asList(student));
            }
        }
        return Result.error("未找到学号为 " + studentId + " 的学生");
    }

    @GetMapping("/list")
    public Result<List<Student>> getStudentList(
            @RequestParam(required = false) String classname,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        List<Student> filteredList = studentList.stream()
                .filter(s -> classname == null || classname.isEmpty() || s.getClassname().equals(classname))
                .collect(Collectors.toList());

        int start = (page - 1) * size;
        int end = Math.min(start + size, filteredList.size());

        if (start >= filteredList.size()) {
            return Result.error("未找到符合条件的学生");
        }

        return Result.success(filteredList.subList(start, end));
    }

    @PostMapping("/attendance")
    public Result<String> studentAttendance(@RequestBody AttendanceParam param) {
        if (param.getStudentId() == null || param.getStudentId().isEmpty()) {
            return Result.error("学号不能为空");
        }
        return Result.success("学号 " + param.getStudentId() + " 打卡成功！");
    }

    @GetMapping("/courses")
    public Result<List<String>> getStudentCourses() {
        return Result.success(Arrays.asList(
                "Javaee开发实践",
                "机器学习与数据挖掘",
                "Spring Boot框架",
                "数据库原理"
        ));
    }

    // ========== 第八周新增接口 ==========

    /**
     * 新增接口1：带排序的分页查询
     */
    @GetMapping("/page")
    public Result<Map<String, Object>> getStudentsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "age") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        // 1. 创建比较器
        Comparator<Student> comparator;
        if ("age".equals(sortField)) {
            comparator = Comparator.comparing(Student::getAge);
        } else if ("name".equals(sortField)) {
            comparator = Comparator.comparing(Student::getName);
        } else {
            comparator = Comparator.comparing(Student::getId);
        }

        if ("desc".equalsIgnoreCase(sortDirection)) {
            comparator = comparator.reversed();
        }

        // 2. 排序
        List<Student> sortedList = studentList.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        // 3. 分页
        int start = page * size;
        int end = Math.min(start + size, sortedList.size());
        List<Student> pageContent = start < sortedList.size()
                ? sortedList.subList(start, end)
                : new ArrayList<>();

        // 4. 构建返回结果
        Map<String, Object> response = new HashMap<>();
        response.put("data", pageContent);
        response.put("totalElements", sortedList.size());
        response.put("totalPages", (int) Math.ceil((double) sortedList.size() / size));
        response.put("currentPage", page);
        response.put("pageSize", size);
        response.put("hasNext", (page + 1) * size < sortedList.size());
        response.put("hasPrevious", page > 0);

        return Result.success(response);
    }

    /**
     * 新增接口2：多条件筛选 + 分页
     */
    @GetMapping("/filter")
    public Result<Map<String, Object>> filterStudents(
            @RequestParam(required = false) String classname,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // 1. 多条件筛选
        List<Student> filteredList = studentList.stream()
                .filter(s -> classname == null || classname.isEmpty() || s.getClassname().equals(classname))
                .filter(s -> gender == null || gender.isEmpty() || s.getGender().equals(gender))
                .filter(s -> minAge == null || s.getAge() >= minAge)
                .filter(s -> maxAge == null || s.getAge() <= maxAge)
                .collect(Collectors.toList());

        // 2. 分页
        int start = page * size;
        int end = Math.min(start + size, filteredList.size());
        List<Student> pageContent = start < filteredList.size()
                ? filteredList.subList(start, end)
                : new ArrayList<>();

        // 3. 构建返回结果
        Map<String, Object> response = new HashMap<>();
        response.put("data", pageContent);
        response.put("totalElements", filteredList.size());
        response.put("totalPages", (int) Math.ceil((double) filteredList.size() / size));
        response.put("currentPage", page);
        response.put("pageSize", size);

        return Result.success(response);
    }

    /**
     * 新增接口3：按示例查询
     */
    @GetMapping("/example")
    public Result<List<Student>> queryByExample(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String classname,
            @RequestParam(required = false) String gender) {

        List<Student> result = studentList.stream()
                .filter(s -> name == null || name.isEmpty() || s.getName().contains(name))
                .filter(s -> classname == null || classname.isEmpty() || s.getClassname().contains(classname))
                .filter(s -> gender == null || gender.isEmpty() || s.getGender().equals(gender))
                .collect(Collectors.toList());

        return result.isEmpty() ? Result.error("未找到符合条件的学生") : Result.success(result);
    }

    /**
     * 新增接口4：获取班级列表
     */
    @GetMapping("/classes")
    public Result<List<String>> getAllClasses() {
        List<String> classes = studentList.stream()
                .map(Student::getClassname)
                .distinct()
                .collect(Collectors.toList());
        return Result.success(classes);
    }
}

// 考勤参数类
class AttendanceParam {
    private String studentId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}