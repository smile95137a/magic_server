package com.qiyuan.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferingVO {
    @Schema(description = "供品ID", example = "123")
    private String id;

    @Schema(description = "供品圖片Base64編碼", example = "data:xxx;base64,/9j/4AAQSk...")
    private String imageBase64;

    @Schema(description = "可獲得經驗值", example = "10")
    private Byte points;

    @Schema(description = "供品價格", example = "50")
    private Short price;

    @Schema(description = "供品名稱", example = "金紙")
    private String name;
}
