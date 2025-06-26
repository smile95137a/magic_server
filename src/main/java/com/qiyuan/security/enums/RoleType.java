package com.qiyuan.security.enums;

public enum RoleType {
    USER,
    ADMIN;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}

