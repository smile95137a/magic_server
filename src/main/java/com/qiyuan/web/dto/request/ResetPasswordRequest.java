package com.qiyuan.web.dto.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String newPassword;
    private String token;
}
