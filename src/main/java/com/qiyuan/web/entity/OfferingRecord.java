package com.qiyuan.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferingRecord {
    private String id;
    private String offeringName;
    private String godName;
    private Date createTime;
}
