package com.qiyuan.web.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanternAdminRecord {
    private String id;               // 燈購買紀錄 ID
    private String lanternName;     // 燈的名稱
    private String lanternCode;     // 燈的代碼
    private String username;        // 使用者的nickname
    private String userId;          // 使用者 ID
    private String name;            // 點燈姓名
    private Date birthday; // 生日
    private String message;         // 祝福語
    private Date createTime;   // 建立時間
    private Date expiredTime;  // 到期時間
    private Short blessingTimes;    // 祝福次數
}