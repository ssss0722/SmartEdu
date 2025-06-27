package com.utils;

import io.jsonwebtoken.*;
import java.util.Date;

import io.jsonwebtoken.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtils {
    // 使用字节数组作为密钥（推荐）
    private static final byte[] SECRET_KEY_BYTES = "t7b0X5eI6cD8zJvF2qLh1uRnK9wA4sYpZ3xM7gN0iVfC5dTQlWjO4yBkUaHrP9mS6eI2oD1vG3qJ8nK4wC1234567890abcdefghijklmnopqrstuvwxyzABCD".getBytes(StandardCharsets.UTF_8);
    private static final long EXPIRATION = 86400000; // 24小时

    public static String generateToken(Long userId, String username, String subject, String role) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("userId", userId)
                .claim("username", username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                // 使用字节数组签名（解决Base64问题）
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY_BYTES)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY_BYTES) // 使用字节数组
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}