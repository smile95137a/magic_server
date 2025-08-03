package com.qiyuan.web.controller.front;

import com.qiyuan.security.controller.GlobalResponseController;
import com.qiyuan.web.dto.PaymentAtmCallbackResult;
import com.qiyuan.web.dto.request.PaymentSuccessRequest;
import com.qiyuan.web.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/payment")
public class PaymentFrontController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentFrontController.class);


    private PaymentService paymentService;

    public PaymentFrontController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/virtual/success")
    public boolean markVirtualPaymentSuccess(@RequestBody @Validated PaymentSuccessRequest request) {
        paymentService.markVirtualPaymentSuccess(request);
        return true;
    }


    @PostMapping("/atmCallback")
    public ResponseEntity<String> atmCallback(
            @RequestParam String Send_Type,
            @RequestParam String result,
            @RequestParam String ret_msg,
            @RequestParam String OrderID,
            @RequestParam String e_money,
            @RequestParam String PayAmount,
            @RequestParam String e_date,
            @RequestParam String e_time,
            @RequestParam String e_orderno,
            @RequestParam String e_payaccount,
            @RequestParam String e_PayInfo,
            @RequestParam String str_check
    ) {
        try {
            paymentService.markAtmPaymentResult(
                    PaymentAtmCallbackResult.builder()
                            .sendType(Send_Type)
                            .result(result)
                            .retMsg(ret_msg)
                            .orderID(OrderID)
                            .eMoney(e_money)
                            .payAmount(PayAmount)
                            .eDate(e_date)
                            .eTime(e_time)
                            .eOrderno(e_orderno)
                            .ePayaccount(e_payaccount)
                            .ePayInfo(e_PayInfo)
                            .strCheck(str_check)
                            .build()
            );
        } catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return ResponseEntity.ok("Received payment callback successfully");

    }




}
