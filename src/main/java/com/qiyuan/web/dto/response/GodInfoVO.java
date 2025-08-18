package com.qiyuan.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema(description = "神明資訊")
public class GodInfoVO {
    @Schema(description = "神明名稱", example = "媽祖")
    private String name;
    @Schema(description = "神明圖片編碼", example = "g-gg")
    private String imageCode;
    @Schema(description = "是否為金牌神明", example = "true")
    private boolean isGolden;
    @Schema(description = "供品列表")
    private List<OfferingVO> offerings;
    @Schema(description = "上架時間", type = "string", format = "date-time", example = "2025-06-23T12:00:00Z")
    private Date onshelfTime;
    @Schema(description = "下架時間", type = "string", format = "date-time", example = "2025-07-01T12:00:00Z")
    private Date offshelfTime;
    @Schema(description = "擲筊冷卻結束時間", type = "string", format = "date-time", example = "2025-06-24T12:00:00Z")
    private Date cooldownTime;
    @Schema(description = "距離金身還差多少經驗值", example = "8")
    private int expToGolden;
    @Schema(description = "累積總經驗值", example = "32")
    private int totalAccumulateExp;
}
