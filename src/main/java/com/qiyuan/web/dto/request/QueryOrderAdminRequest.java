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
public class QueryOrderAdminRequest {
    private String userId;
    private String status;
    private String paymentStatus;
    private Date startTime;
    private Date endTime;
    private Integer page;
    private Integer size;
}
