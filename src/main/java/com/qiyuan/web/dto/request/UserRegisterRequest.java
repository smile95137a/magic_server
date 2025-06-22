package com.qiyuan.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @Pattern(regexp = "^09\\d{8}$", message = "手機號碼格式錯誤，須為 09 開頭共 10 碼")
    @NotBlank
    private String phone;
    @NotBlank
    private String nickName;
    @NotBlank
    private String lineId;
    private String addressName;
    private String address;
}
