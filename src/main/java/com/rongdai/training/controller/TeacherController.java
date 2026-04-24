package com.rongdai.training.controller;

import com.rongdai.training.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private UploadService uploadService;

    @GetMapping("/upload")
    public String uploadPage() {
        return "teacher/course-upload";
    }

    @PostMapping("/doUpload")
    public String doUpload(@RequestParam("courseName") String courseName,
                           @RequestParam("category") String category,
                           @RequestParam("credit") Integer credit,
                           @RequestParam("videoFile") MultipartFile file) {
        try {
            uploadService.uploadCourse(courseName, category, credit, file);
            return "redirect:/student/courses"; // 成功后跳转到列表
        } catch (Exception e) {
            e.printStackTrace();
            return "error/500";
        }
    }
}