package com.dto;

import java.util.Date;
import java.util.List;

public class ExamUpdateDTO extends ExamRequestDTO{
    private Long id;
    private String name;
    private Long paperId;
    private Date startTime;
    private Date endTime;
    private List<String> sUsernames;

    // getter & setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getPaperId() {
        return paperId;
    }

    @Override
    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    @Override
    public Date getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public Date getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public List<String> getsUsernames() {
        return sUsernames;
    }

    @Override
    public void setsUsernames(List<String> sUsernames) {
        this.sUsernames = sUsernames;
    }
}
