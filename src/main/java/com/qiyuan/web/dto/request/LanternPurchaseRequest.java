package com.qiyuan.web.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanternPurchaseRequest implements Serializable {

    @Pattern(regexp = "^lan-[A-Za-z]+$", message = "code 格式錯誤")
    private String lanternCode;

    @Valid
    private List<LanternPurchaseInfo> list;

    private Integer month; // 幾個月

    @NotBlank(message = "付款方式不可為空")
    private String paymentMethod; 
}
