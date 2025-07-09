package com.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import com.baidu.aip.face.AipFace;

@Service
public class BaiduFaceService {

    private final AipFace client;

    public BaiduFaceService(
        @Value("${baidu.ai.api-key}") String apiKey,
        @Value("${baidu.ai.secret-key}") String secretKey,
        @Value("${baidu.ai.app-id}") String appId) {

        client = new AipFace(appId, apiKey, secretKey);
        client.setConnectionTimeoutInMillis(5000);
    }

    // 人脸注册
    public JSONObject registerFace(String userId, String base64Image, String groupId) {
        HashMap<String, String> options = new HashMap<>();
        options.put("user_info", "User:" + userId);
        options.put("quality_control", "NORMAL");
        return client.addUser(base64Image, "BASE64", groupId, userId, options);
    }

    // 人脸搜索
    public JSONObject searchFace(String base64Image, String groupName) {
        HashMap<String, Object> options = new HashMap<>(); // 改为 Object
        options.put("max_user_num", 1); // 注意：这里使用整数而不是字符串
        options.put("match_threshold", 80); // 使用整数而不是字符串
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "HIGH");
        return client.search(base64Image, "BASE64", groupName, options);
    }
}