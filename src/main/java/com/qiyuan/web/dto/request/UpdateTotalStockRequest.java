package com.qiyuan.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateTotalStockRequest {
    private Integer totalStock;
    private String remark;
}
