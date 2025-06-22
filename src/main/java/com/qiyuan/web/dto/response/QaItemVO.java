package com.qiyuan.web.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class QaItemVO {
    private String title;
    private String content;

}
