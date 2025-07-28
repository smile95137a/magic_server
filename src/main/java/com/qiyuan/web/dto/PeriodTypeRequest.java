package com.qiyuan.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qiyuan.web.enums.SourceTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PeriodTypeRequest {
    @Schema(description = "起始日期(yyyy/MM/dd)", example = "2025/06/23")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    @NotNull
    private Date startTime;

    @Schema(description = "結束日期(yyyy/MM/dd)", example = "2025/07/23")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    @NotNull
    private Date endTime;

//    @Schema(description = "類型 (O:供品, M:老師, G:請神, L:點燈)", example = "O", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotNull
//    private SourceTypeEnum type;
}
