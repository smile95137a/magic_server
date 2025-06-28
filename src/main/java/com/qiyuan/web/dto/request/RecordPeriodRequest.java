package com.qiyuan.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qiyuan.common.annotations.ValidRecordPeriod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidRecordPeriod
public class RecordPeriodRequest {
    @Schema(description = "起始日期(yyyy/MM/dd)", example = "2025/06/23")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    @NotNull
    private Date startTime;

    @Schema(description = "結束日期(yyyy/MM/dd)", example = "2025/07/23")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    @NotNull
    private Date endTime;
}
