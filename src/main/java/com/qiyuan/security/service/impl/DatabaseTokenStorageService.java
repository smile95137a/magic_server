package com.qiyuan.security.service.impl;

import com.qiyuan.security.entity.Token;
import com.qiyuan.security.dao.TokenMapper;
import com.qiyuan.security.service.TokenStorageService;
import com.qiyuan.web.enums.TokenType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


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
        tokenEntity.setExpirationTime(Date.from(expiresAt.atZone(ZoneId.systemDefault()).toInstant()));
        tokenEntity.setRevoked(false);
        tokenEntity.setType(TokenType.REFRESH.getValue());
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
        if (tokenMapper.countRefreshByUsername(username) > 0) {
            tokenMapper.revokeRefreshByUsername(username);
        }
    }

    @Override
    public boolean isTokenValid(String token) {
        Token entity = tokenMapper.findByToken(token);
        if (entity == null || entity.getRevoked()) {
            return false;
        }
        Date expire = entity.getExpirationTime();
        LocalDateTime expireTime = expire.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return expireTime.isAfter(LocalDateTime.now());
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
