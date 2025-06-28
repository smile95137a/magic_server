package com.qiyuan.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BannerRequest {
    @Schema(description = "檔名", example = "banner2025.xxx")
    private String filename;

    @Schema(description = "排序", example = "1")
    private Short sort;

    @Pattern(regexp = "A|B", message = "格式錯誤")
    @Schema(description = "banner類型", example = "A:主banner;B:會員好康")
    private String type;

    @Schema(description = "上架日期(yyyy/MM/dd)", example = "2025/06/23")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date availableFrom;

    @Schema(description = "下架日期(yyyy/MM/dd)", example = "2025/07/01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date availableUntil;
}
