package com.qiyuan.web.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SenderInfoDto {
    @Size(max = 5, message = "姓名最多為 5 個字")
    private String name;
    @Pattern(regexp = "^\\d{0,10}$", message = "手機號碼必須是 10 碼以內的純數字")
    private String phone;
    private String city;
    private String address;
    private String zipcode;
}
