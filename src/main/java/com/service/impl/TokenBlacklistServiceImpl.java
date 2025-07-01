package com.service.impl;

import com.service.TokenBlacklistService;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    // 线程安全的黑名单存储
    private final Set<String> blacklist = ConcurrentHashMap.newKeySet();

    // 定时清理器
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void TokenBlacklistService() {
        // 每10分钟清理一次过期token
        scheduler.scheduleAtFixedRate(this::cleanExpiredTokens, 10, 10, TimeUnit.MINUTES);
    }

    public void addToBlacklist(String token) {
        blacklist.add(token);
    }

    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }

    private void cleanExpiredTokens() {
        // 实际应用中应检查token过期时间
        // 这里简单保留最近1000个token
        if (blacklist.size() > 1000) {
            Iterator<String> it = blacklist.iterator();
            int count = 0;
            while (it.hasNext() && count < 200) {
                it.next();
                it.remove();
                count++;
            }
        }
    }
}
