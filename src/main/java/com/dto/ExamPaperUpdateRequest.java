package com.dto;

public class ExamPaperUpdateRequest {
    private Long id;             // 试卷ID
    private String title;        // 新标题
    private Long courseId;       // 课程ID
    private Integer single;      // 单选题数量
    private Integer multiple;    // 多选题数量
    private Integer judge;       // 判断题数量
    private Integer blank;       // 填空题数量
    private Integer subjective;  // 主观题数量


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getSingle() {
        return single;
    }

    public void setSingle(Integer single) {
        this.single = single;
    }

    public Integer getMultiple() {
        return multiple;
    }

    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }

    public Integer getJudge() {
        return judge;
    }

    public void setJudge(Integer judge) {
        this.judge = judge;
    }

    public Integer getBlank() {
        return blank;
    }

    public void setBlank(Integer blank) {
        this.blank = blank;
    }

    public Integer getSubjective() {
        return subjective;
    }

    public void setSubjective(Integer subjective) {
        this.subjective = subjective;
    }


}
