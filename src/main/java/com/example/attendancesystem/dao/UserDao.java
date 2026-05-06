package com.example.attendancesystem.dao;

import com.example.attendancesystem.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

// 删除 @Repository 和 @Autowired
// interface 不需要这些注解
@Repository
public interface UserDao {

    // 添加用户
    int addUser(User user);

    // 根据ID删除用户（软删除，修改status为0）
    int deleteUser(Integer id);

    // 物理删除用户（从数据库彻底删除）
    int deleteUserPhysical(Integer id);

    // 更新用户信息
    int updateUser(User user);

    // ========== 查询 ==========

    // 根据ID查询用户
    User getUserById(Integer id);

    // 根据用户名查询用户
    User getUserByUsername(String username);

    // 根据学号查询（学生）
    User getUserByStudentNo(String studentNo);

    // 根据工号查询（教师）
    User getUserByTeacherNo(String teacherNo);

    // 根据角色查询用户列表
    List<User> getUsersByRole(String role);

    // 根据状态查询用户列表
    List<User> getUsersByStatus(Integer status);

    // 查询所有用户
    List<User> getAllUsers();

    // 分页查询用户
    List<User> getUsersByPage(int offset, int pageSize);

    // ========== 统计 ==========

    // 统计用户总数
    int countUsers();

    // 按角色统计人数
    int countUsersByRole(String role);

    // ========== 批量操作 ==========

    // 批量添加用户
    int[] batchAddUsers(List<User> users);

    // 批量更新状态
    int batchUpdateStatus(List<Integer> ids, Integer status);

    User findByName(String username);

}