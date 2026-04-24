package com.rongdai.training.entity;

import java.util.Date;

public class StudyRecord {
    private Integer recordId;
    private Integer userId;
    private Integer courseId;
    private Integer progress;
    private Integer isFinished;
    private Date finishTime;

    public StudyRecord() {}

    // Getter & Setter
    public Integer getRecordId() { return recordId; }
    public void setRecordId(Integer recordId) { this.recordId = recordId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }
    public Integer getProgress() { return progress; }
    public void setProgress(Integer progress) { this.progress = progress; }
    public Integer getIsFinished() { return isFinished; }
    public void setIsFinished(Integer isFinished) { this.isFinished = isFinished; }
    public Date getFinishTime() { return finishTime; }
    public void setFinishTime(Date finishTime) { this.finishTime = finishTime; }
}