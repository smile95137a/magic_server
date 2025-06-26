package com.qiyuan.security.entity;

import java.time.LocalDateTime;

public class Token {
    private String username;
    private String token;
    private LocalDateTime expirationTime;
    private boolean revoked;

    // Constructors
    public Token() {}

    public Token(String username, String token, LocalDateTime expirationTime, boolean revoked) {
        this.username = username;
        this.token = token;
        this.expirationTime = expirationTime;
        this.revoked = revoked;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }
}
