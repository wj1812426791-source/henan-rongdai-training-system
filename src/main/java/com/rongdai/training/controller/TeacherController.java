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
    public String managePage(Model model) {
        List<Course> courses = courseMapper.findAllActiveCourses();
        model.addAttribute("courses", courses);
        return "teacher/course_manage";
    }

    @PostMapping("/doUpload")
    public String doUpload(@RequestParam(required = false) Integer courseId,
                           @RequestParam("courseName") String courseName,
                           @RequestParam("category") String category,
                           @RequestParam("credit") Integer credit,
                           @RequestParam(value = "videoFile", required = false) MultipartFile file,
                           HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
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