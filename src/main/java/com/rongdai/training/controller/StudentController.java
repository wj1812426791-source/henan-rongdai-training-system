package com.rongdai.training.controller;

import com.rongdai.training.entity.ExamQuestions;
import com.rongdai.training.entity.User;
import com.rongdai.training.mapper.CourseMapper;
import com.rongdai.training.mapper.ExamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ExamMapper examMapper;

    @GetMapping("/public-courses")
    public String publicCourses(Model model) {
        model.addAttribute("courses", courseMapper.findPublicCoursesMap());
        model.addAttribute("categories", courseMapper.findAllCategories());
        model.addAttribute("courseType", "public");
        return "student/course_card_list";
    }

    @GetMapping("/dept-courses")
    public String deptCourses(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("courses", courseMapper.findDeptCourses(user.getDeptId()));
        model.addAttribute("categories", courseMapper.findAllCategories());
        model.addAttribute("courseType", "dept");
        return "student/course_card_list";
    }

    @GetMapping("/play")
    public String playVideo(@RequestParam Integer courseId, @RequestParam String type, Model model) {
        Map<String, Object> course = courseMapper.findCourseDetailById(courseId);
        model.addAttribute("course", course);
        model.addAttribute("courseType", type);
        return "student/video-play";
    }

    @GetMapping("/index")
    public String studentIndex(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        Integer currentCredit = courseMapper.getUserTotalCredit(user.getUserId());
        Integer standard = 100;
        model.addAttribute("currentCredit", currentCredit != null ? currentCredit : 0);
        model.addAttribute("standard", standard);
        return "student/index";
    }

    @GetMapping("/exam/center")
    public String examCenter(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Map<String, Object> status = courseMapper.getTrainingStatus(user.getUserId());
        int passScore = 100;

        model.addAttribute("status", status);
        model.addAttribute("passScore", passScore);

        if (status != null && status.get("examStatus") != null
                && Integer.valueOf(1).equals(status.get("examStatus"))) {
            Integer examId = (Integer) status.get("examId");
            if (examId != null) {
                List<Map<String, Object>> examDetails = examMapper.getExamWithQuestions(examId);
                model.addAttribute("examDetails", examDetails);
            }
        }

        return "student/exam_center";
    }

    @PostMapping("/exam/submit")
    @ResponseBody
    public Map<String, Object> submitExam(@RequestBody List<Map<String, String>> userAnswers, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.put("success", false);
            result.put("message", "请先登录");
            return result;
        }

        int score = 0;

        List<ExamQuestions> correctAnswers = examMapper.getAnswersByUserId(user.getUserId());

        for (Map<String, String> ans : userAnswers) {
            String qId = ans.get("questionId");
            String userA = ans.get("answer");

            for (ExamQuestions correct : correctAnswers) {
                if (correct.getQuestionId().toString().equals(qId)) {
                    if (correct.getAnswer().equals(userA)) {
                        score += correct.getScore() != null ? correct.getScore() : 10;
                    }
                    break;
                }
            }
        }

        courseMapper.updateExamResult(user.getUserId(), score, 2);

        result.put("success", true);
        result.put("score", score);
        return result;
    }
}
