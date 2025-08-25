package com.qiyuan.web.schedule;

import com.qiyuan.web.service.GodInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class GodInfoResetScheduler {

    private final GodInfoService godInfoService;

    /**
     * 每天 00:00 執行，清空所有 offering_list
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetOfferingsAtMidnight() {
        log.info("開始執行每日供品清空任務（00:00）...");
        int affected = godInfoService.resetAllOfferingList();
        log.info("完成每日供品清空任務（00:00），影響筆數：{}", affected);
    }
}
