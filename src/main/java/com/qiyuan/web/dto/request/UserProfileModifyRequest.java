package com.qiyuan.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileModifyRequest {
    @NotBlank
    private String nickName;
    @NotBlank
    private String addressName;
    @NotBlank
    private String lineId;
    @NotBlank
    private String phone;

    private String address;
}
