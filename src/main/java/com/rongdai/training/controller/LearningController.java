package com.rongdai.training.controller;

import com.rongdai.training.entity.Course;
import com.rongdai.training.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class LearningController {

    @Autowired
    private LearningService learningService;

    /**
     * 1. 跳转到视频播放页面
     */
    @GetMapping("/play/{courseId}")
    public String showPlayPage(@PathVariable Integer courseId, Model model) {
        Course course = learningService.getCourseDetails(courseId);
        model.addAttribute("course", course);
        
        // 【模拟登录状态】因为没做登录，我们假设当前登录的学员 ID 是 2 (test_stu)
        model.addAttribute("currentUserId", 2); 
        
        return "student/video-play";
    }

    /**
     * 2. 接收前端 Ajax 传来的进度数据 (API 接口)
     */
    @PostMapping("/api/progress")
    @ResponseBody // 这个注解代表返回 JSON 数据，不跳转页面
    public Map<String, Object> updateProgress(
            @RequestParam Integer userId,
            @RequestParam Integer courseId,
            @RequestParam Integer progress,
            @RequestParam Integer isFinished) {

        // 调用 Service 核心逻辑
        boolean justFinished = learningService.saveVideoProgress(userId, courseId, progress, isFinished);

        // 给前端返回 JSON 结果
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("justFinished", justFinished); // true 代表刚刚看完，可以弹窗恭喜加分
        return result;
    }
}