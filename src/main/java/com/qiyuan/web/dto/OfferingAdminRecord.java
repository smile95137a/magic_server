package com.qiyuan.web.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OfferingAdminRecord {
    private String id;               // 訂單/紀錄 ID
    private String offeringName;     // 供品名稱
    private String userId;           // 使用者 ID
    private String username;         // 使用者帳號
    private String godName;          // 神明名稱
    private LocalDateTime createTime; // 建立時間
}

