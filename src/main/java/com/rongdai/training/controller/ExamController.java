package com.rongdai.training.controller;

import com.rongdai.training.entity.Exam;
import com.rongdai.training.entity.ExamQuestions;
import com.rongdai.training.entity.User;
import com.rongdai.training.mapper.ExamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher/exam")
public class ExamController {

    @Autowired
    private ExamMapper examMapper;

    @GetMapping("/list")
    public String listPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("exams", examMapper.selectAllExams());
        return "teacher/exam_list";
    }

    @GetMapping("/add")
    public String addPage(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        return "teacher/exam_add";
    }

    @GetMapping("/api/list")
    @ResponseBody
    public Map<String, Object> list(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.put("data", java.util.Collections.emptyList());
            return result;
        }
        List<Exam> exams = examMapper.findExamsByCreator(user.getUserId());
        result.put("data", exams);
        return result;
    }

    @GetMapping("/questions")
    @ResponseBody
    public Map<String, Object> getQuestions(@RequestParam Integer examId) {
        Map<String, Object> result = new HashMap<>();
        List<ExamQuestions> questions = examMapper.findQuestionsByExam(examId);
        result.put("data", questions);
        return result;
    }

    @PostMapping("/save")
    @ResponseBody
    @Transactional
    public Map<String, Object> saveExam(@RequestBody Exam exam, HttpSession session) {
        Map<String, Object> res = new HashMap<>();
        try {
            User user = (User) session.getAttribute("user");
            if (user != null) exam.setCreatorId(user.getUserId());

            examMapper.insertExam(exam);

            if (exam.getQuestions() != null) {
                for (ExamQuestions q : exam.getQuestions()) {
                    q.setExamId(exam.getExamId());
                    examMapper.insertQuestion(q);
                }
            }
            res.put("success", true);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", "发布失败：" + e.getMessage());
        }
        return res;
    }

    @PostMapping("/delete")
    @ResponseBody
    @Transactional
    public Map<String, Object> delete(@RequestParam Integer examId) {
        Map<String, Object> res = new HashMap<>();
        try {
            examMapper.deleteExamQuestions(examId);
            examMapper.deleteExam(examId);
            res.put("success", true);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", "删除失败：" + e.getMessage());
        }
        return res;
    }
}
