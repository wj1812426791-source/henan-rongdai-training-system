package com.rongdai.training.controller;

import com.rongdai.training.entity.Course;
import com.rongdai.training.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/student/courses")
    public String showCourseList(Model model) {
        Integer currentUserId = 2;
        List<Map<String, Object>> courses = courseService.getCoursesForStudent(currentUserId);
        model.addAttribute("courses", courses);
        return "student/course-list";
    }
}