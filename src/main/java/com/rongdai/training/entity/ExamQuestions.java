package com.rongdai.training.entity;

public class ExamQuestions {
    private Integer questionId;
    private Integer examId;
    private String content;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private Integer score;

    public Integer getQuestionId() { return questionId; }
    public void setQuestionId(Integer questionId) { this.questionId = questionId; }
    public Integer getExamId() { return examId; }
    public void setExamId(Integer examId) { this.examId = examId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getOptionA() { return optionA; }
    public void setOptionA(String optionA) { this.optionA = optionA; }
    public String getOptionB() { return optionB; }
    public void setOptionB(String optionB) { this.optionB = optionB; }
    public String getOptionC() { return optionC; }
    public void setOptionC(String optionC) { this.optionC = optionC; }
    public String getOptionD() { return optionD; }
    public void setOptionD(String optionD) { this.optionD = optionD; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
}
