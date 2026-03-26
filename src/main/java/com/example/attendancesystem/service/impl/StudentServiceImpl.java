package com.example.attendancesystem.service.impl;
import com.example.attendancesystem.entity.Student;
import com.example.attendancesystem.service.StudentService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
/**
 * 学生业务层实现类
 */
@Service
public class StudentServiceImpl implements StudentService  {
    private static final List<Student> STUDENT_DB = new ArrayList<>();
    private static Integer idCounter = 1;

    @Override
    public boolean addStudent(Student student) {
        // 业务校验1：学号不能为空
        if (student.getStudentNo() == null || student.getStudentNo().trim().isEmpty()) {
            throw new RuntimeException("学号不能为空");
        }

        // 业务校验2：姓名不能为空
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new RuntimeException("姓名不能为空");
        }
        if (student.getStudentNo() == null || student.getStudentNo().trim().isEmpty()) {
            throw new RuntimeException("学号不能为空");
        }

        // 业务校验2：姓名不能为空
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new RuntimeException("姓名不能为空");
        }

        // 业务校验3：年龄范围（如果提供了年龄）
        if (student.getAge() != null) {
            if (student.getAge() < 0 || student.getAge() > 150) {
                throw new RuntimeException("年龄必须在0-150之间");
            }
        }

        // 业务校验4：学号是否已存在
        boolean exists = STUDENT_DB.stream()
                .anyMatch(s -> s.getStudentNo().equals(student.getStudentNo()));
        if (exists) {
            throw new RuntimeException("学号已存在: " + student.getStudentNo());
        }

        // 设置ID并保存到模拟数据库
        student.setId(String.valueOf(idCounter++));
        STUDENT_DB.add(student);

        // 输出日志查看当前存储的学生列表
        System.out.println("新增学生成功，当前学生总数: " + STUDENT_DB.size());

        return true;
    }
}
