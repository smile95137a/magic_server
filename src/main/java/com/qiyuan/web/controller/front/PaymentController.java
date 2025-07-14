package com.qiyuan.web.controller.front;

import com.qiyuan.web.dto.request.PaymentNotifyRequest;
import com.qiyuan.web.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/notify")
    public String notify(@RequestBody PaymentNotifyRequest notify) {
        paymentService.handleCallback(notify);
        return "OK";
    }

}
