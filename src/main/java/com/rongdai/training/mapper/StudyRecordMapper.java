package com.rongdai.training.mapper;

import com.rongdai.training.entity.StudyRecord;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StudyRecordMapper {
    // 1. 查询是否已经有学习记录
    @Select("SELECT * FROM StudyRecords WHERE userId = #{userId} AND courseId = #{courseId}")
    StudyRecord findRecord(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    // 2. 插入新记录
    @Insert("INSERT INTO StudyRecords (userId, courseId, progress, isFinished) " +
            "VALUES (#{userId}, #{courseId}, #{progress}, #{isFinished})")
    void insertRecord(StudyRecord record);

    // 3. 更新现有记录 (如果看完，则把 isFinished 置为 1，并更新时间)
    @Update("UPDATE StudyRecords SET progress = #{progress}, isFinished = #{isFinished}, finishTime = GETDATE() " +
            "WHERE recordId = #{recordId}")
    void updateRecord(StudyRecord record);
}