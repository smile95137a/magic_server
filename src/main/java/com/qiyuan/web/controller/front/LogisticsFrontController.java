package com.qiyuan.web.controller.front;

import com.qiyuan.web.service.GomypayLogisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logistics")
@Tag(name = "物流回傳 API")
@RequiredArgsConstructor
public class LogisticsFrontController {

    private static final Logger logger = LoggerFactory.getLogger(LogisticsFrontController.class);
    private final  GomypayLogisticsService logisticsService;

    @Operation(summary = "物流出貨 Callback 接口")
    @PostMapping(value = "/callback", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> handleCallback(
            @RequestParam("EshopId") String eshopId,
            @RequestParam("OrderNo") String orderNo,
            @RequestParam("status") String status
    ) {
        logger.info("收到物流 callback：EshopId={}, OrderNo={}, status={}", eshopId, orderNo, status);
        try {
            logisticsService.updateLogisticsStatus(eshopId, orderNo, status);
            return ResponseEntity.ok(eshopId + "," + orderNo + ",000");
        } catch (Exception e) {
            logger.error("處理物流 callback 發生錯誤", e);
            return ResponseEntity.ok(eshopId + "," + orderNo + ",999");
        }
    }
}
