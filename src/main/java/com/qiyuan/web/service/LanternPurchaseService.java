package com.qiyuan.web.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import com.qiyuan.web.dao.*;
import com.qiyuan.web.dto.InvoiceDTO;
import com.qiyuan.web.dto.LanternAdminRecord;
import com.qiyuan.web.dto.response.PaymentNoVO;
import com.qiyuan.web.entity.*;
import com.qiyuan.web.enums.SourceTypeEnum;
import com.qiyuan.web.util.SecurityUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dto.LanternBlessingDTO;
import com.qiyuan.web.dto.request.LanternPurchaseInfo;
import com.qiyuan.web.dto.request.LanternPurchaseRequest;
import com.qiyuan.web.dto.request.RecordPeriodRequest;
import com.qiyuan.web.dto.response.LanternBlessingVO;
import com.qiyuan.web.dto.response.LanternPriceVO;
import com.qiyuan.web.dto.response.RecordVO;
import com.qiyuan.web.entity.example.LanternExample;
import com.qiyuan.web.entity.example.LanternPurchaseExample;
import com.qiyuan.web.enums.OrderStatus;
import com.qiyuan.web.enums.RecordItem;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.JsonUtil;
import com.qiyuan.web.util.RandomGenerator;

@Service
public class LanternPurchaseService {
    private LanternPurchaseMapper lanternPurchaseMapper;

    private SystemConfigService systemConfigService;

    private LanternMapper lanternMapper;

    private UserMapper userMapper;

    private PaymentService paymentService;

    private PaymentTransactionMapper paymentTransactionMapper;

    private VirtualOrdersMapper virtualOrdersMapper;
    private VirtualOrderItemMapper virtualOrderItemMapper;

    public LanternPurchaseService(LanternPurchaseMapper lanternPurchaseMapper, SystemConfigService systemConfigService, LanternMapper lanternMapper, UserMapper userMapper, PaymentService paymentService, PaymentTransactionMapper paymentTransactionMapper, VirtualOrdersMapper virtualOrdersMapper, VirtualOrderItemMapper virtualOrderItemMapper) {
        this.lanternPurchaseMapper = lanternPurchaseMapper;
        this.systemConfigService = systemConfigService;
        this.lanternMapper = lanternMapper;
        this.userMapper = userMapper;
        this.paymentService = paymentService;
        this.paymentTransactionMapper = paymentTransactionMapper;
        this.virtualOrdersMapper = virtualOrdersMapper;
        this.virtualOrderItemMapper = virtualOrderItemMapper;
    }

    public List<LanternPurchase> getByLanternId(String lanternId) {
        LanternPurchaseExample e = new LanternPurchaseExample();
        e.createCriteria().andLanternIdEqualTo(lanternId);
        return lanternPurchaseMapper.selectByExample(e);
    }

    public long countByLanternId(String lanternId) {
        LanternPurchaseExample e = new LanternPurchaseExample();
        e.createCriteria().andLanternIdEqualTo(lanternId);
        return lanternPurchaseMapper.countByExample(e);
    }

    public List<RecordVO> getLanternPurchaseList(RecordPeriodRequest req) {
        Date startTime = req.getStartTime();
        Date endTime = DateUtil.getEndOfDate(req.getEndTime());
        List<LanternRecord> lanternRecords = lanternPurchaseMapper.selectRecordsByPeriod(startTime, endTime, 100);

        return lanternRecords.stream()
                .map(l -> RecordVO.builder()
                        .date(DateFormatUtils.format(l.getCreateTime(), "yyyy/MM/dd HH:mm"))
                        .item(RecordItem.Lantern.getLabel())
                        .content(l.getLanternName())
                        .build())
                .collect(Collectors.toList());
    }

    public List<LanternAdminRecord> getAdminLanternPurchaseList(RecordPeriodRequest req) {
        Date startTime = req.getStartTime();
        Date endTime = DateUtil.getEndOfDate(req.getEndTime());
        return lanternPurchaseMapper.selectAdminRecordsByPeriod(startTime, endTime);
    }

    public List<LanternBlessingDTO> getMyActiveLanterns() {
        String username = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(username);
        return lanternPurchaseMapper.selectMyActiveLanterns(user.getId());
    }

    public List<LanternBlessingVO> getLatestLanternBlessing(int num) {
        LanternPurchaseExample e = new LanternPurchaseExample();

        e.setOrderByClause("create_time DESC");

        List<LanternBlessingDTO> list = lanternPurchaseMapper.selectLimitByExample(e, num);
        return lanternBlessingDto2VO(list);
    }

    public List<LanternBlessingVO> getRankLanternBlessing(int num) {
        LanternPurchaseExample e = new LanternPurchaseExample();
        e.setOrderByClause("blessing_times DESC");
        List<LanternBlessingDTO> list = lanternPurchaseMapper.selectLimitByExample(e, num);

        return lanternBlessingDto2VO(list);
    }

