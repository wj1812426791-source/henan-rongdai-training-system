package com.rongdai.training.controller;

import com.rongdai.training.entity.Course;
import com.rongdai.training.mapper.CourseMapper;
import com.rongdai.training.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public String uploadPage() {
        return "teacher/course-upload";
    }

    @GetMapping("/manage")
    public String managePage(Model model) {
        List<Course> courses = courseMapper.findAllActiveCourses();
        model.addAttribute("courses", courses);
        return "teacher/course_manage";
    }

    @PostMapping("/doUpload")
    public String doUpload(@RequestParam("courseName") String courseName,
                           @RequestParam("category") String category,
                           @RequestParam("credit") Integer credit,
                           @RequestParam("videoFile") MultipartFile file) {
        try {
            uploadService.uploadCourse(courseName, category, credit, file);
            return "redirect:/student/courses";
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