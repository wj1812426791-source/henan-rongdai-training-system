package com.rongdai.training.controller;

import com.rongdai.training.entity.Course;
import com.rongdai.training.entity.User;
import com.rongdai.training.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private CourseMapper courseMapper;

    @GetMapping("/public-courses")
    public String publicCourses(Model model) {
        List<Course> courses = courseMapper.findPublicCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("courseType", "public");
        return "student/course-list";
    }

    @GetMapping("/dept-courses")
    public String deptCourses(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<Map<String, Object>> courses = courseMapper.findDeptCourses(user.getDeptId());
        model.addAttribute("courses", courses);
        model.addAttribute("courseType", "dept");
        return "student/course-list";
    }

    @GetMapping("/play")
    public String playVideo(@RequestParam Integer courseId, @RequestParam String type, Model model) {
        Map<String, Object> course = courseMapper.findCourseDetailById(courseId);
        model.addAttribute("course", course);
        model.addAttribute("courseType", type);
        return "student/video-play";
    }
}
