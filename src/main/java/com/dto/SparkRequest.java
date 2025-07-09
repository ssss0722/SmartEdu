package com.dto;

import java.util.List;

// SparkRequest.java
public class SparkRequest {
    private String model;
    private List<Message> messages;
    private Double temperature;
    private Integer max_tokens;

    // 添加 getter 和 setter
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> messages) { this.messages = messages; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public Integer getMax_tokens() { return max_tokens; }
    public void setMax_tokens(Integer max_tokens) { this.max_tokens = max_tokens; }

    // Message 内部类（修复构造函数问题）
    public static class Message {
        private String role;
        private String content;

        // 添加正确的构造函数
        public Message() {}

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        // 添加 getter 和 setter
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
}