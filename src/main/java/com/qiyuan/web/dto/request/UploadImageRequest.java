package com.qiyuan.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UploadImageRequest {
    @NotNull
    private Integer productId;

    @NotBlank
    @Pattern(regexp = "^(main|gallery|description)$", message = "圖片類型錯誤")
    private String type;

    @NotNull
    private MultipartFile file;
}
