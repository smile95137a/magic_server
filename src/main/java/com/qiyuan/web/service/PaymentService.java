package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.*;
import com.qiyuan.web.dto.request.PaymentSuccessRequest;
import com.qiyuan.web.entity.PaymentTransaction;
import com.qiyuan.web.entity.PaymentTransactionExample;
import com.qiyuan.web.enums.OrderStatus;
import com.qiyuan.web.enums.PayMethodEnum;
import com.qiyuan.web.enums.SourceTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentTransactionMapper paymentTransactionMapper;
    private final GomypayClient gomypayClient;
    private final OrdersMapper ordersMapper;
    private final LanternPurchaseMapper lanternPurchaseMapper;
    private final OfferingPurchaseMapper offeringPurchaseMapper;
    private final MasterServiceRequestMapper masterServiceRequestMapper;
    private final UserMapper userMapper;
    private final GodService godService;

    public PaymentService(PaymentTransactionMapper paymentTransactionMapper, GomypayClient gomypayClient, OrdersMapper ordersMapper, LanternPurchaseMapper lanternPurchaseMapper, OfferingPurchaseMapper offeringPurchaseMapper, MasterServiceRequestMapper masterServiceRequestMapper, UserMapper userMapper, GodService godService) {
        this.paymentTransactionMapper = paymentTransactionMapper;
        this.gomypayClient = gomypayClient;
        this.ordersMapper = ordersMapper;
        this.lanternPurchaseMapper = lanternPurchaseMapper;
        this.offeringPurchaseMapper = offeringPurchaseMapper;
        this.masterServiceRequestMapper = masterServiceRequestMapper;
        this.userMapper = userMapper;
        this.godService = godService;
    }

    @Value("${gomypay.customerNo}")
    private String customerNo;

    @Transactional
    public void markVirtualPaymentSuccess(PaymentSuccessRequest request) {
        String externalOrderNo = request.getExternalOrderNo();
        String sourceType = request.getSourceType();

        // 1. 查詢金流紀錄
        PaymentTransactionExample example = new PaymentTransactionExample();
        example.createCriteria()
                .andIdEqualTo(externalOrderNo)
                .andSourceTypeEqualTo(sourceType);

        List<PaymentTransaction> txList = paymentTransactionMapper.selectByExample(example);

        if (txList.isEmpty()) throw new ApiException("找不到對應的付款紀錄");


        PaymentTransaction tx = txList.get(0);
        // 已經標記為成功，直接略過
        if (!OrderStatus.CREATED.getValue().equalsIgnoreCase(tx.getStatus())) {
            logger.info("付款已處理或非等待狀態, status={}, id={}", tx.getStatus(), tx.getId());
            return;
        }

        // 2. 更新付款狀態為成功
        tx.setStatus(OrderStatus.PAID.getValue());
        tx.setProviderOrderNo(request.getProviderOrderNo());
        tx.setPayMethod(PayMethodEnum.CREDIT_CARD.getCode());
        tx.setUpdateTime(new Date());
        paymentTransactionMapper.updateByPrimaryKey(tx);

        if (StringUtils.equalsIgnoreCase(tx.getSourceType(), SourceTypeEnum.OFFERING.getCode())) {
            godService.processOfferingAfterPayment(tx.getId());
        } else if (StringUtils.equalsIgnoreCase(tx.getSourceType(), SourceTypeEnum.GOD.getCode())) {
            godService.markGodDescendPaid(tx.getId());
        }
    }

//    public PaymentCreateResult createPayment(User user, String externalOrderNo, BigDecimal amount,
//                                             PayMethodEnum payMethod, SourceTypeEnum sourceType, String sourceId) {
//        GomypayRequest req = GomypayRequest.builder()
//                .sendType(payMethod.getApiValue())         // 如 0, 7, ... 依金流API文件
//                .orderNo(externalOrderNo)
//                .amount(amount)
//                .userName(user.getNickname())
//                .phone(user.getPhone())
//                .email(user.getEmail())
//                .memo("線上訂單")                          // 可自訂備註
//                .transMode("1")             // 一般交易 1，分期 2
//                .installment("0")     // 無分期填0
//                .build();
//
//        // === 2. 呼叫金流API，取得回應
//        GomypayResponse resp = gomypayClient.createPayment(req);
//
//        // === 3. 寫入 payment_transaction
//        Date now = DateUtil.getCurrentDate();
//        PaymentTransaction trx = new PaymentTransaction();
//        trx.setId(RandomGenerator.getUUID());
//        trx.setUserId(user.getId());
//        trx.setSourceType(sourceType.getCode());
//        trx.setSourceId(sourceId);
//        trx.setExternalOrderNo(externalOrderNo);
//        trx.setPayMethod(payMethod.getCode());
//        trx.setProvider("gomypay");
//        trx.setAmount(amount);
//        trx.setStatus("pending");
//        trx.setMerchantTradeNo(resp.getTradeNo());
//        trx.setRawData(JsonUtil.toJson(resp));
//        trx.setCreateTime(now);
//        trx.setUpdateTime(now);
//        paymentTransactionMapper.insertSelective(trx);
//
//        // === 4. 回傳付款資訊給前端
//        return PaymentCreateResult.builder()
//                .paymentUrl(resp.getPaymentUrl())
//                .build();
//    }



//    public void handleCallback(PaymentNotifyRequest notify) {
//        // 1. 簽章驗證
//        if (!gomypayClient.verifySign(notify.getStrCheck())) throw new ApiException("驗證失敗");
//
//        // 2. 查找金流紀錄
//        PaymentTransactionExample e = new PaymentTransactionExample();
//        e.createCriteria().andExternalOrderNoEqualTo(notify.getOrderNo());
//        List<PaymentTransaction> trxList = paymentTransactionMapper.selectByExample(e);
//        if (trxList == null || trxList.isEmpty()) throw new ApiException("交易不存在");
//
//        PaymentTransaction trx = trxList.get(0);
//
//        // 3. 狀態判斷與更新
//        String currentStatus = trx.getStatus();
//        String callbackStatus = notify.getStatus();
//
//        // 若已付款成功，再次通知則略過
//        if ("paid".equals(currentStatus)) {
//            return;
//        }
//
//        // 處理付款成功
//        if ("1".equals(callbackStatus)) {
//            trx.setStatus("paid");
//            trx.setMerchantTradeNo(notify.getTradeNo());
//            trx.setRawData(notify.toJson());
//            trx.setUpdateTime(new Date());
//            paymentTransactionMapper.updateByPrimaryKeySelective(trx);
//
//            markSourceAsPaid(trx.getSourceType(), trx.getSourceId());
//            return;
//        }
//
//        // 處理付款失敗
//        if ("0".equals(callbackStatus)) {
//            trx.setStatus("failed");
//            trx.setMerchantTradeNo(notify.getTradeNo());
//            trx.setRawData(notify.toJson());
//            trx.setUpdateTime(new Date());
//            paymentTransactionMapper.updateByPrimaryKeySelective(trx);
//            return;
//        }
//
//        // 其他情境（如果未來金流增加新狀態）
//        trx.setStatus("unknown");
//        trx.setMerchantTradeNo(notify.getTradeNo());
//        trx.setRawData(notify.toJson());
//        trx.setUpdateTime(new Date());
//        paymentTransactionMapper.updateByPrimaryKeySelective(trx);
//    }






}
