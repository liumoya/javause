package com.example.attendancesystem.service;
import com.example.attendancesystem.entity.User;

import java.util.List;

public interface UserService {
    // 创建用户
    String createUser(User user);

    // 根据ID查询
    User getUserById(Long id);

    // 登录验证
    User login(String username, String password);

    // 查询所有教师
    List<User> getAllTeachers();

    // 更新用户
    String updateUser(User user);

    // 删除用户
    String deleteUser(Long id);
}
