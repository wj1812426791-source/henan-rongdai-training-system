package com.rongdai.training.entity;

import java.util.List;
import java.util.Date;

public class Exam {
    private Integer examId;
    private String title;
    private Integer totalScore;
    private Integer passScore;
    private Date createTime;
    private Integer creatorId;
    private List<ExamQuestions> questions;

    public Integer getExamId() { return examId; }
    public void setExamId(Integer examId) { this.examId = examId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getTotalScore() { return totalScore; }
    public void setTotalScore(Integer totalScore) { this.totalScore = totalScore; }
    public Integer getPassScore() { return passScore; }
    public void setPassScore(Integer passScore) { this.passScore = passScore; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public Integer getCreatorId() { return creatorId; }
    public void setCreatorId(Integer creatorId) { this.creatorId = creatorId; }
    public List<ExamQuestions> getQuestions() { return questions; }
    public void setQuestions(List<ExamQuestions> questions) { this.questions = questions; }
}
