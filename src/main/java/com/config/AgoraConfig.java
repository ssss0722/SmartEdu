package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgoraConfig {
    
    @Value("${agora.app.id}")
    private String appId;
    
    @Value("${agora.app.certificate}")
    private String appCertificate;
    
    @Value("${agora.token.expire-time:3600}") // 默认1小时
    private int tokenExpireTime;
    
    public String getAppId() {
        return appId;
    }
    
    public String getAppCertificate() {
        return appCertificate;
    }
    
    public int getTokenExpireTime() {
        return tokenExpireTime;
    }
}