    public List<LanternBlessingVO> getRecommendation(int num) {
        LanternPurchaseExample e = new LanternPurchaseExample();
        List<String> lanternIds = systemConfigService.getLanternPromotion();
        if (!lanternIds.isEmpty()) {
            e.createCriteria().andLanternIdIn(lanternIds);
        }
        e.setOrderByClause("create_time ASC");
        List<LanternBlessingDTO> list = lanternPurchaseMapper.selectDistinctLimitByExample(e, num);

        if (list.isEmpty()) {
            LanternPurchaseExample e3 = new LanternPurchaseExample();
            list = lanternPurchaseMapper.selectDistinctLimitByExample(e3, num);
        }

        return lanternBlessingDto2VO(list);
    }

    private List<LanternBlessingVO> lanternBlessingDto2VO(List<LanternBlessingDTO> list) {
        if (!list.isEmpty()) {
            return list.stream()
                    .map(l -> LanternBlessingVO.builder()
                            .blessing(l.getBlessingTimes())
                            .name(l.getName())
                            .lanternCode(l.getLanternCode())
                            .createTime(l.getCreateTime())
                            .message(l.getMessage())
                            .build()
                    )
                    .collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

    @Transactional
    public PaymentNoVO addLanternPurchaseRecord(LanternPurchaseRequest req) {
        List<LanternPurchaseInfo> purchaseList = req.getList();
        if (purchaseList == null || purchaseList.isEmpty()) throw new ApiException("請輸入至少一筆點燈購買資訊");

        String username = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(username);
        String userId = user.getId();
        String lanternCode = req.getLanternCode();

        LanternExample e = new LanternExample();
        e.createCriteria().andIconNameEqualTo(lanternCode);
        Lantern lantern = lanternMapper.selectByExample(e).get(0);

        Integer month = req.getMonth();
        String listJson = lantern.getPriceListJson();
        List<LanternPriceVO> lanternPrice = JsonUtil.fromJsonList(listJson, LanternPriceVO.class);
        Optional<LanternPriceVO> vo = lanternPrice.stream().filter(p -> p.getMonth() == month).findFirst();
        if (!vo.isPresent()) {
            throw new ApiException("不存在的購買月份，請重新選擇");
        }

        Integer unitPrice = vo.get().getPrice();
        BigDecimal totalAmount = BigDecimal.valueOf(unitPrice).multiply(BigDecimal.valueOf(purchaseList.size()));
        Integer availableDays = month == 12 ? 365 : month * 30;
        String orderId = RandomGenerator.getUUID().toLowerCase(Locale.ROOT);
        String paymentId = orderId.substring(0, 25);

        Date currentDate = DateUtil.getCurrentDate();

        for (LanternPurchaseInfo info : req.getList()) {
            LanternPurchase entity = new LanternPurchase();
            String id = RandomGenerator.getUUID().toLowerCase(Locale.ROOT);
            entity.setId(id);
            entity.setExternalOrderNo(paymentId);
            entity.setLanternId(lantern.getId());
            entity.setUserId(userId);
            entity.setName(info.getName());
            entity.setBirthday(DateUtil.parseStringToDate(info.getBirthday()));
            entity.setMessage(info.getMessage());
            entity.setBlessingTimes((short)0);
            entity.setCreateTime(new Date());
            entity.setExpiredTime(DateUtil.adjustDate(currentDate, availableDays, Date.class));
            lanternPurchaseMapper.insertSelective(entity);
        }

        // 新增訂單
        InvoiceDTO invoiceDTO = JsonUtil.fromJson(user.getReceipt(), InvoiceDTO.class);
        VirtualOrders orders = VirtualOrders.builder()
                .id(orderId)
                .externalOrderNo(paymentId)
                .userId(userId)
                .totalAmount(totalAmount)
                .status(OrderStatus.CREATED.getValue())
                .invoiceType(invoiceDTO.getType().getValue())
                .invoiceTarget(invoiceDTO.getValue())
                .sourceType(SourceTypeEnum.LANTERN.getCode())
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        virtualOrdersMapper.insertSelective(orders);

        VirtualOrderItem virtualOrderItem = VirtualOrderItem
                .builder()
                .orderId(orderId)
                .description(String.format("%s-%s", SourceTypeEnum.LANTERN.getLabel(), lantern.getName()))
                .quantity(purchaseList.size())
                .unitPrice(BigDecimal.valueOf(unitPrice))
                .amount(totalAmount)
                .createTime(new Date())
                .build();
        virtualOrderItemMapper.insertSelective(virtualOrderItem);

        // 金流訂單
        paymentTransactionMapper.insertSelective(PaymentTransaction.builder()
                .id(paymentId)
                .amount(totalAmount)
                .userId(userId)
                .createTime(currentDate)
                .sourceType("L")
                .status(OrderStatus.CREATED.getValue())
                .payMethod(req.getPaymentMethod())
                .build());

        return PaymentNoVO.builder().externalPaymentNo(paymentId).price(totalAmount).build();
    }
}
