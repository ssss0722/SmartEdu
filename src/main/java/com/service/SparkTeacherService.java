package com.service;

import com.dto.SparkRequest;
import com.dto.SparkResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class SparkTeacherService {
    private final RestTemplate restTemplate;
    @Value("${xfyun.appid}")
    private String appId;

    @Value("${xfyun.api-key}")
    private String apiKey;

    @Value("${xfyun.api-secret}")
    private String apiSecret;

    @Value("${xfyun.api-password}")
    private String apiPassword;

    @Value("${xfyun.model}")
    private String model;

    public SparkTeacherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateTeachingPlan(String subject, String grade,int type,String topic) throws Exception {
        try{

        // 1. 构造系统提示词
        String systemPrompt = "你是一位资深教师助手，请根据学科和年级生成详细的教学计划。";

        // 2. 构造用户问题
        String userQuestion1 = String.format("为%s年级的%s学科生成一份教学计划，主题为%s，包括教学目标、教学内容和教学方法。", grade, subject,topic);
        String userQuestion2 = String.format("为%s年级的%s学科生成一份教案，主题为%s，", grade, subject,topic);
        String userQuestion3 = String.format("为%s年级的%s学科生成一份课件大纲，主题为%s，", grade, subject,topic);
        String userQuestion4 = String.format("为%s年级的%s学科想几个课堂活动，主题为%s，", grade, subject,topic);

        // 3. 构造请求体
        SparkRequest request = new SparkRequest();
        request.setModel(model);

        List<SparkRequest.Message> messages = new ArrayList<>();
        messages.add(new SparkRequest.Message("system", systemPrompt));
        switch (type){
            case 1:
                messages.add(new SparkRequest.Message("user", userQuestion1));
                break;
            case 2:
                messages.add(new SparkRequest.Message("user", userQuestion2));
                break;
            case 3:
                messages.add(new SparkRequest.Message("user", userQuestion3));
                break;
            case 4:
                messages.add(new SparkRequest.Message("user", userQuestion4));
                break;
        }
        request.setMessages(messages);

        // 4. 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiPassword);

        HttpEntity<SparkRequest> entity = new HttpEntity<>(request, headers);

            // 发送请求
            ResponseEntity<SparkResponse> response;
            try {
                response = restTemplate.exchange(
                        "https://spark-api-open.xf-yun.com/v1/chat/completions",
                        HttpMethod.POST,
                        entity,
                        SparkResponse.class
                );
            } catch (RestClientException e) {
                // 捕获网络层异常
                throw new RuntimeException("API连接失败: " + e.getMessage(), e);
            }
            // 处理响应
            SparkResponse body = response.getBody();
            if (body == null) {
                throw new RuntimeException("星火API返回空响应");
            }

            // 处理错误响应（新增详细错误日志）
            if (body.getCode() != 0) {
                String errorDetails = "错误信息: " + body.getMessage();
                if (body.getError() != null) {
                    errorDetails += " | 错误类型: " + body.getError().getType();
                    errorDetails += " | 错误代码: " + body.getError().getCode();
                }
                throw new RuntimeException("星火API错误: " + errorDetails);
            }

            // 安全解析响应内容
            if (body.getChoices() == null || body.getChoices().isEmpty()) {
                throw new RuntimeException("星火API返回空结果集");
            }

            SparkResponse.Message message = body.getChoices().get(0).getMessage();
            if (message == null || message.getContent() == null) {
                throw new RuntimeException("星火API返回无效内容");
            }

            return message.getContent();
        } catch (Exception e) {
            // 打印完整堆栈跟踪（关键调试信息）
            e.printStackTrace();
            throw new RuntimeException("生成失败: " + e.getMessage(), e);
        }}
}