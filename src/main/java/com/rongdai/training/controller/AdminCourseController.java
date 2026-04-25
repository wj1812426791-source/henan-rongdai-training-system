package com.rongdai.training.controller;

import com.rongdai.training.mapper.CourseMapper;
import com.rongdai.training.mapper.DeptMapper;
import com.rongdai.training.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminCourseController {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DeptMapper deptMapper;

    @GetMapping("/index")
    public String adminIndex(Model model, HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("totalUsers", userMapper.countAllUsers());
        model.addAttribute("totalDepts", deptMapper.countAllDepts());
        model.addAttribute("totalCourses", courseMapper.countAllCourses());
        model.addAttribute("pendingCourseAudit", courseMapper.countPendingCourseAudits());
        return "admin/index";
    }

    @GetMapping("/audit")
    public String auditPage(Model model) {
        model.addAttribute("pendingList", courseMapper.findPendingCourses());
        return "admin/course_audit";
    }

    @PostMapping("/api/audit")
    @ResponseBody
    public Map<String, Object> doAudit(@RequestParam Integer courseId, @RequestParam Integer status) {
        courseMapper.updateAuditStatus(courseId, status);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }
}
