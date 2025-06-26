package com.qiyuan.security.service.impl;

import com.qiyuan.security.entity.Token;
import com.qiyuan.security.dao.TokenMapper;
import com.qiyuan.security.service.TokenStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class DatabaseTokenStorageService implements TokenStorageService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseTokenStorageService.class);

    private final TokenMapper tokenMapper;

    public DatabaseTokenStorageService(TokenMapper tokenMapper) {
        this.tokenMapper = tokenMapper;
    }

    @Override
    public void saveRefreshToken(String username, String token, LocalDateTime expiresAt) {
        Token tokenEntity = new Token();
        tokenEntity.setUsername(username);
        tokenEntity.setToken(token);
        tokenEntity.setExpirationTime(expiresAt);
        tokenEntity.setRevoked(false);
        tokenMapper.insert(tokenEntity);
        logger.debug("儲存 Refresh Token for user: {}", username);
    }

    @Override
    public void revokeToken(String token) {
        Token entity = tokenMapper.findByToken(token);
        if (entity != null) {
            tokenMapper.revokeToken(token);
             logger.debug("已廢止 Token: {}", token);
        }
    }

    @Override
    public void revokeTokensByUsername(String username) {
        if (tokenMapper.countByUsername(username) > 0) {
            tokenMapper.revokeByUserName(username);
        }
    }

    @Override
    public boolean isTokenValid(String token) {
        Token entity = tokenMapper.findByToken(token);
        return entity != null &&
                !entity.isRevoked() &&
                entity.getExpirationTime().isAfter(LocalDateTime.now());
    }

    @Override
    public void cleanupExpiredTokens() {
        int tokens = tokenMapper.countTokenExpiredOver7Day();
        if (tokens > 0) {
            tokenMapper.deleteExpiredTokens();
            logger.debug("已清除所有過期的 token");
        }
    }
}
