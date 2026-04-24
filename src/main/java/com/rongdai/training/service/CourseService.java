package com.rongdai.training.service;

import com.rongdai.training.entity.Course;
import java.util.List;

public interface CourseService {
    List<Course> getActiveCourses();
}