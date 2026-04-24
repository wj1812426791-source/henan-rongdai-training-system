package com.rongdai.training.controller;

import com.rongdai.training.entity.Course;
import com.rongdai.training.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/student/courses")
    public String showCourseList(Model model) {
        // 通过 Service 层获取数据
        List<Course> courses = courseService.getActiveCourses();
        model.addAttribute("courses", courses);
        return "student/course-list";
    }
}