package com.controller;

import com.annotation.IgnoreAuth;
import com.utils.AgoraTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import com.config.AgoraConfig;

@RestController
@RequestMapping("/api/secure/token")
public class SecureTokenController {
    
    private final AgoraConfig agoraConfig;
    
    @Autowired
    public SecureTokenController(AgoraConfig agoraConfig) {
        this.agoraConfig = agoraConfig;
    }

    @IgnoreAuth
    @GetMapping("/rtc")
    public ResponseEntity<?> generateRtcToken(
            @RequestParam String channelName,
            @RequestHeader("Authorization") String authHeader) {
        
        // 伪代码：验证JWT Token并提取用户ID
        String userId = validateAndExtractUserId(authHeader);
        
        // 伪代码：根据业务逻辑确定用户角色
        AgoraTokenGenerator.Role role = determineUserRole(userId, channelName);
        
        String token = AgoraTokenGenerator.generateRtcToken(
            agoraConfig.getAppId(),
            agoraConfig.getAppCertificate(),
            channelName,
            userId,
            role,
            agoraConfig.getTokenExpireTime()
        );
        
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("appId", agoraConfig.getAppId());
        response.put("channel", channelName);
        response.put("userId", userId);
        response.put("role", role.name());
        response.put("expireTime", String.valueOf(agoraConfig.getTokenExpireTime()));
        
        return ResponseEntity.ok(response);
    }
    
    private String validateAndExtractUserId(String authHeader) {
        // 实际实现中应验证JWT Token并返回用户ID
        // 这里返回一个示例值
        return "secure_user_" + System.currentTimeMillis() % 1000;
    }
    
    private AgoraTokenGenerator.Role determineUserRole(String userId, String channelName) {
        // 实际实现中根据业务逻辑确定用户角色
        // 示例：用户ID包含"admin"则返回发布者角色
        return userId.contains("admin") ? 
            AgoraTokenGenerator.Role.PUBLISHER :
            AgoraTokenGenerator.Role.SUBSCRIBER;
    }
}