package com.qiyuan.web.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Schema(description = "收件人姓名", example = "王小明")
    @Size(max = 5, message = "收件人姓名最多為 5 個字")
    private String name;

    @Schema(description = "收件人手機", example = "0912345678")
    @Pattern(regexp = "^\\d{0,10}$", message = "手機號碼必須是 10 碼以內的純數字")
    private String phone;

    @Schema(description = "收件人郵遞區號", example = "100")
    private String zipCode;
    @Schema(description = "收件人地址縣市", example = "台北市")
    private String city;
    @Schema(description = "收件人詳細地址", example = "信義區松山路100號")
    private String address;
}

