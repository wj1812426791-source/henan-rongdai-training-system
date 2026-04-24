package com.rongdai.training.service;

import com.rongdai.training.entity.Course;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    // 处理文件上传并保存课程数据
    void uploadCourse(String courseName, String category, Integer credit, MultipartFile file) throws Exception;
}