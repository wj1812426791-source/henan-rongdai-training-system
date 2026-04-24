package com.rongdai.training.service.impl;

import com.rongdai.training.entity.Course;
import com.rongdai.training.mapper.CourseMapper;
import com.rongdai.training.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> getActiveCourses() {
        return courseMapper.findAllActiveCourses();
    }

    @Override
    public List<Map<String, Object>> getCoursesForStudent(Integer userId) {
        return courseMapper.findCoursesWithStatus(userId);
    }
}