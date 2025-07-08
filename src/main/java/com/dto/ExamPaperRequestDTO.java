package com.dto;

import com.baomidou.mybatisplus.annotations.TableField;

public class ExamPaperRequestDTO {

    private String title;        // 新标题
    private Long courseId;       // 课程ID
    private Integer single;      // 单选题数量
    private Integer multiple;    // 多选题数量
    private Integer judge;       // 判断题数量
    private Integer blank;       // 填空题数量
    private  Integer time;//时长
    @TableField("t_username")
    private String tUsername;//教师工号
    private Integer status;//考试状态


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

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String gettUsername() {
        return tUsername;
    }

    public void settUsername(String tUsername) {
        this.tUsername = tUsername;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
