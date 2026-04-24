package com.rongdai.training.controller;

import com.rongdai.training.entity.User;
import com.rongdai.training.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("depts", authService.getAllDepartments());
        return "auth/register";
    }

    @PostMapping("/doRegister")
    public String doRegister(User user, Model model) {
        if (authService.register(user)) {
            return "redirect:/login?msg=success";
        }
        model.addAttribute("error", "该账号已被占用");
        model.addAttribute("depts", authService.getAllDepartments());
        return "auth/register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/doLogin")
    public String doLogin(String username, String password, HttpSession session, Model model) {
        User user = authService.login(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            if ("admin".equals(user.getRole())) return "redirect:/admin/teachers";
            if ("teacher".equals(user.getRole())) return "redirect:/teacher/manage";
            return "redirect:/student/courses";
        }
        model.addAttribute("error", "用户名或密码错误");
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
