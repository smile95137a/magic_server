package com.qiyuan.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MyGodInfoVO {
    private String godName;                 // 神明名稱
    private int totalDays;                  // 供奉總天數
    private int passDays;                // 已供奉天數
    private int remainingDays;              // 剩餘天數
    private List<OfferingStateVO> offeringStates;  // 目前有效強化供品
}
