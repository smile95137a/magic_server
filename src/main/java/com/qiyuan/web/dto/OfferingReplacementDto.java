package com.qiyuan.web.dto;

import com.qiyuan.web.entity.Offering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferingReplacementDto {
    private Integer index;
    private String newOfferingId;
    private String oldOfferingId;
    private Offering offering;
}

