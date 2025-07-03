package com.qiyuan.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QueryOrderRequest {
    private String status;
    private Date startTime;
    private Date endTime;
    private Integer page;
    private Integer size;
}
