package com.qiyuan.web.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CountRequest {
    @NotNull(message = "count 不能為空")
    @Min(value = 1, message = "最少要 1 筆")
    @Max(value = 20, message = "最多只能請求 20 筆資料")
    private int count;
}
