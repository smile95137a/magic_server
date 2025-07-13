package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dto.request.GomypayRequest;
import com.qiyuan.web.dto.request.PaymentNotifyRequest;
import com.qiyuan.web.dto.response.GomypayResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
@Service
public class GomypayClient {

    @Value("${gomypay.customerNo}")
    private String customerNo;

    @Value("${gomypay.pwd}")
    private String apiPassword;

    @Value("${gomypay.api}")
    private String apiUrl;

    @Value("${ap.backend}")
    private String backendApi;

    public GomypayResponse createPayment(GomypayRequest req) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        // 必填參數
        params.add("Send_Type", req.getSendType()); // 支付方式 0=信用卡、1=銀聯、2=超商條碼、3=WebATM、4=虛擬帳號、6=超商代碼、7=LinePay
        params.add("Pay_Mode_No", "2");            // 固定2
        params.add("CustomerId", customerNo);       // 商店代號
        params.add("Order_No", req.getOrderNo());   // 訂單編號（唯一）
        params.add("Amount", req.getAmount().toString());
        params.add("TransCode", "00");              // 固定00（授權）
        params.add("Buyer_Name", req.getUserName());
        params.add("Buyer_Telm", req.getPhone());
        params.add("Buyer_Mail", req.getEmail());
        params.add("Buyer_Memo", req.getMemo());
        params.add("TransMode", req.getTransMode());        // 一般交易 1 / 分期 2
        params.add("Installment", req.getInstallment());    // 期數，無分期填 0

        params.add("e_return", "1");
        params.add("Callback_Url", backendApi + "/api/payment/notify");
        params.add("Str_Check", apiPassword);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<GomypayResponse> response =
                restTemplate.postForEntity(apiUrl, entity, GomypayResponse.class);

        if (response.getBody() == null || !"0000".equals(response.getBody().getCode())) {
            String msg = response.getBody() != null ? response.getBody().getMsg() : "未知錯誤";
            throw new ApiException("建立金流交易失敗：" + msg);
        }
        return response.getBody();
    }

    public boolean verifySign(String strCheck) {
        return apiPassword.equals(strCheck);
    }
}

