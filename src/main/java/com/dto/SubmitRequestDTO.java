package com.dto;

import java.util.Map;

public class SubmitRequestDTO {
    private Long paperId;
    private Map<Long,String> answers;


    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Map<Long, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, String> answers) {
        this.answers = answers;
    }
}
