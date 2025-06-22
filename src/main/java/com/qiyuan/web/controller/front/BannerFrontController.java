package com.qiyuan.web.controller.front;

import com.qiyuan.web.service.BannerService;
import com.qiyuan.web.dto.response.BannerVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banner")
@Tag(name = "前台 Banner ", description = "前台 Banner 查詢")
public class BannerFrontController {

    @Autowired
    private BannerService bannerService;

    @Operation(summary = "取得指定類型的 banner 列表", description = "依照 type 取得前台顯示的 banner ")
    @PostMapping(value = "/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BannerVO> getAvailableBannerByType(
            @Parameter(description = "banner 類型，只能為一個英數字元 (如：A, 1, b)", example = "A")
            @PathVariable("type")
            @Pattern(regexp = "^[a-zA-Z0-9]$", message = "type 必須為一個英數字元") String type) {
        return bannerService.getAvailableBannerByType(type);
    }
}
