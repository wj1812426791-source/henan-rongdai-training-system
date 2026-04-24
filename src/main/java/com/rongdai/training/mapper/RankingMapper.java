package com.rongdai.training.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface RankingMapper {

    @Select("SELECT " +
            "  u.username AS \"username\", " +
            "  d.deptName AS \"deptName\", " +
            "  COUNT(sr.courseId) AS \"finishedCount\", " +
            "  ISNULL(SUM(c.credit), 0) AS \"totalCredit\", " + // 使用 ISNULL 防止返回 null 导致页面报错
            "  DENSE_RANK() OVER (ORDER BY SUM(c.credit) DESC) AS \"rankNo\" " +
            "FROM Users u " +
            "INNER JOIN Departments d ON u.deptId = d.deptId " +
            "LEFT JOIN StudyRecords sr ON u.userId = sr.userId AND sr.isFinished = 1 " +
            "LEFT JOIN Courses c ON sr.courseId = c.courseId " +
            "GROUP BY u.username, d.deptName " +
            "ORDER BY \"totalCredit\" DESC")
    List<Map<String, Object>> getCreditRanking();
}