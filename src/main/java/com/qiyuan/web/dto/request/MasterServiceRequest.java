package com.qiyuan.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MasterServiceRequest implements Serializable {
    private String serviceItem;

    @NotBlank(message = "老師編號不可為空")
    private String masterCode;

    @NotBlank(message = "顧客姓名不可為空")
    @Size(min = 1, message = "顧客姓名至少需 1 個字")
    private String customerName;

    @NotBlank(message = "LINE ID 不可為空")
    private String customerLine;

    @NotBlank(message = "Email 不可為空")
    @Email(message = "Email 格式不正確")
    private String customerEmail;

    @NotBlank(message = "電話不可為空")
    private String customerPhone;
    
    private String paymentMethod; 

    private String note;
}
