package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.*;
import com.qiyuan.web.dto.PaymentNotifyDTO;
import com.qiyuan.web.dto.request.GomypayRequest;
import com.qiyuan.web.dto.request.PaymentNotifyRequest;
import com.qiyuan.web.dto.response.GomypayResponse;
import com.qiyuan.web.dto.response.PaymentCreateResult;
import com.qiyuan.web.entity.PaymentTransaction;
import com.qiyuan.web.entity.User;
import com.qiyuan.web.entity.example.PaymentTransactionExample;
import com.qiyuan.web.enums.PayMethodEnum;
import com.qiyuan.web.enums.SourceTypeEnum;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.JsonUtil;
import com.qiyuan.web.util.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentTransactionMapper paymentTransactionMapper;
    private final GomypayClient gomypayClient;
    private final OrdersMapper ordersMapper;
    private final LanternPurchaseMapper lanternPurchaseMapper;
    private final OfferingPurchaseMapper offeringPurchaseMapper;
    private final MasterServiceRequestMapper masterServiceRequestMapper;
    private final UserMapper userMapper;

    public PaymentService(PaymentTransactionMapper paymentTransactionMapper, GomypayClient gomypayClient, OrdersMapper ordersMapper, LanternPurchaseMapper lanternPurchaseMapper, OfferingPurchaseMapper offeringPurchaseMapper, MasterServiceRequestMapper masterServiceRequestMapper, UserMapper userMapper) {
        this.paymentTransactionMapper = paymentTransactionMapper;
        this.gomypayClient = gomypayClient;
        this.ordersMapper = ordersMapper;
        this.lanternPurchaseMapper = lanternPurchaseMapper;
        this.offeringPurchaseMapper = offeringPurchaseMapper;
        this.masterServiceRequestMapper = masterServiceRequestMapper;
        this.userMapper = userMapper;
    }

    @Value("${gomypay.customerNo}")
    private String customerNo;

    public PaymentCreateResult createPayment(User user, String externalOrderNo, BigDecimal amount,
                                             PayMethodEnum payMethod, SourceTypeEnum sourceType, String sourceId) {
        GomypayRequest req = GomypayRequest.builder()
                .sendType(payMethod.getApiValue())         // 如 0, 7, ... 依金流API文件
                .orderNo(externalOrderNo)
                .amount(amount)
                .userName(user.getNickname())
                .phone(user.getPhone())
                .email(user.getEmail())
                .memo("線上訂單")                          // 可自訂備註
                .transMode("1")             // 一般交易 1，分期 2
                .installment("0")     // 無分期填0
                .build();

        // === 2. 呼叫金流API，取得回應
        GomypayResponse resp = gomypayClient.createPayment(req);

        // === 3. 寫入 payment_transaction
        Date now = DateUtil.getCurrentDate();
        PaymentTransaction trx = new PaymentTransaction();
        trx.setId(RandomGenerator.getUUID());
        trx.setUserId(user.getId());
        trx.setSourceType(sourceType.getCode());
        trx.setSourceId(sourceId);
        trx.setExternalOrderNo(externalOrderNo);
        trx.setPayMethod(payMethod.getCode());
        trx.setProvider("gomypay");
        trx.setAmount(amount);
        trx.setStatus("pending");
        trx.setMerchantTradeNo(resp.getTradeNo());
        trx.setRawData(JsonUtil.toJson(resp));
        trx.setCreateTime(now);
        trx.setUpdateTime(now);
        paymentTransactionMapper.insertSelective(trx);

        // === 4. 回傳付款資訊給前端
        return PaymentCreateResult.builder()
                .paymentUrl(resp.getPaymentUrl())
                .build();
    }



    public void handleCallback(PaymentNotifyRequest notify) {
        // 1. 簽章驗證
        if (!gomypayClient.verifySign(notify.getStrCheck())) throw new ApiException("驗證失敗");

        // 2. 查找金流紀錄
        PaymentTransactionExample e = new PaymentTransactionExample();
        e.createCriteria().andExternalOrderNoEqualTo(notify.getOrderNo());
        List<PaymentTransaction> trxList = paymentTransactionMapper.selectByExample(e);
        if (trxList == null || trxList.isEmpty()) throw new ApiException("交易不存在");

        PaymentTransaction trx = trxList.get(0);

        // 3. 狀態判斷與更新
        String currentStatus = trx.getStatus();
        String callbackStatus = notify.getStatus();

        // 若已付款成功，再次通知則略過
        if ("paid".equals(currentStatus)) {
            return;
        }

        // 處理付款成功
        if ("1".equals(callbackStatus)) {
            trx.setStatus("paid");
            trx.setMerchantTradeNo(notify.getTradeNo());
            trx.setRawData(notify.toJson());
            trx.setUpdateTime(new Date());
            paymentTransactionMapper.updateByPrimaryKeySelective(trx);

            markSourceAsPaid(trx.getSourceType(), trx.getSourceId());
            return;
        }

        // 處理付款失敗
        if ("0".equals(callbackStatus)) {
            trx.setStatus("failed");
            trx.setMerchantTradeNo(notify.getTradeNo());
            trx.setRawData(notify.toJson());
            trx.setUpdateTime(new Date());
            paymentTransactionMapper.updateByPrimaryKeySelective(trx);
            return;
        }

        // 其他情境（如果未來金流增加新狀態）
        trx.setStatus("unknown");
        trx.setMerchantTradeNo(notify.getTradeNo());
        trx.setRawData(notify.toJson());
        trx.setUpdateTime(new Date());
        paymentTransactionMapper.updateByPrimaryKeySelective(trx);
    }

    private void markSourceAsPaid(String sourceType, String sourceId) {
        // 強烈建議 sourceId 用 String 存（UUID），不用轉 Integer
        SourceTypeEnum type = SourceTypeEnum.fromCode(sourceType);

        switch (type) {
            case REAL: // 假設你的 SourceTypeEnum 實體商品訂單寫 REAL（M 也行）
                // 只有實體商品才要 mark paid，這裡通常會把 orders.status 改成 "paid" 或 "待出貨"
//                ordersMapper.updateStatusToPaid(sourceId);  // 你要補一個 updateStatusToPaid 方法
                break;
            case LANTERN:
            case OFFERING:
            case MASTER_SERVICE:
                break;
            default:
                throw new ApiException("不支援的來源類型: " + sourceType);
        }
    }






}
