package com.qiyuan.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShippingMethodRequest {
    private String id;         // 新增時可不傳，修改時必傳
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Integer fee;
    @NotNull
    private Boolean status;
    private Short sort;
}
