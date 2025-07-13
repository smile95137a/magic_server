package com.qiyuan.web.controller.admin;

import com.qiyuan.web.dto.request.ModifyBannerRequest;
import com.qiyuan.web.dto.request.NewBannerRequest;
import com.qiyuan.web.dto.response.BannerAdminVO;
import com.qiyuan.web.security.RoleExpressions;
import com.qiyuan.web.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/banner")
@PreAuthorize(RoleExpressions.ONLY_ADMIN)
@Tag(name = "橫幅管理", description = "後台橫幅(Banner)管理操作")
public class BannerAdminController {

    @Autowired
    private BannerService bannerService;

    @PostMapping("/{type}")
    @Operation(summary = "查詢指定類型橫幅", description = "根據類型(type)查詢所有橫幅")
    public List<BannerAdminVO> getBannerByType(
            @Parameter(description = "橫幅類型，必須為一個字元", example = "A")
            @PathVariable("type") @Pattern(regexp = "^[a-zA-Z0-9]$", message = "type 必須為一個英數字元") String type) {
        return bannerService.getAllBannerByType(type);
    }

    @PostMapping("/{id}")
    @Operation(summary = "查詢單一橫幅", description = "根據 id 查詢單一橫幅資料")
    public BannerAdminVO getBannerById(
            @Parameter(description = "橫幅ID", example = "55")
            @PathVariable("id") Integer id) {
        return bannerService.getBannerById(id);
    }


    @PostMapping("/add")
    @Operation(summary = "新增橫幅", description = "新增一筆橫幅資料")
    public boolean addBanner(@Validated @RequestBody NewBannerRequest banner) {
        return bannerService.addNewBanner(banner);
    }

    @PostMapping("/modify")
    @Operation(summary = "修改橫幅", description = "修改現有橫幅資料")
    public boolean modifyBanner(@Validated @RequestBody ModifyBannerRequest banner) {
        return bannerService.modifyBanner(banner);
    }
}