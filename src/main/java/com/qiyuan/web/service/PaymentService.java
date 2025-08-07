package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.*;
import com.qiyuan.web.dto.PaymentAtmCallbackResult;
import com.qiyuan.web.dto.request.PaymentSuccessRequest;
import com.qiyuan.web.entity.Orders;
import com.qiyuan.web.entity.PaymentTransaction;
import com.qiyuan.web.entity.PaymentTransactionExample;
import com.qiyuan.web.entity.VirtualOrders;
import com.qiyuan.web.entity.example.OrdersExample;
import com.qiyuan.web.entity.example.VirtualOrdersExample;
import com.qiyuan.web.enums.OrderStatus;
import com.qiyuan.web.enums.PayMethodEnum;
import com.qiyuan.web.enums.SourceTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentTransactionMapper paymentTransactionMapper;
    private final InvoiceService invoiceService;
    private final GomypayClient gomypayClient;
    private final OrdersMapper ordersMapper;
    private final VirtualOrdersMapper virtualOrdersMapper;
    private final LanternPurchaseMapper lanternPurchaseMapper;
    private final OfferingPurchaseMapper offeringPurchaseMapper;
    private final MasterServiceRequestMapper masterServiceRequestMapper;
    private final UserMapper userMapper;
    private final GodService godService;

    public PaymentService(PaymentTransactionMapper paymentTransactionMapper, InvoiceService invoiceService, GomypayClient gomypayClient, OrdersMapper ordersMapper, VirtualOrdersMapper virtualOrdersMapper, LanternPurchaseMapper lanternPurchaseMapper, OfferingPurchaseMapper offeringPurchaseMapper, MasterServiceRequestMapper masterServiceRequestMapper, UserMapper userMapper, GodService godService) {
        this.paymentTransactionMapper = paymentTransactionMapper;
        this.invoiceService = invoiceService;
        this.gomypayClient = gomypayClient;
        this.ordersMapper = ordersMapper;
        this.virtualOrdersMapper = virtualOrdersMapper;
        this.lanternPurchaseMapper = lanternPurchaseMapper;
        this.offeringPurchaseMapper = offeringPurchaseMapper;
        this.masterServiceRequestMapper = masterServiceRequestMapper;
        this.userMapper = userMapper;
        this.godService = godService;
    }

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

        // 更新訂單狀態
        VirtualOrdersExample voe = new VirtualOrdersExample();
        voe.createCriteria().andExternalOrderNoEqualTo(externalOrderNo);
        List<VirtualOrders> virtualOrders = virtualOrdersMapper.selectByExample(voe);
        virtualOrders.get(0).setStatus(OrderStatus.PAID.getValue());
        virtualOrdersMapper.updateByPrimaryKeySelective(virtualOrders.get(0));


        try {
            // 發票開立
            invoiceService.issueInvoice(externalOrderNo);
        } catch (Exception e) {
            logger.error("虛擬商品訂單 {} 金流單號 {} 付款完成，但發票開立失敗!!", virtualOrders.get(0).getId(), externalOrderNo);
        }

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
        String orderId = null;

        if ("1".equals(result.getResult())) {
            tx.setStatus(OrderStatus.PAID.getValue());

            if (tx.getSourceType().equals(SourceTypeEnum.REAL.getCode())) {
                OrdersExample e = new OrdersExample();
                e.createCriteria().andExternalOrderNoEqualTo(paymentId);
                List<Orders> orders = ordersMapper.selectByExample(e);
                Orders target = orders.get(0);
                orderId = target.getId();
                target.setStatus(OrderStatus.PAID.getValue());
                target.setPaid(true);
                ordersMapper.updateByPrimaryKey(target);
            }
        }

        paymentTransactionMapper.updateByPrimaryKey(tx);


        try {
            // 發票開立
            invoiceService.issueInvoice(paymentId);
        } catch (Exception e) {
            logger.error("商城訂單 {} 金流單號 {} 付款完成，但發票開立失敗!!", StringUtils.defaultString(orderId), paymentId);
        }
    }




}
