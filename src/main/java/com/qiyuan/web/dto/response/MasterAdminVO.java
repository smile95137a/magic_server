package com.qiyuan.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MasterAdminVO {
    @Schema(description = "老師代號", example = "A001")
    private String code;

    @Schema(description = "老師名字", example = "王大明")
    private String name;

    @Schema(description = "老師頭銜", example = "紫微命理大師")
    private String title;

    @Schema(description = "老師主星", example = "天機")
    private String mainStar;

    @Schema(description = "老師簡介", example = "專業命理師，從業20年，服務過千名客戶。")
    private String bio;

    @Schema(description = "老師學經歷", example = "國立大學命理系畢業，紫微斗數協會會員。")
    private String experience;

    @Schema(description = "老師親算項目", example = "紫微斗數、八字、流年運勢")
    private String personalItems;

    @Schema(description = "老師服務項目")
    private List<QaItemVO> serviceItem;

    @Schema(description = "老師是否在架上", example = "true")
    private Boolean status;

    @Schema(
            description = "老師圖像（Base64編碼字串，格式如：data:image/jpeg;base64,...）",
            example = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD..."
    )
    private String imageBase64;

    @Schema(description = "老師排序", example = "10")
    private Byte sort;
}
