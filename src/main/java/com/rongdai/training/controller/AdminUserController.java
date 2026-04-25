package com.rongdai.training.controller;

import com.rongdai.training.entity.User;
import com.rongdai.training.mapper.UserMapper;
import com.rongdai.training.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private UserMapper userMapper;

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
        model.addAttribute("students", userMapper.findAllStudentsWithDept());
        model.addAttribute("departments", userMapper.findAllDepts());
        model.addAttribute("roleType", "student");
        model.addAttribute("title", "学员账户管理");
        return "admin/student_manage";
    }

    // 更新学员部门
    @PostMapping("/student/updateDept")
    @ResponseBody
    public String updateDept(@RequestParam Integer userId, @RequestParam Integer deptId) {
        userMapper.updateStudentDept(userId, deptId);
        return "success";
    }

    // 保存修改（基础信息+部门）
    @PostMapping("/student/update")
    @ResponseBody
    public Map<String, Object> updateStudent(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            userMapper.updateStudent(user);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // 删除学员
    @PostMapping("/student/delete/{id}")
    @ResponseBody
    public Map<String, Object> deleteStudent(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            userMapper.deleteStudent(id);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
        }
        return result;
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

    // 修改用户
    @PostMapping("/user/update")
    public String updateUser(User user) {
        adminUserService.updateUser(user);
        return "teacher".equals(user.getRole()) ? "redirect:/admin/teachers" : "redirect:/admin/students";
    }
}