package com.qiyuan.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;

    private String password;

    private String email;

    private String phone;

    private String nickname;

    private String lineId;

    private String addressName;

    private String address;

    private String receiptType;

    private String username;
    
    private String oauthType;

    private String oauthId;

    private String provider;

    private String city;

    private String district;

}
