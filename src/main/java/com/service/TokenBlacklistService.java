package com.service;

public interface TokenBlacklistService {
    /**
     * 将 token 加入黑名单
     */
    void addToBlacklist(String token);

    /**
     * 检查 token 是否在黑名单中
     */
    boolean isBlacklisted(String token);
}
