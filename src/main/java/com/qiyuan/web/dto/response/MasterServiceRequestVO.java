package com.qiyuan.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MasterServiceRequestVO {
    private String serial; // 前端看到的序號（格式: 01XY）
    private String masterCode;
    private String name;
    private String phone;
    private String email;
    private String lineId;
    private String note;
    private String service;
    private Date createTime;
}
