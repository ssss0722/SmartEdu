package com.controller;

import com.annotation.IgnoreAuth;
import com.utils.AgoraTokenGenerator;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import com.config.AgoraConfig;

@RestController
@RequestMapping("/api/token")
public class TokenController {
    
    private final AgoraConfig agoraConfig;
    private final Bucket bucket;
    
    @Autowired
    public TokenController(AgoraConfig agoraConfig) {
        this.agoraConfig = agoraConfig;
        
        // 配置限流：每秒10个请求，突发容量100
        Bandwidth limit = Bandwidth.classic(100, Refill.greedy(10, Duration.ofSeconds(1)));
        this.bucket = Bucket4j.builder().addLimit(limit).build();
    }

    @IgnoreAuth
    @GetMapping("/rtc")
    public ResponseEntity<?> generateRtcToken(
            @RequestParam String channelName,
            @RequestParam String userId,
            @RequestParam(defaultValue = "PUBLISHER") String role) {
        
        // 检查限流
        if (!bucket.tryConsume(1)) {
            return ResponseEntity.status(429).body("请求过于频繁，请稍后再试");
        }
        
        AgoraTokenGenerator.Role tokenRole = "SUBSCRIBER".equalsIgnoreCase(role) ?
            AgoraTokenGenerator.Role.SUBSCRIBER : AgoraTokenGenerator.Role.PUBLISHER;
        
        String token = AgoraTokenGenerator.generateRtcToken(
            agoraConfig.getAppId(),
            agoraConfig.getAppCertificate(),
            channelName,
            userId,
            tokenRole,
            agoraConfig.getTokenExpireTime()
        );
        
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("appId", agoraConfig.getAppId());
        response.put("channel", channelName);
        response.put("userId", userId);
        response.put("role", role);
        response.put("expireTime", String.valueOf(agoraConfig.getTokenExpireTime()));
        
        return ResponseEntity.ok(response);
    }

    @IgnoreAuth
    @GetMapping("/rtm")
    public ResponseEntity<?> generateRtmToken(
            @RequestParam String userId) {
        
        // 检查限流
        if (!bucket.tryConsume(1)) {
            return ResponseEntity.status(429).body("请求过于频繁，请稍后再试");
        }
        
        String token = AgoraTokenGenerator.generateRtmToken(
            agoraConfig.getAppId(),
            agoraConfig.getAppCertificate(),
            userId,
            agoraConfig.getTokenExpireTime()
        );
        
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("appId", agoraConfig.getAppId());
        response.put("userId", userId);
        response.put("expireTime", String.valueOf(agoraConfig.getTokenExpireTime()));
        
        return ResponseEntity.ok(response);
    }
}