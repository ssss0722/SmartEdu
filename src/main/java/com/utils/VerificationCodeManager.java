package com.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class VerificationCodeManager {
    private static final Map<String, CodeInfo> codeStore = new ConcurrentHashMap<>();
    private static final long EXPIRE_MINUTES = 5; // 验证码有效期5分钟

    public static void storeCode(String email, String code) {
        codeStore.put(email, new CodeInfo(code, System.currentTimeMillis()));
    }

    public static boolean verifyCode(String email, String code) {
        CodeInfo stored = codeStore.get(email);
        if (stored == null) return false;
        
        // 检查是否过期
        long elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(
            System.currentTimeMillis() - stored.timestamp
        );
        
        if (elapsedMinutes > EXPIRE_MINUTES) {
            codeStore.remove(email);
            return false;
        }
        
        return stored.code.equals(code);
    }

    public static void removeCode(String email) {
        codeStore.remove(email);
    }

    private static class CodeInfo {
        String code;
        long timestamp;

        CodeInfo(String code, long timestamp) {
            this.code = code;
            this.timestamp = timestamp;
        }
    }
}