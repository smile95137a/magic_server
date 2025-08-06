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
    private String orderId;
    private String username;
    private String status;
    private Boolean isPaid;
    private Date startTime;
    private Date endTime;
}
