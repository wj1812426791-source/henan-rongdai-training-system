package com.rongdai.training.controller;

import com.rongdai.training.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminCourseController {

    @Autowired
    private CourseMapper courseMapper;

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
