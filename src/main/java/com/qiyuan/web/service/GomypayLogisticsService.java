package com.qiyuan.web.service;

import com.qiyuan.web.config.GomypayLogisticsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GomypayLogisticsService {

    private final RestTemplate restTemplate;
    private final GomypayLogisticsProperties props;


//    /**
//     * 成立超商物流訂單
//     */
//    public String createStorePickupOrder(StorePickupRequest request) {
//        // 封裝參數、送出 POST 到 props.getStoreCreateUri()
//    }
//
//    /**
//     * 成立宅配物流訂單（順豐）
//     */
//    public String createExpressDeliveryOrder(ExpressDeliveryRequest request) {
//        // 封裝參數、送出 POST 到 props.getExpressCreateUri()
//    }
}
