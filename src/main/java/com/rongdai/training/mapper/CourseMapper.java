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

    // 10. 查询教师名下的所有课程（不加状态限制）
    @Select("SELECT * FROM Courses WHERE teacherId = #{tid}")
    List<Course> findCoursesByTeacher(@Param("tid") Integer tid);

    // 11. 查询所有分类
    @Select("SELECT * FROM CourseCategories")
    List<Map<String, Object>> findAllCategories();

    // 12. 添加分类
    @Insert("INSERT INTO CourseCategories (categoryName) VALUES (#{name})")
    void insertCategory(@Param("name") String name);

    // 13. 删除分类
    @Delete("DELETE FROM CourseCategories WHERE categoryId = #{id}")
    void deleteCategory(Integer id);

    // 14. 更新分类名称
    @Update("UPDATE CourseCategories SET categoryName = #{name} WHERE categoryId = #{id}")
    void updateCategory(@Param("id") Integer id, @Param("name") String name);

    // 15. 全员公开课：查所有审核通过的
    @Select("SELECT * FROM Courses WHERE auditStatus = 1")
    List<Course> findPublicCourses();

    // 15-2. 全员公开课返回Map格式
    @Select("SELECT * FROM Courses WHERE auditStatus = 1")
    List<Map<String, Object>> findPublicCoursesMap();

    // 16. 部门必修课：联查 LearningPlans
    @Select("SELECT c.*, lp.deadline " +
            "FROM Courses c " +
            "INNER JOIN LearningPlans lp ON c.courseId = lp.courseId " +
            "WHERE c.auditStatus = 1 AND lp.deptId = #{deptId}")
    List<Map<String, Object>> findDeptCourses(Integer deptId);

    // 17. 查询课程详情
    @Select("SELECT * FROM Courses WHERE courseId = #{courseId}")
    Map<String, Object> findCourseDetailById(Integer courseId);

    // 18. 下发课程到部门
    @Insert("INSERT INTO LearningPlans (courseId, deptId, deadline) VALUES (#{courseId}, #{deptId}, #{deadline})")
    void dispatchCourseToDept(@Param("courseId") Integer courseId,
                              @Param("deptId") Integer deptId,
                              @Param("deadline") String deadline);

    // 19. 检查该部门是否已经有了这个课的计划
    @Select("SELECT COUNT(*) FROM LearningPlans WHERE courseId = #{courseId} AND deptId = #{deptId}")
    int checkPlanExistence(@Param("courseId") Integer courseId, @Param("deptId") Integer deptId);

    // 20. 获取所有部门
    @Select("SELECT * FROM Departments")
    List<Map<String, Object>> findAllDepts();

    // 21. 查询该教师名下所有已审核通过的课程
    @Select("SELECT * FROM Courses WHERE teacherId = #{teacherId} AND auditStatus = 1")
    List<Course> findPublishedCoursesByTeacher(Integer teacherId);

    // 22. 查出该教师下发的所有计划，并关联课程名和部门名
    @Select("SELECT lp.*, c.courseName, d.deptName FROM LearningPlans lp " +
            "JOIN Courses c ON lp.courseId = c.courseId " +
            "JOIN Departments d ON lp.deptId = d.deptId " +
            "WHERE c.teacherId = #{teacherId}")
    List<Map<String, Object>> findAllLearningPlans(Integer teacherId);

    // 23. 查询学分达标且未审核的学员
    @Select("SELECT u.userId, u.realName, d.deptName, SUM(c.credit) as currentCredit " +
            "FROM Users u " +
            "JOIN Departments d ON u.deptId = d.deptId " +
            "JOIN StudyRecords sr ON u.userId = sr.userId " +
            "JOIN Courses c ON sr.courseId = c.courseId " +
            "WHERE sr.isFinished = 1 " +
            "AND u.userId NOT IN (SELECT userId FROM TrainingStatus WHERE status = 1) " +
            "GROUP BY u.userId, u.realName, d.deptName " +
            "HAVING SUM(c.credit) >= #{standard}")
    List<Map<String, Object>> findQualifiedStudents(Integer standard);

    // 24. 保存审核通过记录
    @Insert("INSERT INTO TrainingStatus (userId, finalCredit, status, auditTime) " +
            "VALUES (#{userId}, #{credit}, 1, GETDATE())")
    void saveTrainingStatus(@Param("userId") Integer userId, @Param("credit") Integer credit);

    // 25. 获取用户累计学分
    @Select("SELECT ISNULL(SUM(c.credit), 0) FROM StudyRecords sr " +
            "JOIN Courses c ON sr.courseId = c.courseId " +
            "WHERE sr.userId = #{userId} AND sr.isFinished = 1")
    Integer getUserTotalCredit(Integer userId);

    // 26. 统计教师名下的课程数
    @Select("SELECT COUNT(*) FROM Courses WHERE teacherId = #{teacherId}")
    int countCoursesByTeacher(Integer teacherId);

    // 27. 统计待审核人数（基于学分标准）
    @Select("SELECT COUNT(*) FROM (" +
            "  SELECT u.userId FROM Users u " +
            "  JOIN StudyRecords sr ON u.userId = sr.userId " +
            "  JOIN Courses c ON sr.courseId = c.courseId " +
            "  WHERE sr.isFinished = 1 " +
            "  AND u.userId NOT IN (SELECT userId FROM TrainingStatus WHERE status = 1) " +
            "  GROUP BY u.userId " +
            "  HAVING SUM(c.credit) >= #{standard}" +
            ") as t")
    int countPendingAuditsByStandard(Integer standard);

    // 28. 统计已结业人数
    @Select("SELECT COUNT(*) FROM TrainingStatus WHERE status = 1")
    int countPassedStudents();
}