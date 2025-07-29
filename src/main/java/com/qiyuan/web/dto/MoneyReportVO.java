package com.qiyuan.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoneyReportVO {

    @Schema(description = "UUID", example = "a1b2c3d4e5f6...")
    private String id;

    @Schema(description = "對外訂單編號")
    private String externalOrderNo;

    @Schema(description = "第三方訂單編號")
    private String providerOrderNo;

    @Schema(description = "類型 (O:供品, M:老師, G:請神, L:點燈, R: 實體商品)", example = "O")
    private String type;

    @Schema(description = "付款狀態 (created/paid/...)", example = "paid")
    private String status;

    private Date createTime;
}
