package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.*;
import com.qiyuan.web.dto.InvoiceDTO;
import com.qiyuan.web.dto.QapItemVO;
import com.qiyuan.web.dto.request.MasterReservationFilter;
import com.qiyuan.web.dto.response.AddMasterRequestResponse;
import com.qiyuan.web.dto.response.MasterServiceRequestVO;
import com.qiyuan.web.entity.*;
import com.qiyuan.web.entity.example.MasterExample;
import com.qiyuan.web.entity.example.MasterServiceRequestExample;
import com.qiyuan.web.enums.InvoiceType;
import com.qiyuan.web.enums.OrderStatus;
import com.qiyuan.web.enums.SourceTypeEnum;
import com.qiyuan.web.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MasterRequestService {

    private static final Logger logger = LoggerFactory.getLogger(MasterRequestService.class);

    private final MasterServiceRequestMapper mapper;

    private final MasterMapper masterMapper;

    private final PaymentTransactionMapper paymentTransactionMapper;

    private final UserMapper userMapper;

    private VirtualOrdersMapper virtualOrdersMapper;
    private VirtualOrderItemMapper virtualOrderItemMapper;

    public MasterRequestService(MasterServiceRequestMapper mapper, MasterMapper masterMapper, PaymentTransactionMapper paymentTransactionMapper, UserMapper userMapper, VirtualOrdersMapper virtualOrdersMapper, VirtualOrderItemMapper virtualOrderItemMapper) {
        this.mapper = mapper;
        this.masterMapper = masterMapper;
        this.paymentTransactionMapper = paymentTransactionMapper;
        this.userMapper = userMapper;
        this.virtualOrdersMapper = virtualOrdersMapper;
        this.virtualOrderItemMapper = virtualOrderItemMapper;
    }

    public List<MasterServiceRequestVO> getMasterReservationByFilter(MasterReservationFilter filter) {
        MasterServiceRequestExample example = new MasterServiceRequestExample();
        MasterServiceRequestExample.Criteria criteria = example.createCriteria();
        if (filter.getMasterCode() != null && !filter.getMasterCode().isEmpty()) {
            criteria.andMasterCodeEqualTo(filter.getMasterCode());
        }
        if (filter.getStartTime() != null) {
            criteria.andCreateTimeGreaterThanOrEqualTo(filter.getStartTime());
        }
        if (filter.getEndTime() != null) {
            criteria.andCreateTimeLessThanOrEqualTo(DateUtil.getEndOfDate(filter.getEndTime()));
        }

        if (filter.getOrderId() != null) {
            criteria.andSerialEqualTo(parseOrderIdToTid(filter.getOrderId()));
        }
        example.setOrderByClause("serial DESC");
        List<MasterServiceRequest> list = mapper.selectByExample(example);
        return list.stream()
                .map(r -> MasterServiceRequestVO.builder()
                        .serial(getOrderIdFromTid(r.getSerial()))
                        .name(r.getName())
                        .masterCode(r.getMasterCode())
                        .service(r.getService())
                        .email(r.getEmail())
                        .lineId(r.getLineId())
                        .note(r.getNote())
                        .phone(r.getPhone())
                        .createTime(r.getCreateTime())
                        .build()
                ).collect(Collectors.toList());

    }


    @Transactional
    public AddMasterRequestResponse addMasterRequest(com.qiyuan.web.dto.request.MasterServiceRequest req) {
        MasterExample e = new MasterExample();
        e.createCriteria().andStatusEqualTo(true).andCodeEqualTo(req.getMasterCode());
        List<Master> masters = masterMapper.selectByExample(e);
        if (masters == null || masters.isEmpty()) {
            throw new ApiException("請選擇老師！");
        }

        String orderId = RandomGenerator.getUUID().toLowerCase(Locale.ROOT);
        String paymentId = orderId.substring(0, 25);
        Master master = masters.get(0);
        List<QapItemVO> qapItem = JsonUtil.fromJsonList(master.getServicesJson(), QapItemVO.class);
        Optional<QapItemVO> itemO = qapItem.stream().filter(q -> StringUtils.equals(q.getTitle(), req.getServiceItem())).findFirst();

        if (!itemO.isPresent()) throw new ApiException("查無該服務項目，請重新選擇");
        QapItemVO item = itemO.get();

        MasterServiceRequest request = MasterServiceRequest.builder()
                .masterCode(master.getCode())
                .service(item.getTitle())
                .email(req.getCustomerEmail())
                .lineId(req.getCustomerLine())
                .name(req.getCustomerName())
                .note(req.getNote())
                .phone(req.getCustomerPhone())
                .externalOrderNo(paymentId)
                .build();

        if (mapper.insertSelective(request) == 0) {
            throw new ApiException("系統發生錯誤，請聯繫客服！");
        }

        String userId = null;
        Authentication authentication = SecurityUtils.getAuthentication();
        User user = userMapper.selectByUsername(authentication.getName());
        userId = user.getId();

        InvoiceDTO invoiceDTO = null;
        try {
            invoiceDTO = JsonUtil.fromJson(user.getReceipt(), InvoiceDTO.class);
        } catch (Exception ex) {
            throw new ApiException("請先至會員中心填寫發票資訊");
        }
        if (invoiceDTO == null || (!invoiceDTO.getType().equals(InvoiceType.CITIZEN) && StringUtils.isBlank(invoiceDTO.getValue()))) {
            throw new ApiException("請先至會員中心填寫發票資訊");
        }

        BigDecimal total = BigDecimal.valueOf(item.getPrice());
        
        // 新增訂單
        VirtualOrders orders = VirtualOrders.builder()
                .id(orderId)
                .externalOrderNo(paymentId)
                .userId(userId)
                .totalAmount(total)
                .status(OrderStatus.CREATED.getValue())
                .invoiceType(invoiceDTO.getType().getValue())
                .invoiceTarget(invoiceDTO.getValue())
                .sourceType(SourceTypeEnum.MASTER_SERVICE.getCode())
                .createTime(new Date())
                .updateTime(new Date())
                .build();

        virtualOrdersMapper.insertSelective(orders);

        VirtualOrderItem orderItem = VirtualOrderItem.builder()
                .orderId(orderId)
                .description(String.format("%s-%s", SourceTypeEnum.MASTER_SERVICE.getLabel(), item.getTitle()))
                .quantity(1)
                .unitPrice(total)
                .amount(total)
                .createTime(new Date())
                .build();

        virtualOrderItemMapper.insertSelective(orderItem);

        paymentTransactionMapper.insertSelective(PaymentTransaction.builder()
                .id(paymentId)
                .userId(userId)
                .sourceType(SourceTypeEnum.MASTER_SERVICE.getCode())
                .amount(total)
                .status(OrderStatus.CREATED.getValue())
                .payMethod(req.getPaymentMethod())
                .createTime(DateUtil.getCurrentDate())
                .build());

        String serial = String.format("%s-%s", req.getMasterCode(), getOrderIdFromTid(request.getSerial()));
        logger.info("[老師親算] 成功新建訂單 => 編號: {}, 老師代號:{}, 流水號: {}, 金流訂單編號: {}", serial, req.getMasterCode(), request.getSerial(), paymentId);

        return AddMasterRequestResponse.builder()
                .orderNo(serial)
                .externalPaymentNo(paymentId)
                .price(BigDecimal.valueOf(item.getPrice()))
                .build();
    }

    public String getOrderIdFromTid(int i) {
        return Base36Util.encode4digit(i);
    }



    public int parseOrderIdToTid(String orderId) {
        return Base36Util.decode4digit(orderId);
    }

}
