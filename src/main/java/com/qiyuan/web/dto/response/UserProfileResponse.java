package com.qiyuan.web.dto.response;

import com.qiyuan.web.dto.InvoiceDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponse {
    private String nickName;
    private String addressName;
    private String lineId;
    private String phone;
    private String zipCode;
    private String city;
    private String area;
    private String address;
    private String email;
    private InvoiceDTO invoice;
}
