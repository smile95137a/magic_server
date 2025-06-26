package com.qiyuan.security.util;

import com.qiyuan.security.controller.GlobalResponseController;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    // 從配置文件讀取密鑰，如果沒有則使用預設值
    @Value("${jwt.secret:mySecretKeyForJWTTokenGenerationThatShouldBeAtLeast256BitsLong}")
    private String secretKey;

    // Token 過期時間（小時）
    @Value("${jwt.expiration:30}")
    private int expirationMinutes;

    // Refresh Token 過期時間（天）
    @Value("${jwt.refresh-expiration:7}")
    private int refreshExpirationDays;

    /**
     * 獲取簽名密鑰
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * 生成 Access Token
     *
     * @param username 用戶名
     * @return JWT token
     */
    public String generateToken(String username) {
        return generateToken(username, null);
    }

    /**
     * 生成帶有自定義聲明的 Access Token
     *
     * @param username 用戶名
     * @param claims 自定義聲明
     * @return JWT token
     */
    public String generateToken(String username, Map<String, Object> claims) {
        Instant now = Instant.now();
        Instant expiration = now.plus(Duration.ofMinutes(expirationMinutes));

        JwtBuilder builder = Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(getSigningKey()); // 0.12.6 版本簡化了簽名方法

        // 添加自定義聲明
        if (claims != null && !claims.isEmpty()) {
            builder.claims(claims);
        }

        return builder.compact();
    }

    /**
     * 生成 Refresh Token
     *
     * @param username 用戶名
     * @return refresh token
     */
    public String generateRefreshToken(String username) {
        Instant now = Instant.now();
        Instant expiration = now.plus(Duration.ofDays(refreshExpirationDays));

        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .claim("type", "refresh") // 標記為 refresh token
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 從 token 中獲取用戶名
     *
     * @param token JWT token
     * @return 用戶名
     */
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * 從 token 中獲取過期時間
     *
     * @param token JWT token
     * @return 過期時間
     */
    public Date getExpirationFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    /**
     * 從 token 中獲取簽發時間
     *
     * @param token JWT token
     * @return 簽發時間
     */
    public Date getIssuedAtFromToken(String token) {
        return getClaimsFromToken(token).getIssuedAt();
    }

    /**
     * 從 token 中獲取所有聲明
     *
     * @param token JWT token
     * @return Claims 對象
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser() // 0.12.6 版本使用 parser() 而不是 parserBuilder()
                .verifyWith(getSigningKey()) // 使用 verifyWith() 設置密鑰
                .build()
                .parseSignedClaims(token) // 使用 parseSignedClaims() 解析
                .getPayload();
    }

    /**
     * 從 token 中獲取特定聲明
     *
     * @param token JWT token
     * @param claimName 聲明名稱
     * @param claimType 聲明類型
     * @return 聲明值
     */
    public <T> T getClaimFromToken(String token, String claimName, Class<T> claimType) {
        Claims claims = getClaimsFromToken(token);
        return claims.get(claimName, claimType);
    }

    /**
     * 檢查 token 是否過期
     *
     * @param token JWT token
     * @return true 如果已過期，false 如果未過期
     */
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getExpirationFromToken(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 驗證 token 是否有效
     *
     * @param token JWT token
     * @param username 預期的用戶名
     * @return true 如果有效，false 如果無效
     */
    public boolean validateToken(String token, String username) {
        try {
            String tokenUsername = getUsernameFromToken(token);
            return (username.equals(tokenUsername) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 驗證 token 是否有效（不檢查用戶名）
     *
     * @param token JWT token
     * @return true 如果有效，false 如果無效
     */
    public boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
            return !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            logger.error("Token 已過期: " + e.getMessage(), e);
            return false;
        } catch (UnsupportedJwtException e) {
            logger.error("不支持的 Token: " + e.getMessage(), e);
            return false;
        } catch (MalformedJwtException e) {
            logger.error("Token 格式錯誤: " + e.getMessage(), e);
            return false;
        } catch (SecurityException e) {
            logger.error("Token 簽名無效: " + e.getMessage(), e);
            return false;
        } catch (IllegalArgumentException e) {
            logger.error("Token 參數無效: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 檢查是否為 Refresh Token
     *
     * @param token JWT token
     * @return true 如果是 refresh token
     */
    public boolean isRefreshToken(String token) {
        try {
            String tokenType = getClaimFromToken(token, "type", String.class);
            return "refresh".equals(tokenType);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新 token（使用 refresh token 生成新的 access token）
     *
     * @param refreshToken refresh token
     * @return 新的 access token
     * @throws IllegalArgumentException 如果 refresh token 無效
     */
    public String refreshAccessToken(String refreshToken) {
        if (!validateToken(refreshToken) || !isRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("無效的 Refresh Token");
        }

        String username = getUsernameFromToken(refreshToken);
        return generateToken(username);
    }

    /**
     * 獲取 token 剩餘有效時間（秒）
     *
     * @param token JWT token
     * @return 剩餘秒數，如果已過期返回 0
     */
    public long getTokenRemainingTime(String token) {
        try {
            Date expiration = getExpirationFromToken(token);
            long remaining = expiration.getTime() - System.currentTimeMillis();
            return Math.max(0, remaining / 1000);
        } catch (Exception e) {
            return 0;
        }
    }
}