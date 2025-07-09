package com.interceptor;

import com.annotation.IgnoreAuth;
import com.service.TokenBlacklistService;
import com.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final TokenBlacklistService tokenBlacklistService;

    @Autowired
    public JwtInterceptor(TokenBlacklistService tokenBlacklistService) {
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 尝试从请求头获取token (优先)
        String tokenHeader = request.getHeader("Authorization");
        String token = null;

        // 处理Authorization头（支持Bearer格式）
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            token = tokenHeader.substring(7);
        }
        // 2. 如果请求头没有，再从请求参数获取token
        else if (request.getParameter("token") != null) {
            token = request.getParameter("token");
        }

        // 2. 检查是否带IgnoreAuth注解的接口
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethodAnnotation(IgnoreAuth.class) != null) {
                return true; // 忽略认证
            }
        }

        // 3. 验证token
        if (!isValidToken(token)) {
            sendJsonError(response, 401, "请先登录");
            return false;
        }

        return true;
    }


    private boolean isValidToken(String fullToken) {
        if (fullToken == null) return false;

        // 提取纯token部分
        String token = fullToken.startsWith("Bearer ") ?
                fullToken.substring(7) : fullToken;

        // 检查黑名单
        if (tokenBlacklistService.isBlacklisted(token)) {
            return false;
        }

        // 验证有效性
        return JwtUtils.isValidToken(fullToken);
    }

    private void sendJsonError(HttpServletResponse response, int code, String message) {
        try {
            response.setStatus(code);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(
                    String.format("{\"code\":%d,\"msg\":\"%s\"}", code, message)
            );
        } catch (IOException e) {
            // 日志记录
        }
    }
}