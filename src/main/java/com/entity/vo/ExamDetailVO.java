package com.entity.vo;


public class ExamDetailVO {
    private Long questionId;
    private String questionStem;
    private String correctAnswer;
    private Integer questionScore;
    private String studentAnswer;
    private Integer studentScore;
    private String options; // 题目选项（JSON字符串）

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }



    // getter & setter
    public Long getQuestionId() {
        return questionId;
    }
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
    public String getQuestionStem() {
        return questionStem;
    }
    public void setQuestionStem(String questionStem) {
        this.questionStem = questionStem;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    public Integer getQuestionScore() {
        return questionScore;
    }
    public void setQuestionScore(Integer questionScore) {
        this.questionScore = questionScore;
    }
    public String getStudentAnswer() {
        return studentAnswer;
    }
    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }
    public Integer getStudentScore() {
        return studentScore;
    }
    public void setStudentScore(Integer studentScore) {
        this.studentScore = studentScore;
    }
}

