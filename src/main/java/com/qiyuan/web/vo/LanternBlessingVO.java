package com.qiyuan.web.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanternBlessingVO implements Serializable {
    private String name;
    private String message;
    private Date createTime;
    private String lanternCode;
    private int blessing;
}
