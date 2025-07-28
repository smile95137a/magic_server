package com.qiyuan.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChineseCalendarRequest {

    @Schema(description = "年份", example = "2024")
    private int year;

    @Schema(description = "月份", example = "4")
    private int month;

    @Schema(description = "日期", example = "3")
    private int day;
}