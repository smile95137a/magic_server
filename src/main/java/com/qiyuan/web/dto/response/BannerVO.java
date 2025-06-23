package com.qiyuan.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class BannerVO implements Serializable {
    @Schema(description = "圖片Base64編碼", example = "data:xxx;base64,/9j/4AAQSk...")
    private String imgBase64;
    @Schema(description = "排序", example = "1")
    private Short sort;
}
