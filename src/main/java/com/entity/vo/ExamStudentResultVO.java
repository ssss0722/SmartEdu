package com.entity.vo;

public class ExamStudentResultVO {
    private String studentName;
    private String paperName;
    private Integer totalScore;
    private String courseName;
    private String submitTime;

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPaperName() {
        return paperName;
    }
    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public Integer getTotalScore() {
        return totalScore;
    }
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSubmitTime() {
        return submitTime;
    }
    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }
}
