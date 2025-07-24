package com.qiyuan.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class QapItemVO {
    private String title;
    private String content;
    private Integer price;
}
