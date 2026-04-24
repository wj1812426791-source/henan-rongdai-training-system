package com.rongdai.training.service.impl;

import com.rongdai.training.entity.Course;
import com.rongdai.training.mapper.CourseMapper;
import com.rongdai.training.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> getActiveCourses() {
        // 调用 Mapper 查询 auditStatus = 1 的课程
        return courseMapper.findAllActiveCourses();
    }
}