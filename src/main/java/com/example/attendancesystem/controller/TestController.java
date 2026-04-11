package com.example.attendancesystem.controller;
import com.example.attendancesystem.dao.UserDao;
import com.example.attendancesystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private UserDao userDao;
    // ========== 1. 增（Create）==========
    @PostMapping("/add")
    public Map<String, Object> addUser(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            int rows = userDao.addUser(user);
            result.put("success", rows > 0);
            result.put("message", rows > 0 ? "添加成功" : "添加失败");
            if (rows > 0) {
                result.put("data", user);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    // ========== 2. 删（Delete）- 软删除 ==========
    @DeleteMapping("/soft/{id}")
    public Map<String, Object> softDeleteUser(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            int rows = userDao.deleteUser(id);
            result.put("success", rows > 0);
            result.put("message", rows > 0 ? "软删除成功" : "用户不存在");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    // ========== 3. 删（Delete）- 物理删除 ==========
    @DeleteMapping("/physical/{id}")
    public Map<String, Object> physicalDeleteUser(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            int rows = userDao.deleteUserPhysical(id);
            result.put("success", rows > 0);
            result.put("message", rows > 0 ? "物理删除成功" : "用户不存在");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    // ========== 4. 改（Update）==========
    @PutMapping("/update/{id}")
    public Map<String, Object> updateUser(@PathVariable Integer id, @RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            user.setId(id);
            int rows = userDao.updateUser(user);
            result.put("success", rows > 0);
            result.put("message", rows > 0 ? "更新成功" : "更新失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    // ========== 5. 查（Read）- 根据ID查询 ==========
    @GetMapping("/id/{id}")
    public Map<String, Object> getUserById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        User user = userDao.getUserById(id);
        if (user != null) {
            result.put("success", true);
            result.put("data", user);
        } else {
            result.put("success", false);
            result.put("message", "用户不存在");
        }
        return result;
    }

    // ========== 6. 查（Read）- 根据用户名查询 ==========
    @GetMapping("/username/{username}")
    public Map<String, Object> getUserByUsername(@PathVariable String username) {
        Map<String, Object> result = new HashMap<>();
        User user = userDao.getUserByUsername(username);
        if (user != null) {
            result.put("success", true);
            result.put("data", user);
        } else {
            result.put("success", false);
            result.put("message", "用户不存在");
        }
        return result;
    }

    // ========== 7. 查（Read）- 查询所有用户 ==========
    @GetMapping("/all")
    public Map<String, Object> getAllUsers() {
        Map<String, Object> result = new HashMap<>();
        List<User> users = userDao.getAllUsers();
        result.put("success", true);
        result.put("data", users);
        result.put("count", users.size());
        return result;
    }

    // ========== 8. 查（Read）- 根据角色查询 ==========
    @GetMapping("/role/{role}")
    public Map<String, Object> getUsersByRole(@PathVariable String role) {
        Map<String, Object> result = new HashMap<>();
        List<User> users = userDao.getUsersByRole(role);
        result.put("success", true);
        result.put("data", users);
        result.put("count", users.size());
        return result;
    }

    // ========== 9. 查（Read）- 分页查询 ==========
    @GetMapping("/page")
    public Map<String, Object> getUsersByPage(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> result = new HashMap<>();
        int offset = page * size;
        List<User> users = userDao.getUsersByPage(offset, size);
        int total = userDao.countUsers();
        result.put("success", true);
        result.put("data", users);
        result.put("page", page);
        result.put("size", size);
        result.put("total", total);
        return result;
    }

    // ========== 10. 统计 - 总人数 ==========
    @GetMapping("/count")
    public Map<String, Object> getCount() {
        Map<String, Object> result = new HashMap<>();
        result.put("total", userDao.countUsers());
        result.put("students", userDao.countUsersByRole("student"));
        result.put("teachers", userDao.countUsersByRole("teacher"));
        result.put("admin", userDao.countUsersByRole("admin"));
        return result;
    }

    // ========== 11. 批量添加 ==========
    @PostMapping("/batch")
    public Map<String, Object> batchAddUsers(@RequestBody List<User> users) {
        Map<String, Object> result = new HashMap<>();
        try {
            int[] rows = userDao.batchAddUsers(users);
            result.put("success", true);
            result.put("message", "成功添加 " + rows.length + " 条数据");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    // ========== 12. 批量更新状态 ==========
    @PutMapping("/batch/status")
    public Map<String, Object> batchUpdateStatus(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            @SuppressWarnings("unchecked")
            List<Integer> ids = (List<Integer>) params.get("ids");
            Integer status = (Integer) params.get("status");
            int rows = userDao.batchUpdateStatus(ids, status);
            result.put("success", true);
            result.put("message", "成功更新 " + rows + " 条数据");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    // ========== 登录验证接口 ==========
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginInfo) {
        Map<String, Object> result = new HashMap<>();
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");

        // 参数校验
        if (username == null || username.isEmpty()) {
            result.put("success", false);
            result.put("message", "用户名不能为空");
            return result;
        }
        if (password == null || password.isEmpty()) {
            result.put("success", false);
            result.put("message", "密码不能为空");
            return result;
        }

        // 根据用户名查询用户
        User user = userDao.getUserByUsername(username);

        // 验证用户是否存在
        if (user == null) {
            result.put("success", false);
            result.put("message", "用户不存在");
            return result;
        }

        // 验证密码（这里用明文比较，实际项目中应该用加密）
        if (!password.equals(user.getPassword())) {
            result.put("success", false);
            result.put("message", "密码错误");
            return result;
        }

        // 验证账户状态
        if (user.getStatus() == null || user.getStatus() != 1) {
            result.put("success", false);
            result.put("message", "账户已被禁用，请联系管理员");
            return result;
        }

        // 登录成功，返回用户信息（不返回密码）
        user.setPassword(null);  // 清除密码，不返回给前端
        result.put("success", true);
        result.put("message", "登录成功");
        result.put("data", user);
        return result;
    }

}
