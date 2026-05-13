package com.example.attendancesystem.controller;

import com.example.attendancesystem.dao.UserDao;
import com.example.attendancesystem.entity.User;
import com.example.attendancesystem.utils.BCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @Autowired
    private UserDao userDao;

    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String loginPage(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String success,
            Model model) {

        if (error != null) {
            model.addAttribute("error", true);
            model.addAttribute("errorMsg", "用户名或密码错误");
        }

        if (success != null) {
            model.addAttribute("success", true);
            model.addAttribute("successMsg", "注册成功，请登录");
        }

        model.addAttribute("title", "用户登录");
        return "login";
    }

    /**
     * 注册页面
     */
    @GetMapping("/register")
    public String registerPage(Model model) {
        return "register";
    }

    /**
     * 处理注册请求
     */
    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String realName,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam(defaultValue = "STUDENT") String role,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            Model model) {

        // 1. 检查两次密码是否一致
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", true);
            model.addAttribute("errorMsg", "两次输入的密码不一致");
            model.addAttribute("username", username);
            model.addAttribute("realName", realName);
            model.addAttribute("phone", phone);
            model.addAttribute("email", email);
            return "register";
        }

        // 2. 检查用户名是否已存在
        User existingUser = userDao.findByName(username);
        if (existingUser != null) {
            model.addAttribute("error", true);
            model.addAttribute("errorMsg", "用户名已存在");
            model.addAttribute("username", username);
            model.addAttribute("realName", realName);
            model.addAttribute("phone", phone);
            model.addAttribute("email", email);
            return "register";
        }

        // 3. 创建新用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(BCryptUtil.encode(password));
        user.setRealName(realName);
        user.setRole(role);
        user.setPhone(phone);
        user.setEmail(email);
        user.setStatus(1);

        // 4. 保存用户
        int result = userDao.addUser(user);

        if (result > 0) {
            // 注册成功，跳转到登录页
            return "redirect:/login?success=true";
        } else {
            model.addAttribute("error", true);
            model.addAttribute("errorMsg", "注册失败，请重试");
            return "register";
        }
    }

    /**
     * 首页
     */
    @GetMapping({"/", "/index"})
    public String index(Model model) {
        // 获取当前登录用户
        String username = getCurrentUsername();
        model.addAttribute("username", username);
        return "index";
    }

    /**
     * 获取当前登录用户名
     */
    private String getCurrentUsername() {
        try {
            org.springframework.security.core.userdetails.UserDetails user =
                    (org.springframework.security.core.userdetails.UserDetails)
                            org.springframework.security.core.context.SecurityContextHolder.getContext()
                                    .getAuthentication().getPrincipal();
            return user.getUsername();
        } catch (Exception e) {
            return "游客";
        }
    }
}