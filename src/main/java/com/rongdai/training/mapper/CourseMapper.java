package com.rongdai.training.mapper;

import com.rongdai.training.entity.Course;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface CourseMapper {

    // 1. 查询所有已审核的课程
    @Select("SELECT * FROM Courses WHERE auditStatus = 1")
    List<Course> findAllActiveCourses();

    // 2. 根据ID查询单门课程
    @Select("SELECT * FROM Courses WHERE courseId = #{courseId}")
    Course getCourseById(Integer courseId);

    // 3. 【核心补全】：新增课程方法
    // 这里的字段名必须和你数据库 Courses 表的列名完全一致
    @Insert("INSERT INTO Courses (courseName, videoPath, category, credit, auditStatus) " +
            "VALUES (#{courseName}, #{videoPath}, #{category}, #{credit}, #{auditStatus})")
    void insertCourse(Course course);
}