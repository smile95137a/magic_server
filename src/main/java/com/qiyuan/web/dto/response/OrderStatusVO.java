package com.qiyuan.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "訂單狀態 VO")
public class OrderStatusVO {
    @Schema(description = "訂單狀態代碼", example = "processing")
    private String code;

    @Schema(description = "訂單狀態名稱", example = "訂單準備中")
    private String label;

    public OrderStatusVO() {}

    public OrderStatusVO(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}

