package com.qiyuan.web.security;

public class RoleExpressions {
    public static final String ONLY_USER = "hasRole('USER')";
    public static final String ONLY_ADMIN = "hasRole('ADMIN')";
    public static final String USER_OR_ADMIN = "hasAnyRole('USER','ADMIN')";
}

