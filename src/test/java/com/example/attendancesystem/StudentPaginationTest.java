package com.example.attendancesystem;
import com.example.attendancesystem.entity.Student;
import com.example.attendancesystem.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentPaginationTest {
    @Autowired
    private StudentRepository studentRepository;

    // 设置 TLS 协议（解决 SQL Server 连接问题）
    static {
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2,TLSv1.1,TLSv1.0");
    }

    /**
     * 在每个测试执行前，自动生成测试数据
     */
    @BeforeEach
    void setUpTestData() {
        System.out.println("\n========== 自动生成测试数据 ==========");

        // 清空旧数据
        studentRepository.deleteAll();

        // 自动生成50条学生数据
        List<Student> testStudents = generateTestStudents(50);

        // 批量保存
        List<Student> saved = studentRepository.saveAll(testStudents);

        System.out.println("✅ 已自动生成 " + saved.size() + " 条测试数据");
        System.out.println("=====================================\n");
    }

    /**
     * 自动生成测试学生数据
     */
    private List<Student> generateTestStudents(int count) {
        List<Student> students = new ArrayList<>();

        String[] names = {"张伟", "李芳", "王强", "刘洋", "陈静", "赵磊", "周敏", "吴涛", "郑丽", "林晨"};
        String[] classnames = {"计算机1班", "计算机2班", "软件工程1班", "软件工程2班", "大数据1班", "人工智能1班"};
        String[] genders = {"男", "女"};

        for (int i = 1; i <= count; i++) {
            Student student = new Student();
            student.setStudentNo(String.format("2024%04d", i));
            student.setName(names[i % names.length] + (i / names.length + 1));
            student.setClassname(classnames[i % classnames.length]);
            student.setAge(18 + (i % 8));
            student.setGender(genders[i % 2]);
            student.setPhone(String.format("138%08d", i));
            student.setEmail("student" + i + "@test.com");
            student.setMajor("计算机科学与技术");
            student.setStatus(i % 10 == 0 ? 0 : 1);
            students.add(student);
        }
        return students;
    }

    // ========== 测试方法 ==========

    @Test
    @Order(1)
    void testBasicPagination() {
        System.out.println("\n========== 测试1：基础分页查询 ==========");

        Pageable pageable = PageRequest.of(0, 10);
        Page<Student> page = studentRepository.findAll(pageable);

        System.out.println("总记录数：" + page.getTotalElements());
        System.out.println("总页数：" + page.getTotalPages());
        System.out.println("当前页数据量：" + page.getContent().size());

        assertThat(page.getTotalElements()).isEqualTo(50);
        assertThat(page.getContent().size()).isEqualTo(10);
    }

    @Test
    @Order(2)
    void testPaginationWithSort() {
        System.out.println("\n========== 测试2：带排序的分页查询 ==========");

        Sort sort = Sort.by(Sort.Direction.DESC, "age");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Student> page = studentRepository.findAll(pageable);

        System.out.println("按年龄降序排序，第一页数据：");
        page.getContent().forEach(s -> System.out.println("  - " + s.getName() + "，年龄：" + s.getAge()));

        assertThat(page).isNotNull();
    }

    @Test
    @Order(3)
    void testFindByClassname() {
        System.out.println("\n========== 测试3：根据班级分页查询 ==========");

        Pageable pageable = PageRequest.of(0, 10);
        Page<Student> page = studentRepository.findByClassname("计算机1班", pageable);

        System.out.println("计算机1班学生数：" + page.getTotalElements());

        assertThat(page).isNotNull();
    }

    @Test
    @Order(4)
    void testPageInfo() {
        System.out.println("\n========== 测试4：分页信息验证 ==========");

        Pageable pageable = PageRequest.of(0, 10);
        Page<Student> page = studentRepository.findAll(pageable);

        System.out.println("总记录数：" + page.getTotalElements());
        System.out.println("总页数：" + page.getTotalPages());
        System.out.println("是否有下一页：" + page.hasNext());
        System.out.println("是否有上一页：" + page.hasPrevious());

        assertThat(page.getTotalPages()).isEqualTo(5);  // 50条数据，每页10条
    }
}
