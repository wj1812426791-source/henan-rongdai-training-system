package com.rongdai.training.service;

import com.rongdai.training.entity.Course;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    void uploadCourse(String courseName, String category, Integer credit, MultipartFile file) throws Exception;
    void deleteCourse(Integer courseId);
}