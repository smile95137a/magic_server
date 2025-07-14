package com.qiyuan.web.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "宅配收件人資訊")
public class HomeDeliveryRecipientInfo {
    @Schema(description = "收件人姓名", example = "王小明", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "收件人手機", example = "0912345678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;

    @Schema(description = "收件人地址", example = "台北市信義區松山路100號", requiredMode = Schema.RequiredMode.REQUIRED)
    private String address;
}

