package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.*;
import com.qiyuan.web.dto.PaymentAtmCallbackResult;
import com.qiyuan.web.dto.request.PaymentSuccessRequest;
import com.qiyuan.web.entity.Orders;
import com.qiyuan.web.entity.PaymentTransaction;
import com.qiyuan.web.entity.PaymentTransactionExample;
import com.qiyuan.web.entity.example.OrdersExample;
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

    /**
     * 虛擬商品的信用卡付款(不含實體商品)
     * @param request
     */
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

    /**
     * ATM轉帳的付款(目前僅有實體)
     * @param result
     */
    @Transactional
    public void markAtmPaymentResult(PaymentAtmCallbackResult result) {
        String paymentId = result.getEOrderno();
        PaymentTransaction tx = paymentTransactionMapper.selectByPrimaryKey(paymentId);
        tx.setProviderOrderNo(result.getOrderID());
        tx.setRawData(result.toString());
        tx.setUpdateTime(new Date());

        if ("1".equals(result.getResult())) {
            tx.setStatus(OrderStatus.PAID.getValue());

            if (tx.getSourceType().equals(SourceTypeEnum.REAL.getCode())) {
                OrdersExample e = new OrdersExample();
                e.createCriteria().andExternalOrderNoEqualTo(paymentId);
                List<Orders> orders = ordersMapper.selectByExample(e);
                Orders target = orders.get(0);
                target.setStatus(OrderStatus.PAID.getValue());
                target.setPaid(true);
                ordersMapper.updateByPrimaryKey(target);
            }
        }
        paymentTransactionMapper.updateByPrimaryKey(tx);
    }




}
