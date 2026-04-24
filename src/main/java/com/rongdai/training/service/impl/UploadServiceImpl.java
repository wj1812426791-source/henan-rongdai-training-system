package com.rongdai.training.service.impl;

import com.rongdai.training.entity.Course;
import com.rongdai.training.mapper.CourseMapper;
import com.rongdai.training.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private CourseMapper courseMapper;

    private final String UPLOAD_DIR = "D:/training/uploads/";

    @Override
    public void uploadCourse(String courseName, String category, Integer credit, MultipartFile file, Integer teacherId) throws Exception {
        // 1. 文件名处理
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + suffix;

        // 2. 保存到磁盘
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();
        file.transferTo(new File(UPLOAD_DIR + newFilename));

        // 3. 数据入库
        Course course = new Course();
        course.setCourseName(courseName);
        course.setCategory(category);
        course.setCredit(credit);
        course.setVideoPath(newFilename);
        course.setTeacherId(teacherId);
        course.setAuditStatus(0);

        courseMapper.insertCourse(course);
    }

    @Override
    public void deleteCourse(Integer courseId) {
        Course course = courseMapper.getCourseById(courseId);
        if (course != null) {
            String filePath = "D:/training/uploads/" + course.getVideoPath();
            java.io.File file = new java.io.File(filePath);
            if (file.exists()) {
                file.delete();
            }
            courseMapper.deleteCourse(courseId);
        }
    }
}