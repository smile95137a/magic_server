package com.qiyuan.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OAuthLoginResponse {
    private String token;
    private boolean needCompleteProfile;
    private String email;
    private String nickname;
}
