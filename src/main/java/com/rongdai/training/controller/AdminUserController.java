package com.rongdai.training.controller;

import com.rongdai.training.entity.User;
import com.rongdai.training.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    // 教师管理入口
    @GetMapping("/teachers")
    public String teacherList(Model model) {
        model.addAttribute("users", adminUserService.getUsersByRole("teacher"));
        model.addAttribute("roleType", "teacher");
        model.addAttribute("title", "教师账户管理");
        return "admin/user_manage";
    }

    // 学生管理入口
    @GetMapping("/students")
    public String studentList(Model model) {
        model.addAttribute("users", adminUserService.getUsersByRole("student"));
        model.addAttribute("roleType", "student");
        model.addAttribute("title", "学员账户管理");
        return "admin/user_manage";
    }

    // 物理删除用户
    @PostMapping("/user/delete/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable Integer id) {
        adminUserService.removeUser(id);
        return "success";
    }

    // 新增教师
    @PostMapping("/user/add")
    public String saveUser(User user) {
        adminUserService.addUser(user);
        // 根据角色跳回对应列表
        return "teacher".equals(user.getRole()) ? "redirect:/admin/teachers" : "redirect:/admin/students";
    }
}