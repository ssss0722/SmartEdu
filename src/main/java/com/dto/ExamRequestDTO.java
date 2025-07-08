package com.dto;

import com.baomidou.mybatisplus.annotations.TableField;

import java.util.Date;
import java.util.List;

public class ExamRequestDTO {
    private String name;
    private Long paperId;
    private Integer status;
    private Date startTime;
    private Date endTime;
    private Long courseId;
    @TableField("t_username")
    private String tUsername;
    private List<String> sUsernames;

    public ExamRequestDTO() {
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getPaperId() {
        return paperId;
    }
    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public void settUsername(String username){this.tUsername=username;}
    public String gettUsername(){return tUsername;}

    public List<String> getsUsernames() {
        return sUsernames;
    }

    public void setsUsernames(List<String> sUsernames) {
        this.sUsernames = sUsernames;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
