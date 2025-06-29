package com.qiyuan.web.controller.admin;

import com.qiyuan.web.entity.SystemConfig;
import com.qiyuan.web.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/config")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @GetMapping("/promotion-lantern")
    public List<String> getPromotionLantern() {
        return systemConfigService.getLanternPromotion();
    }

    @PostMapping("/promotion-lantern")
    public boolean updatePromotionLantern(@RequestBody List<String> promotionLanternList) {
        String value = String.join(",", promotionLanternList);
        SystemConfig config = systemConfigService.getSystemConfig("promotion_lantern");
        if (config == null) {
            // 新增
            config = new SystemConfig();
            config.setConfigKey("promotion_lantern");
            config.setConfigValue(value);
            return systemConfigService.insertSystemConfig(config); // 需實作
        } else {
            // 更新
            config.setConfigValue(value);
            return systemConfigService.updateSystemConfig(config); // 需實作
        }
    }

    @GetMapping("/lantern-count")
    public long getPurchaseLanternCount() {
        return systemConfigService.getLanternCount();
    }

    @PostMapping("/lantern-count")
    public boolean updatePurchaseLanternCount(@RequestParam("count") long count) {
        SystemConfig config = systemConfigService.getSystemConfig("purchase_lantern_count");
        if (config == null) {
            config = new SystemConfig();
            config.setConfigKey("purchase_lantern_count");
            config.setConfigValue(String.valueOf(count));
            return systemConfigService.insertSystemConfig(config); // 需實作
        } else {
            config.setConfigValue(String.valueOf(count));
            return systemConfigService.updateSystemConfig(config); // 需實作
        }
    }
}

