package com.rongdai.training.service.impl;

import com.rongdai.training.entity.Course;
import com.rongdai.training.entity.StudyRecord;
import com.rongdai.training.mapper.CourseMapper;
import com.rongdai.training.mapper.StudyRecordMapper;
import com.rongdai.training.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LearningServiceImpl implements LearningService {

    @Autowired
    private CourseMapper courseMapper;
    
    @Autowired
    private StudyRecordMapper studyRecordMapper;

    @Override
    public Course getCourseDetails(Integer courseId) {
        return courseMapper.getCourseById(courseId);
    }

    @Override
    public boolean saveVideoProgress(Integer userId, Integer courseId, Integer progress, Integer isFinished) {
        // 1. 去数据库查有没有这条记录
        StudyRecord record = studyRecordMapper.findRecord(userId, courseId);
        
        if (record == null) {
            // 2. 如果没有，就新建一条
            record = new StudyRecord();
            record.setUserId(userId);
            record.setCourseId(courseId);
            record.setProgress(progress);
            record.setIsFinished(isFinished);
            studyRecordMapper.insertRecord(record);
            return isFinished == 1; // 如果一上来就看完了(比如拉进度条)，返回 true
        } else {
            // 3. 如果已经有了，判断是不是之前就已经看完了
            if (record.getIsFinished() == 1) {
                return false; // 以前就拿过学分了，不再重复奖励
            }
            
            // 4. 更新进度
            record.setProgress(progress);
            if (isFinished == 1) {
                record.setIsFinished(1);
            }
            studyRecordMapper.updateRecord(record);
            return isFinished == 1; // 告诉前端：恭喜，刚刚完成！
        }
    }
}