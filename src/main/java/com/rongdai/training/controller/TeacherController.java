package com.rongdai.training.controller;

import com.rongdai.training.entity.Course;
import com.rongdai.training.entity.User;
import com.rongdai.training.mapper.CourseMapper;
import com.rongdai.training.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private CourseMapper courseMapper;

    @GetMapping("/upload")
    public String uploadPage(@RequestParam(required = false) Integer editId, Model model) {
        List<Map<String, Object>> categories = courseMapper.findAllCategories();
        model.addAttribute("categories", categories);

        if (editId != null) {
            Course oldCourse = courseMapper.findById(editId);
            model.addAttribute("course", oldCourse);
            model.addAttribute("isEdit", true);
        } else {
            model.addAttribute("course", new Course());
            model.addAttribute("isEdit", false);
        }
        return "teacher/course-upload";
    }

    @GetMapping("/manage")
    public String managePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("courses", courseMapper.findCoursesByTeacher(user.getUserId()));
        model.addAttribute("departments", courseMapper.findAllDepts());
        return "teacher/course_manage";
    }

    @PostMapping("/dispatch")
    @ResponseBody
    public Map<String, Object> dispatchCourse(@RequestParam Integer courseId,
                                              @RequestParam Integer deptId,
                                              @RequestParam String deadline) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (courseMapper.checkPlanExistence(courseId, deptId) > 0) {
                result.put("success", false);
                result.put("msg", "该部门已存在此课程的学习计划");
                return result;
            }
            courseMapper.dispatchCourseToDept(courseId, deptId, deadline);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", "下发失败：" + e.getMessage());
        }
        return result;
    }

    @PostMapping("/doUpload")
    public String doUpload(@RequestParam(required = false) Integer courseId,
                           @RequestParam("courseName") String courseName,
                           @RequestParam("category") String category,
                           @RequestParam("credit") Integer credit,
                           @RequestParam(value = "videoFile", required = false) MultipartFile file,
                           HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            System.out.println("上传失败：用户未登录或 Session 已失效");
            return "redirect:/login";
        }

        try {
            if (courseId != null) {
                Course course = new Course();
                course.setCourseId(courseId);
                course.setCourseName(courseName);
                course.setCategory(category);
                course.setCredit(credit);
                course.setAuditStatus(0);
                courseMapper.updateCourse(course);
            } else {
                uploadService.uploadCourse(courseName, category, credit, file, user.getUserId());
            }
            return "redirect:/teacher/manage";
        } catch (Exception e) {
            e.printStackTrace();
            return "error/500";
        }
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public Map<String, Object> deleteCourse(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            uploadService.deleteCourse(id);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
        }
        return result;
    }
}