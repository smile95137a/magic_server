package com.qiyuan.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "超商取貨收件人資訊")
public class StorePickupRecipientInfo {
    @Schema(description = "門市代碼", example = "123456")
    private String storeId;

    @Schema(description = "門市名稱", example = "7-11 松山店")
    private String storeName;

    @Schema(description = "門市地址", example = "台北市信義區松山路200號")
    private String storeAddress;

    @Schema(description = "收件人姓名", example = "王小明")
    @Size(max = 5, message = "收件人姓名最多為 5 個字")
    private String recipientName;

    @Schema(description = "收件人手機", example = "0912345678")
    @Pattern(regexp = "^\\d{0,10}$", message = "手機號碼必須是 10 碼以內的純數字")
    private String phone;
}

