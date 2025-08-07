package com.qiyuan.web.controller.admin;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dto.SenderInfoDto;
import com.qiyuan.web.dto.request.CountRequest;
import com.qiyuan.web.entity.SystemConfig;
import com.qiyuan.web.security.RoleExpressions;
import com.qiyuan.web.service.SystemConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/system/config")
@PreAuthorize(RoleExpressions.ONLY_ADMIN)
@Tag(name = "系統設定", description = "管理系統設定值")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @GetMapping("/promotion-lantern")
    @Operation(summary = "查詢推薦點燈的設定", description = "取得目前設定的推薦點燈ID清單")
    public List<String> getPromotionLantern() {
        return systemConfigService.getLanternPromotion();
    }

    @PostMapping("/promotion-lantern")
    @Operation(summary = "更新推薦點燈的設定", description = "將推薦點燈的ID清單儲存至系統設定")
    public boolean updatePromotionLantern(@RequestBody List<String> promotionLanternList) {
        String value = String.join(",", promotionLanternList);
        SystemConfig config = systemConfigService.getSystemConfig("promotion_lantern");
        if (config == null) {
            config = new SystemConfig();
            config.setConfigKey("promotion_lantern");
            config.setConfigValue(value);
            return systemConfigService.insertSystemConfig(config); // 需實作
        } else {
            config.setConfigValue(value);
            return systemConfigService.updateSystemConfig(config); // 需實作
        }
    }

    @GetMapping("/lantern-count")
    @Operation(summary = "查詢要疊加的點燈購買數字", description = "取得要疊加的點燈購買數字")
    public long getPurchaseLanternCount() {
        return systemConfigService.getLanternCount();
    }

    @PostMapping(value = "/lantern-count")
    @Operation(summary = "更新要疊加的點燈購買數字", description = "設定要疊加的點燈購買數字")
    public boolean updatePurchaseLanternCount(@RequestBody CountRequest request) {
        if (request == null) throw new ApiException("更新失敗，請輸入數字");
        SystemConfig config = systemConfigService.getSystemConfig("purchase_lantern_count");

        if (config == null) {
            config = new SystemConfig();
            config.setConfigKey("purchase_lantern_count");
            config.setConfigValue(String.valueOf(request.getCount()));
            return systemConfigService.insertSystemConfig(config);
        } else {
            config.setConfigValue(String.valueOf(request.getCount()));
            return systemConfigService.updateSystemConfig(config);
        }
    }

    @GetMapping("/sender-info")
    public SenderInfoDto getSenderInfo() {
        return systemConfigService.getSenderInfo();
    }

    @PostMapping("/sender-info")
    public boolean updateSenderInfo(@Validated @RequestBody SenderInfoDto req) {
        return systemConfigService.updateSenderInfo(req);
    }

}

