package com.rongdai.training.mapper;

import com.rongdai.training.entity.Exam;
import com.rongdai.training.entity.ExamQuestions;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExamMapper {

    @Insert("INSERT INTO Exam (title, creatorId) VALUES (#{title}, #{creatorId})")
    @Options(useGeneratedKeys = true, keyProperty = "examId")
    void insertExam(Exam exam);

    @Insert("INSERT INTO ExamQuestions (examId, content, optionA, optionB, optionC, optionD, answer, score) " +
            "VALUES (#{examId}, #{content}, #{optionA}, #{optionB}, #{optionC}, #{optionD}, #{answer}, #{score})")
    void insertQuestion(ExamQuestions question);

    @Select("SELECT * FROM Exam WHERE creatorId = #{creatorId} ORDER BY createTime DESC")
    List<Exam> findExamsByCreator(Integer creatorId);

    @Select("SELECT * FROM ExamQuestions WHERE examId = #{examId}")
    List<ExamQuestions> findQuestionsByExam(Integer examId);

    @Select("SELECT * FROM Exam ORDER BY createTime DESC")
    List<Exam> findAllExams();

    @Select("SELECT e.*, (SELECT COUNT(*) FROM ExamQuestions q WHERE q.examId = e.examId) as qCount " +
            "FROM Exam e ORDER BY e.createTime DESC")
    List<Map<String, Object>> selectAllExams();

    @Select("SELECT * FROM Exam WHERE examId = #{examId}")
    Map<String, Object> selectExamById(Integer examId);

    @Delete("DELETE FROM ExamQuestions WHERE examId = #{examId}")
    void deleteExamQuestions(Integer examId);

    @Delete("DELETE FROM Exam WHERE examId = #{examId}")
    void deleteExam(Integer examId);

    @Select("SELECT eq.* FROM ExamQuestions eq " +
            "JOIN TrainingStatus ts ON ts.examId = eq.examId " +
            "WHERE ts.userId = #{userId}")
    List<ExamQuestions> getAnswersByUserId(Integer userId);

    @Select("SELECT e.examId, e.title, eq.questionId, eq.content, eq.optionA, eq.optionB, eq.optionC, eq.optionD, eq.answer, eq.score " +
            "FROM Exam e " +
            "JOIN ExamQuestions eq ON e.examId = eq.examId " +
            "WHERE e.examId = #{examId}")
    List<Map<String, Object>> getExamWithQuestions(Integer examId);
}
