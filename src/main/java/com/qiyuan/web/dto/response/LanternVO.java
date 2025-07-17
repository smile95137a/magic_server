package com.qiyuan.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanternVO {
    private String id;

    private String name;

    private String subtitle;

    private String iconName;

    private String labelRight;

    private String qaJson;

    private Byte sort;

    private Integer count;

    private List<LanternPriceVO> lanternPrice;
}
