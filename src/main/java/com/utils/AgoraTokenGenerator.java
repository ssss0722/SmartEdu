package com.utils;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class AgoraTokenGenerator {

    // 角色定义
    public enum Role {
        PUBLISHER(1), SUBSCRIBER(2);
        
        public final int value;
        
        Role(int value) {
            this.value = value;
        }
    }

    // 生成RTC Token
    public static String generateRtcToken(String appId, String appCertificate, 
                                         String channelName, String userId, 
                                         Role role, int expirationTimeInSeconds) {
        // 计算过期时间戳（当前时间+有效期）
        int timestamp = (int)(System.currentTimeMillis() / 1000) + expirationTimeInSeconds;
        
        // 构建消息内容
        byte[] message = buildMessage(
            appId, 
            channelName, 
            userId, 
            timestamp, 
            role.value
        );
        
        // 生成签名
        byte[] signature = generateSignature(appCertificate, message);
        
        // 构建Token内容
        byte[] tokenContent = buildTokenContent(
            (byte)3, // 版本号
            timestamp,
            role.value,
            signature,
            channelName,
            userId
        );
        
        // 编码并返回Token
        return "006" + appId + Base64.getEncoder().encodeToString(tokenContent);
    }

    // 生成RTM Token
    public static String generateRtmToken(String appId, String appCertificate, 
                                         String userId, int expirationTimeInSeconds) {
        // 计算过期时间戳
        int timestamp = (int)(System.currentTimeMillis() / 1000) + expirationTimeInSeconds;
        
        // 构建消息内容
        byte[] message = buildMessage(appId, userId, timestamp);
        
        // 生成签名
        byte[] signature = generateSignature(appCertificate, message);
        
        // 构建Token内容
        byte[] tokenContent = buildTokenContent(
            (byte)3, // 版本号
            timestamp,
            1, // RTM固定权限
            signature,
            userId
        );
        
        // 编码并返回Token
        return "006" + appId + Base64.getEncoder().encodeToString(tokenContent);
    }

    // 构建RTC消息
    private static byte[] buildMessage(String appId, String channelName, 
                                      String userId, int timestamp, int role) {
        byte[] appIdBytes = appId.getBytes(StandardCharsets.UTF_8);
        byte[] channelBytes = channelName.getBytes(StandardCharsets.UTF_8);
        byte[] userIdBytes = userId.getBytes(StandardCharsets.UTF_8);
        
        // 计算总长度
        int totalLength = appIdBytes.length + channelBytes.length + 
                         userIdBytes.length + 4 + 4;
        
        byte[] buffer = new byte[totalLength];
        int offset = 0;
        
        // 写入appId
        System.arraycopy(appIdBytes, 0, buffer, offset, appIdBytes.length);
        offset += appIdBytes.length;
        
        // 写入channelName
        System.arraycopy(channelBytes, 0, buffer, offset, channelBytes.length);
        offset += channelBytes.length;
        
        // 写入userId
        System.arraycopy(userIdBytes, 0, buffer, offset, userIdBytes.length);
        offset += userIdBytes.length;
        
        // 写入时间戳（大端序）
        buffer[offset++] = (byte)(timestamp >>> 24);
        buffer[offset++] = (byte)(timestamp >>> 16);
        buffer[offset++] = (byte)(timestamp >>> 8);
        buffer[offset] = (byte)timestamp;
        
        return buffer;
    }

    // 构建RTM消息
    private static byte[] buildMessage(String appId, String userId, int timestamp) {
        byte[] appIdBytes = appId.getBytes(StandardCharsets.UTF_8);
        byte[] userIdBytes = userId.getBytes(StandardCharsets.UTF_8);
        
        // 计算总长度
        int totalLength = appIdBytes.length + userIdBytes.length + 4;
        
        byte[] buffer = new byte[totalLength];
        int offset = 0;
        
        // 写入appId
        System.arraycopy(appIdBytes, 0, buffer, offset, appIdBytes.length);
        offset += appIdBytes.length;
        
        // 写入userId
        System.arraycopy(userIdBytes, 0, buffer, offset, userIdBytes.length);
        offset += userIdBytes.length;
        
        // 写入时间戳（大端序）
        buffer[offset++] = (byte)(timestamp >>> 24);
        buffer[offset++] = (byte)(timestamp >>> 16);
        buffer[offset++] = (byte)(timestamp >>> 8);
        buffer[offset] = (byte)timestamp;
        
        return buffer;
    }

    // 生成HMAC-SHA256签名
    private static byte[] generateSignature(String key, byte[] message) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        HMac hmac = new HMac(new SHA256Digest());
        hmac.init(new KeyParameter(keyBytes));
        
        byte[] result = new byte[32];
        hmac.update(message, 0, message.length);
        hmac.doFinal(result, 0);
        
        return result;
    }

    // 构建Token内容
    private static byte[] buildTokenContent(byte version, int timestamp, int role, 
                                           byte[] signature, String... items) {
        // 计算总长度
        int totalLength = 1 + 4 + 4 + 32; // 版本 + 时间戳 + 角色 + 签名
        
        // 添加变长字段长度
        for (String item : items) {
            totalLength += 2 + item.getBytes(StandardCharsets.UTF_8).length;
        }
        
        byte[] buffer = new byte[totalLength];
        int offset = 0;
        
        // 写入版本
        buffer[offset++] = version;
        
        // 写入时间戳（大端序）
        buffer[offset++] = (byte)(timestamp >>> 24);
        buffer[offset++] = (byte)(timestamp >>> 16);
        buffer[offset++] = (byte)(timestamp >>> 8);
        buffer[offset++] = (byte)timestamp;
        
        // 写入角色（大端序）
        buffer[offset++] = (byte)(role >>> 24);
        buffer[offset++] = (byte)(role >>> 16);
        buffer[offset++] = (byte)(role >>> 8);
        buffer[offset++] = (byte)role;
        
        // 写入签名
        System.arraycopy(signature, 0, buffer, offset, 32);
        offset += 32;
        
        // 写入变长字段
        for (String item : items) {
            byte[] itemBytes = item.getBytes(StandardCharsets.UTF_8);
            int length = itemBytes.length;
            
            // 写入长度（小端序）
            buffer[offset++] = (byte)(length & 0xFF);
            buffer[offset++] = (byte)((length >> 8) & 0xFF);
            
            // 写入内容
            System.arraycopy(itemBytes, 0, buffer, offset, length);
            offset += length;
        }
        
        return buffer;
    }
}