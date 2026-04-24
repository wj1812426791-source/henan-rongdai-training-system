package com.rongdai.training.mapper;
import org.apache.ibatis.annotations.Update;
import com.rongdai.training.entity.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface CourseMapper {

    // 1. 查询所有已审核的课程
    @Select("SELECT * FROM Courses WHERE auditStatus = 1")
    List<Course> findAllActiveCourses();

    // 2. 根据ID查询单门课程
    @Select("SELECT * FROM Courses WHERE courseId = #{courseId}")
    Course getCourseById(Integer courseId);

    // 3. 【核心补全】：新增课程方法
    @Insert("INSERT INTO Courses (courseName, videoPath, category, teacherId, auditStatus, credit) " +
            "VALUES (#{courseName}, #{videoPath}, #{category}, #{teacherId}, #{auditStatus}, #{credit})")
    void insertCourse(Course course);

    // 4. 查询所有课程，并关联StudyRecords表查看当前用户的学习状态
    @Select("SELECT c.*, ISNULL(sr.isFinished, 0) as isFinished " +
            "FROM Courses c " +
            "LEFT JOIN StudyRecords sr ON c.courseId = sr.courseId AND sr.userId = #{userId} " +
            "WHERE c.auditStatus = 1")
    List<Map<String, Object>> findCoursesWithStatus(@Param("userId") Integer userId);

    // 5. 删除课程
    @Delete("DELETE FROM Courses WHERE courseId = #{courseId}")
    void deleteCourse(Integer courseId);

    // 6. 管理员查询待审核列表（关联教师姓名显示）
    @Select("SELECT c.*, u.realName as teacherName FROM Courses c " +
            "LEFT JOIN Users u ON c.teacherId = u.userId " +
            "WHERE c.auditStatus = 0")
    List<Map<String, Object>> findPendingCourses();

    // 7. 执行审核：更新状态 (1:通过, 2:驳回)
    @Update("UPDATE Courses SET auditStatus = #{status} WHERE courseId = #{courseId}")
    void updateAuditStatus(@Param("courseId") Integer courseId, @Param("status") Integer status);

    // 8. 根据ID查询课程
    @Select("SELECT * FROM Courses WHERE courseId = #{courseId}")
    Course findById(Integer courseId);

    // 9. 更新课程
    @Update("UPDATE Courses SET courseName=#{courseName}, videoPath=#{videoPath}, " +
            "category=#{category}, auditStatus=0, credit=#{credit} " +
            "WHERE courseId=#{courseId}")
    void updateCourse(Course course);
}