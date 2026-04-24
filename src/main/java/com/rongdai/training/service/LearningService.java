package com.rongdai.training.service;
import com.rongdai.training.entity.Course;

public interface LearningService {
    Course getCourseDetails(Integer courseId);
    
    // 保存播放进度，返回 true 表示刚刚看完并获得了学分
    boolean saveVideoProgress(Integer userId, Integer courseId, Integer progress, Integer isFinished);
}