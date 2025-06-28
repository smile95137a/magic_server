package com.qiyuan.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordVO {
    @Schema(description = "日期(yyyy/MM/dd mm:SS)", example = "2025/06/23 11:20")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
    private String date;
    @Schema(description = "項目名稱", example = "項目")
    private String item;
    @Schema(description = "內容名稱", example = "內容")
    private String content;
}
