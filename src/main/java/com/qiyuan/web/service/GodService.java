package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.*;
import com.qiyuan.web.dto.InvoiceDTO;
import com.qiyuan.web.dto.OfferingReplacementDto;
import com.qiyuan.web.dto.request.GodExtendPeriodRequest;
import com.qiyuan.web.dto.request.IssueInvoiceDto;
import com.qiyuan.web.dto.request.OfferingPresentRequest;
import com.qiyuan.web.dto.request.PresentOfferingRequest;
import com.qiyuan.web.dto.response.*;
import com.qiyuan.web.entity.*;
import com.qiyuan.web.entity.example.*;
import com.qiyuan.web.enums.OrderStatus;
import com.qiyuan.web.enums.SourceTypeEnum;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.JsonUtil;
import com.qiyuan.web.util.RandomGenerator;
import com.qiyuan.web.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GodService {

    private final Logger log = LoggerFactory.getLogger(GodService.class);
    private final GodMapper godMapper;
    private final UserMapper userMapper;
    private final GodInfoService godInfoService;
    private final OfferingService offeringService;
    private final PaymentTransactionMapper paymentTransactionMapper;
    private final GodPurchaseMapper godPurchaseMapper;
    private final VirtualOrdersMapper virtualOrdersMapper;
    private final VirtualOrderItemMapper virtualOrderItemMapper;

    public GodService(GodMapper godMapper, UserMapper userMapper, GodInfoService godInfoService, OfferingService offeringService, PaymentTransactionMapper paymentTransactionMapper, GodPurchaseMapper godPurchaseMapper, VirtualOrdersMapper virtualOrdersMapper, VirtualOrderItemMapper virtualOrderItemMapper) {
        this.godMapper = godMapper;
        this.userMapper = userMapper;
        this.godInfoService = godInfoService;
        this.offeringService = offeringService;
        this.paymentTransactionMapper = paymentTransactionMapper;
        this.godPurchaseMapper = godPurchaseMapper;
        this.virtualOrdersMapper = virtualOrdersMapper;
        this.virtualOrderItemMapper = virtualOrderItemMapper;
    }

    public List<God> getGodList() {
        GodExample e = new GodExample();
        e.setOrderByClause("sort ASC");
        return godMapper.selectByExample(e);
    }

    @Transactional
    public PaymentNoVO prepareGodDescendPurchase(GodExtendPeriodRequest request) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(username);
        String godCode = request.getGodCode().toLowerCase(Locale.ROOT);
        God god = getGodByCode(godCode);
        Date now = DateUtil.getCurrentDate();

        String id = RandomGenerator.getUUID().toLowerCase(Locale.ROOT);
        String paymentId = id.substring(0, 25);
        Byte day = Byte.valueOf(request.getDay());
        GodPurchase record = GodPurchase.builder()
                .id(id)
                .userId(user.getId())
                .godId(god.getId())
                .durationDays(day)
                .status(OrderStatus.CREATED.getValue())
                .externalOrderNo(paymentId)
                .createTime(now)
                .build();
        // 請神價格： 7天200元、30天800元
        BigDecimal price = BigDecimal.valueOf(day == 30 ? 800 : 200);
        PaymentTransaction tx = PaymentTransaction.builder()
                .id(paymentId)
                .userId(user.getId())
                .status(OrderStatus.CREATED.getValue())
                .payMethod(request.getPaymentMethod())
                .amount(price)
                .sourceType(SourceTypeEnum.GOD.getCode())
                .createTime(now)
                .build();

        // 建立請神訂單
        String orderId = RandomGenerator.getUUID().toLowerCase(Locale.ROOT);
        InvoiceDTO invoiceDTO = JsonUtil.fromJson(user.getReceipt(), InvoiceDTO.class);
        VirtualOrders order = VirtualOrders.builder()
                .id(orderId)
                .externalOrderNo(paymentId)
                .userId(user.getId())
                .totalAmount(price)
                .status(OrderStatus.CREATED.getValue())
                .invoiceType(invoiceDTO.getType().getValue())
                .invoiceTarget(invoiceDTO.getValue())
                .sourceType(SourceTypeEnum.GOD.getCode())
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        virtualOrdersMapper.insertSelective(order);

        VirtualOrderItem orderItem = VirtualOrderItem.builder()
                .orderId(orderId)
                .description(String.format("%s-%s-%d天", SourceTypeEnum.GOD.getLabel(), god.getName(), day))
                .quantity(1)
                .unitPrice(price)
                .amount(price)
                .createTime(new Date())
                .build();
        virtualOrderItemMapper.insertSelective(orderItem);

        godPurchaseMapper.insertSelective(record);
        paymentTransactionMapper.insertSelective(tx);
        return PaymentNoVO.builder()
                .externalPaymentNo(paymentId)
                .price(price)
                .build();
    }

    @Transactional
    public boolean markGodDescendPaid(String paymentId) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(username);

        GodPurchaseExample pe = new GodPurchaseExample();
        pe.createCriteria().andExternalOrderNoEqualTo(paymentId).andUserIdEqualTo(user.getId());
        List<GodPurchase> godPurchases = godPurchaseMapper.selectByExample(pe);
        if (godPurchases == null || godPurchases.isEmpty()) throw new ApiException("查無資料");

        GodPurchase purchase = godPurchases.get(0);
        String godId = purchase.getGodId();
        GodInfo godInfo = godInfoService.getGodInfo(user.getId(), godId);
        Date now = DateUtil.getCurrentDate();

        // 更新付款狀態
        purchase.setStatus(OrderStatus.PAID.getValue());
        purchase.setUpdateTime(now);
        godPurchaseMapper.updateByPrimaryKey(purchase);

        // 更新神明資訊
        Byte addDays = purchase.getDurationDays();
        // 第一次請神
        if (godInfo == null) {
            godInfo = GodInfo.builder()
                    .godId(godId)
                    .userId(user.getId())
                    .exp((byte) 0)
                    .level((byte) 1)
                    .jiaobeiLastTime(now)
                    .onshelfTime(now)
                    .offshelfTime(DateUtil.adjustDate(now, addDays, Date.class))
                    .cooldownTime(DateUtil.adjustDate(now, addDays + 1, Date.class))
                    .build();
            return godInfoService.addGodInfo(godInfo);
        } else {
            boolean isExtend = godInfo.getOffshelfTime() != null && godInfo.getOffshelfTime().after(now);
            godInfo.setJiaobeiLastTime(now);
            if (isExtend) {
                // 延長
                Date expiredDay = DateUtil.adjustDate(godInfo.getOffshelfTime(), addDays, Date.class);
                godInfo.setOffshelfTime(expiredDay);
                godInfo.setCooldownTime(DateUtil.adjustDate(expiredDay,  1, Date.class));
            } else {
                // 請神
                godInfo.setOnshelfTime(now);
                godInfo.setOffshelfTime(DateUtil.adjustDate(now, addDays, Date.class));
                godInfo.setCooldownTime(DateUtil.adjustDate(now, addDays + 1, Date.class));
            }

            return godInfoService.updateGodInfo(godInfo);
        }
    }

    public GodInfoVO getGodInfo(String godCode) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(username);
        God god = getGodByCode(godCode);
        GodInfo godInfo = godInfoService.getGodInfo(user.getId(), god.getId());
        if (godInfo == null) {
            return null;
        }

        boolean isValid =
                godInfo.getOffshelfTime() != null &&
                        godInfo.getOnshelfTime() != null &&
                        godInfo.getOffshelfTime().after(godInfo.getOnshelfTime()) &&
                        new Date().before(godInfo.getOffshelfTime());

        if (!isValid)  return null;

        boolean isGolden = godInfo.getGoldenExpiration() != null
                && godInfo.getGoldenExpiration().after(DateUtil.getCurrentDate());

        GodInfoVO vo = GodInfoVO.builder()
                .imageCode(god.getImageCode())
                .name(god.getName())
                .isGolden(isGolden)
                .cooldownTime(godInfo.getCooldownTime())
                .onshelfTime(godInfo.getOnshelfTime())
                .offshelfTime(godInfo.getOffshelfTime())
                .build();

        String offeringJson = godInfo.getOfferingList();
        if (StringUtils.isNotBlank(offeringJson)) {
            try {
                List<OfferingStateVO> offerings = JsonUtil.fromJsonList(offeringJson, OfferingStateVO.class);
                List<Offering> offeringList = offeringService.getOfferingByIds(offerings.stream().map(OfferingStateVO::getId).collect(Collectors.toList()));
                List<OfferingVO> offeringVOList = offeringList.stream().map(offeringService::convertToVo).collect(Collectors.toList());
                vo.setOfferings(offeringVOList);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new ApiException("發生未知錯誤");
            }

        }
        return vo;
    }

    @Transactional
    public PaymentNoVO addOffering(PresentOfferingRequest param) {
        String godCode = param.getGodCode();
        String username = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(username);
        God god = getGodByCode(godCode);
        GodInfo godInfo = godInfoService.getGodInfo(user.getId(), god.getId());
        if (godInfo == null) throw new ApiException("請先請神成功!");

        List<OfferingPresentRequest> list = param.getList();
        List<String> offeringIds = list.stream().map(OfferingPresentRequest::getNewOfferingId).collect(Collectors.toList());
        List<Offering> offerings = offeringService.getOfferingByIds(offeringIds);
        if (offerings == null || offerings.isEmpty() || offerings.size() != offeringIds.size()) throw new ApiException("包含不存在的供品，請重新選擇");

        Map<String, Offering> offeringMap = offerings.stream().collect(Collectors.toMap(Offering::getId, o -> o));

        final Map<Integer, String> indexToOldId = new HashMap<>();
        if (godInfo.getOfferingList() != null) {
            List<OfferingStateVO>  currentOfferings = JsonUtil.fromJsonList(godInfo.getOfferingList(), OfferingStateVO.class);
            for (int i = 0; i < currentOfferings.size(); i++) {
                indexToOldId.put(i, currentOfferings.get(i).getId());
            }
        }

        List<OfferingReplacementDto> replacements = list.stream().map(req -> {
            Integer index = req.getIndex();
            String newId = req.getNewOfferingId();
            String oldId = indexToOldId.get(index);
            Offering offering = offeringMap.get(newId);
            return OfferingReplacementDto.builder()
                    .index(index)
                    .newOfferingId(newId)
                    .oldOfferingId(oldId)
                    .offering(offering)
                    .build();
        }).collect(Collectors.toList());


        // ========== 免費供品直接更新 ==========
        List<OfferingReplacementDto> freeOffering = replacements.stream().filter(dto -> dto.getOffering().getPrice() == 0).collect(Collectors.toList());
        List<OfferingStateVO> newOfferingState = null;

        // 免費供品置換
        if (freeOffering.size() > 0) {
            newOfferingState = this.addOffering(godInfo.getOfferingList(), freeOffering);
        }

        if (newOfferingState != null) {
            godInfo.setOfferingList(JsonUtil.toJson(newOfferingState));
        }

        // ========== 收費供品紀錄 ==========
        List<OfferingReplacementDto> payOffering = replacements.stream().filter(dto -> dto.getOffering().getPrice() != 0).collect(Collectors.toList());
        if (payOffering.size() == 0) {
            godInfoService.updateGodInfo(godInfo);

            return PaymentNoVO.builder()
                    .externalPaymentNo(null)
                    .price(BigDecimal.ZERO)
                    .build();
        }

        String replacementStr = payOffering.stream().map(po -> String.format("%d:%s", po.getIndex(), po.getNewOfferingId())).collect(Collectors.joining(","));
        godInfo.setOfferingReplacement(replacementStr);
        godInfoService.updateGodInfo(godInfo);

        String orderId = RandomGenerator.getUUID().toLowerCase(Locale.ROOT);
        String paymentId = orderId.substring(0, 25);
        BigDecimal total = BigDecimal.ZERO;

        Map<String, VirtualOrderItem> orderItemMap = new LinkedHashMap<>();
        for (OfferingReplacementDto dto : payOffering) {
            total = total.add(BigDecimal.valueOf(dto.getOffering().getPrice()));
            // 新增購買紀錄
            offeringService.addOfferingPurchase(
                    OfferingPurchase.builder()
                    .id(RandomGenerator.getUUID().toLowerCase(Locale.ROOT))
                    .externalOrderNo(paymentId)
                    .offeringId(dto.getNewOfferingId())
                    .godId(god.getId())
                    .userId(user.getId())
                    .createTime(new Date())
                    .build());

            // 新增訂單
            VirtualOrderItem existing = orderItemMap.get(dto.getNewOfferingId());
            BigDecimal unit = BigDecimal.valueOf(dto.getOffering().getPrice());
            if (existing == null) {

                orderItemMap.put(dto.getNewOfferingId(),
                        VirtualOrderItem.builder()
                        .orderId(orderId)
                        .description(String.format("%s-%s", SourceTypeEnum.OFFERING.getLabel(), dto.getOffering().getName()))
                        .quantity(1)
                        .unitPrice(unit)
                        .amount(unit)
                        .createTime(new Date())
                        .build());
            } else {
                existing.setQuantity(existing.getQuantity() + 1);
                existing.setAmount(existing.getAmount().add(unit));
            }
        }

        InvoiceDTO invoiceDTO = JsonUtil.fromJson(user.getReceipt(), InvoiceDTO.class);
        VirtualOrders orders = VirtualOrders.builder()
                .id(orderId)
                .externalOrderNo(paymentId)
                .userId(user.getId())
                .totalAmount(total)
                .status(OrderStatus.CREATED.getValue())
                .invoiceType(invoiceDTO.getType().getValue())
                .invoiceTarget(invoiceDTO.getValue())
                .sourceType(SourceTypeEnum.GOD.getCode())
                .createTime(new Date())
                .updateTime(new Date())
                .build();

        virtualOrdersMapper.insertSelective(orders);
        List<VirtualOrderItem> itemList = new ArrayList<>(orderItemMap.values());

        for (VirtualOrderItem item : itemList) {
            virtualOrderItemMapper.insertSelective(item);
        }

        paymentTransactionMapper.insertSelective(
                PaymentTransaction.builder()
                        .id(paymentId)
                        .userId(user.getId())
                        .createTime(new Date())
                        .sourceType("O")
                        .amount(total)
                        .status(OrderStatus.CREATED.getValue())
                        .payMethod(param.getPaymentMethod())
                        .build()
        );
        return PaymentNoVO.builder()
                .externalPaymentNo(paymentId)
                .price(total)
                .build();
    }


    @Transactional
    public boolean processOfferingAfterPayment(String paymentId) {
        PaymentTransaction paymentTransaction = paymentTransactionMapper.selectByPrimaryKey(paymentId);
        String userId = paymentTransaction.getUserId();

        OfferingPurchaseExample pe = new OfferingPurchaseExample();
        pe.createCriteria().andExternalOrderNoEqualTo(paymentId).andUserIdEqualTo(userId);
        List<OfferingPurchase> offeringPurchase = offeringService.getOfferingPurchase(pe);
        if (offeringPurchase == null || offeringPurchase.isEmpty()) throw new ApiException("查無訂單紀錄，請聯繫客服。");

        OfferingPurchase purchase = offeringPurchase.get(0);

        GodInfoExample e = new GodInfoExample();
        e.createCriteria().andGodIdEqualTo(purchase.getGodId()).andUserIdEqualTo(purchase.getUserId());
        List<GodInfo> godInfos = godInfoService.selectByExample(e);
        if (godInfos == null || godInfos.isEmpty()) throw new ApiException("查無神明紀錄，請聯繫客服。");

        GodInfo godInfo = godInfos.get(0);
        String replacement = godInfo.getOfferingReplacement();

        if (replacement == null || !replacement.contains(":")) throw new ApiException("查無供品購買紀錄，請聯繫客服。");
        List<String> replacements = Arrays.stream(replacement.split(",")).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        List<OfferingReplacementDto> dtoList = replacements.stream().map(r -> {
            String[] tempSplit = r.split(":");
            String newOfferingId = tempSplit[1];
            String prevIndex = tempSplit[0];
            return OfferingReplacementDto.builder()
                    .newOfferingId(newOfferingId)
                    .index(Integer.valueOf(prevIndex))
                    .build();
        }).collect(Collectors.toList());

        List<OfferingStateVO> offeringInfoList = this.addOffering(godInfo.getOfferingList(), dtoList);
        godInfo.setOfferingList(JsonUtil.toJson(offeringInfoList));
        godInfo.setOfferingReplacement("");

        // 升級計算
        List<String> offeringIds = offeringInfoList.stream().map(OfferingStateVO::getId).collect(Collectors.toList());
        List<Offering> offering = offeringService.getOfferingByIds(offeringIds);
        int totolPoints = offering.stream().mapToInt(Offering::getPoints).sum();

        int newExp = godInfo.getExp() + totolPoints;
        int nextLevel = newExp / 10;
        int newGodLevel = nextLevel > 0 ? godInfo.getLevel() + nextLevel : godInfo.getLevel();
        boolean isGolden = false;
        if (newGodLevel >= 5) {
            isGolden = true;
            godInfo.setLevel((byte) 1);
            godInfo.setGoldenExpiration(DateUtil.adjustDate(DateUtil.getCurrentDate(), 1, Date.class));
        } else {
            godInfo.setLevel((byte) newGodLevel);
        }
        newExp = newExp % 10;
        godInfo.setExp((byte) newExp);

        // 更新請神資訊
        godInfoService.updateGodInfo(godInfo);
        return true;
    }

    private List<OfferingStateVO> addOffering(String offeringList, List<OfferingReplacementDto> replacementDtos) {
        List<OfferingStateVO> offeringInfoList = null;
        // 開始新增/置換供品
        if (StringUtils.isBlank(offeringList)) {
            offeringInfoList = IntStream.range(0, 2)
                            .mapToObj(i -> OfferingStateVO.builder().build())
                            .collect(Collectors.toList());
        }

        for (OfferingReplacementDto dto: replacementDtos) {
            OfferingStateVO vo = offeringInfoList.get(dto.getIndex());
            vo.setId(dto.getNewOfferingId());
            vo.setBuyTime(DateFormatUtils.format(DateUtil.getCurrentDate(), "yyyy/MM/dd HH:mm"));
        }
        return offeringInfoList;
    }

    public God getGodByCode(String godCode) {
        GodExample e = new GodExample();
        e.createCriteria().andImageCodeEqualTo(godCode);
        return godMapper.selectByExample(e).get(0);
    }


}
