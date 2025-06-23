package com.qiyuan.web.dto.response;


import com.qiyuan.web.entity.Offering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GodInfoVO {
    private String name;
    private String imageCode;
    private boolean isGolden;
    private List<Offering> offerings;
    private Date onshelfTime;
    private Date offshelfTime;
    private Date cooldownTime;
}
