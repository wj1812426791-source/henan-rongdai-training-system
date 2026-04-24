package com.rongdai.training.service;

import com.rongdai.training.entity.Course;
import java.util.List;
import java.util.Map;

public interface CourseService {
    List<Course> getActiveCourses();
    List<Map<String, Object>> getCoursesForStudent(Integer userId);
}