package com.qiyuan.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    private Long id;
    private String username;
    private String token;
    private String type;
    private Date expirationTime;
    private Boolean revoked;
    private Date createTime;
}
