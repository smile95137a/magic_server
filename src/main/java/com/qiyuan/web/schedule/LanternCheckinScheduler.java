package com.qiyuan.web.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.qiyuan.web.service.LanternPurchaseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class LanternCheckinScheduler {

    private final LanternPurchaseService lanternPurchaseService;

 // 每天 00:00 執行
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetCheckinsAtMidnight() {
        log.info("開始執行每日簽到重置任務（00:00）...");
        lanternPurchaseService.resetAllCheckins();
        log.info("完成每日簽到重置任務（00:00）");
    }
    
    
}
