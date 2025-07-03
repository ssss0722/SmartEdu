package com.dto;

import java.util.Date;
import java.util.List;

public class ExamRequestDTO {
    private String name;
    private Long paperId;
    private Integer status;
    private Date startTime;
    private Date endTime;
    private List<Long> studentIds;

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

    public List<Long> getStudentIds() {
        return studentIds;
    }
    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }
}
