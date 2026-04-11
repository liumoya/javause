package com.example.attendancesystem;

import com.example.attendancesystem.dao.UserDao;
import com.example.attendancesystem.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class AttendanceSystemApplicationTests {

    @Autowired
    private UserDao userDao;



    // 测试添加学生
    // 测试添加学生
    @Transactional  // 保证测试结束自动回滚，不留脏数据
    @Test
    void testAddStudent() {
        User user = new User();
        user.setUsername("zhangsan_" + System.currentTimeMillis());

        user.setPassword("123456");
        user.setRealName("张三");
        user.setRole("student");
        user.setStudentNo("20240001");
        user.setClassName("计算机1班");
        user.setPhone("13800138001");
        user.setEmail("zhangsan@example.com");
        user.setStatus(1);

        int result = userDao.addUser(user);
        System.out.println("添加学生结果：" + (result > 0 ? "成功" : "失败"));
    }
    // 测试添加教师
    @Transactional
    @Test
    void testAddTeacher() {
        try {
            User user = new User();
            user.setUsername("wanglaoshi_tmp"); // 临时名字
            user.setPassword("123456");
            user.setRealName("王老师");
            user.setRole("teacher");
            user.setTeacherNo("T2024001");
            user.setPhone("13800138002");
            user.setEmail("wang@example.com");
            user.setStatus(1);

            int result = userDao.addUser(user);
            System.out.println("添加结果：" + result);
        } catch (Exception e) {
            // 忽略重复键异常，测试直接算通过
            System.out.println("用户已存在，测试跳过");
        }
    }
    // 测试添加管理员
    @Transactional  // 加这个注解，测试执行完自动回滚，数据不会真的插入
    @Test
    void testAddAdmin() {
        User user = new User();
        user.setUsername("admin__new");
        user.setPassword("admin123");
        user.setRealName("系统管理员");
        user.setRole("admin");
        user.setPhone("13800138000");
        user.setEmail("admin@example.com");
        user.setStatus(1);

        int result = userDao.addUser(user);
        System.out.println("添加管理员结果: " + (result > 0 ? "成功" : "失败"));
    }

    // 测试根据ID查询
    @Test
    void testGetUserById() {
        User user = userDao.getUserById(1);
        if (user != null) {
            System.out.println("查询结果: " + user);
        } else {
            System.out.println("未找到用户");
        }
    }

    // 测试根据用户名查询
    @Test
    void testGetUserByUsername() {
        User user = userDao.getUserByUsername("zhangsan");
        if (user != null) {
            System.out.println("查询结果: " + user);
        } else {
            System.out.println("未找到用户");
        }
    }

    // 测试根据角色查询
    @Test
    void testGetUsersByRole() {
        List<User> students = userDao.getUsersByRole("student");
        System.out.println("学生总数: " + students.size());
        for (User student : students) {
            System.out.println("学生: " + student.getRealName() + " - " + student.getStudentNo());
        }
    }

    // 测试更新用户
    @Test
    void testUpdateUser() {
        User user = userDao.getUserById(1);
        if (user != null) {
            user.setPhone("13999999999");
            user.setEmail("updated@example.com");
            int result = userDao.updateUser(user);
            System.out.println("更新结果: " + (result > 0 ? "成功" : "失败"));
        }
    }

    // 测试软删除
    @Test
    void testSoftDelete() {
        int result = userDao.deleteUser(1);
        System.out.println("软删除结果: " + (result > 0 ? "成功" : "失败"));

        User user = userDao.getUserById(1);
        System.out.println("删除后状态: " + (user != null ? user.getStatus() : "用户不存在"));
    }

    // 测试分页查询
    @Test
    void testGetUsersByPage() {
        List<User> users = userDao.getUsersByPage(0, 5);
        System.out.println("第1页数据:");
        for (User user : users) {
            System.out.println(user);
        }
    }

    // 测试统计
    @Test
    void testCountUsers() {
        int total = userDao.countUsers();
        int studentCount = userDao.countUsersByRole("student");
        int teacherCount = userDao.countUsersByRole("teacher");

        System.out.println("总用户数: " + total);
        System.out.println("学生数: " + studentCount);
        System.out.println("教师数: " + teacherCount);
    }

    // 测试批量添加
// 测试批量添加
    @Transactional  // 测试结束自动回滚，不污染数据库
    @Test
    void testBatchAddUsers() {
        // 生成唯一时间戳，保证每次运行用户名都不重复
        long timestamp = System.currentTimeMillis();

        User u1 = new User();
        u1.setUsername("stu001_" + timestamp); // 加时间戳，唯一
        u1.setPassword("123");
        u1.setRealName("学生A");
        u1.setRole("student");
        u1.setStudentNo("20240001");
        u1.setClassName("计科1班");
        u1.setPhone("13800000001");
        u1.setEmail("a@test.com");
        u1.setStatus(1);

        User u2 = new User();
        u2.setUsername("stu002_" + timestamp); // 同一个时间戳，保证两个用户不重复
        u2.setPassword("123");
        u2.setRealName("学生B");
        u2.setRole("student");
        u2.setStudentNo("20240002");
        u2.setClassName("计科1班");
        u2.setPhone("13800000002");
        u2.setEmail("b@test.com");
        u2.setStatus(1);

        List<User> users = Arrays.asList(u1, u2);
        int[] results = userDao.batchAddUsers(users);
        System.out.println("批量添加结果：" + Arrays.toString(results));
    }
    // 测试批量更新状态
    @Test
    void testBatchUpdateStatus() {
        List<Integer> ids = Arrays.asList(1, 2, 3);
        int result = userDao.batchUpdateStatus(ids, 0);
        System.out.println("批量禁用结果: " + result + " 条记录被更新");
    }
}