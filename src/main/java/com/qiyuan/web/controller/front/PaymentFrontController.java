package com.qiyuan.web.controller.front;

import com.qiyuan.web.dto.request.PaymentSuccessRequest;
import com.qiyuan.web.service.PaymentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/payment")
public class PaymentFrontController {

    private PaymentService paymentService;

    public PaymentFrontController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/virtual/success")
    public boolean markVirtualPaymentSuccess(@RequestBody @Validated PaymentSuccessRequest request) {
        paymentService.markVirtualPaymentSuccess(request);
        return true;
    }

    //    @PostMapping("/notify")
//    public String notify(@RequestBody PaymentNotifyRequest notify) {
//        paymentService.handleCallback(notify);
//        return "OK";
//    }



}
