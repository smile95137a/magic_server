package com.qiyuan.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanternPurchaseInfo implements Serializable {
    @NotBlank(message = "姓名不可為空")
    @Size(min = 1, message = "姓名至少需 1 個字")
    private String name;

    @NotBlank(message = "生日不可為空")
    @Pattern(
            regexp = "^(\\d{4})[-/](0[1-9]|1[0-2])[-/](0[1-9]|[12][0-9]|3[01])$",
            message = "生日格式須為 yyyy-MM-dd 或 yyyy/MM/dd"
    )
    private String birthday;

    @NotBlank(message = "留言不可為空")
    @Size(min = 1, message = "留言至少需 1 個字")
    private String message;

}
