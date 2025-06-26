package com.qiyuan.security.service;

import java.time.Instant;
import java.time.LocalDateTime;

public interface TokenStorageService {
    void saveRefreshToken(String username, String token, LocalDateTime expiresAt);
    void revokeToken(String token); // 登出或廢止
    void revokeTokensByUsername(String username);
    boolean isTokenValid(String token);
    void cleanupExpiredTokens();
}

