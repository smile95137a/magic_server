package com.qiyuan.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferingVO {
    private String id;

    private String imageBase64;

    private Byte points;

    private Short price;

    private String name;
}
