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
import com.qiyuan.web.enums.InvoiceType;
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
        InvoiceDTO invoiceDTO = null;
        try {
            invoiceDTO = JsonUtil.fromJson(user.getReceipt(), InvoiceDTO.class);
        } catch (Exception e) {
            throw new ApiException("請先至會員中心填寫發票資訊");
        }
        if (invoiceDTO == null || (!invoiceDTO.getType().equals(InvoiceType.CITIZEN) && StringUtils.isBlank(invoiceDTO.getValue()))) {
            throw new ApiException("請先至會員中心填寫發票資訊");
        }

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
            	godInfo.setOfferingList(null);
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
        int currentLevel = godInfo.getLevel();
        int currentExp = godInfo.getExp();

        // 總共累積的經驗（等級 -1 的所有滿級經驗 + 當前 exp）
        int totalAccumulateExp = (currentLevel - 1) * 10 + currentExp;

        // 距離金身還差多少 (假設金身需要等級 5 → 40 exp)
        int expToGolden = 0;
        if (!isGolden) {
            int requiredExpForGolden = (5 - 1) * 10; // 等級 4 滿經驗 + 下一級 = 40
            expToGolden = requiredExpForGolden - totalAccumulateExp;
            if (expToGolden < 0) expToGolden = 0;
        }

        GodInfoVO vo = GodInfoVO.builder()
                .imageCode(god.getImageCode())
                .name(god.getName())
                .isGolden(isGolden)
                .cooldownTime(godInfo.getCooldownTime())
                .onshelfTime(godInfo.getOnshelfTime())
                .offshelfTime(godInfo.getOffshelfTime())
                .expToGolden(expToGolden)
                .totalAccumulateExp(totalAccumulateExp)
                .build();

        String offeringJson = godInfo.getOfferingList();
        if (StringUtils.isNotBlank(offeringJson)) {
            try {
            	List<OfferingStateVO> states = JsonUtil.fromJsonList(offeringJson, OfferingStateVO.class);

            	List<String> distinctIds = states.stream()
            	        .map(OfferingStateVO::getId)
            	        .filter(StringUtils::isNotBlank)
            	        .distinct()
            	        .collect(Collectors.toList());

            	List<Offering> found = distinctIds.isEmpty()
            	        ? Collections.emptyList()
            	        : offeringService.getOfferingByIds(distinctIds);

            	Map<String, OfferingVO> idToVO = found.stream()
            	        .collect(Collectors.toMap(Offering::getId, o -> offeringService.convertToVo(o)));

            	List<OfferingVO> offeringVOList = new ArrayList<>(states.size());
            	for (OfferingStateVO s : states) {
            	    String id = s.getId();
            	    OfferingVO voItem = StringUtils.isBlank(id) ? null : idToVO.get(id);
            	    offeringVOList.add(voItem);
            	}

            	// 4) 設回去，長度會跟 offeringJson 的陣列一樣（例如 3 個）
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

        InvoiceDTO invoiceDTO = null;
        try {
            invoiceDTO = JsonUtil.fromJson(user.getReceipt(), InvoiceDTO.class);
        } catch (Exception e) {
            throw new ApiException("請先至會員中心填寫發票資訊");
        }
        if (invoiceDTO == null || (!invoiceDTO.getType().equals(InvoiceType.CITIZEN) && StringUtils.isBlank(invoiceDTO.getValue()))) {
            throw new ApiException("請先至會員中心填寫發票資訊");
        }

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
        
        // 1) 解析本次更換清單（只含本次付費項目）
        List<String> newIds = new ArrayList<>();
        List<OfferingReplacementDto> dtoList = Arrays.stream(replacement.split(","))
                .filter(StringUtils::isNotBlank)
                .map(r -> {
                    String[] tmp = r.split(":");
                    String newOfferingId = tmp[1];
                    Integer index = Integer.valueOf(tmp[0]);
                    newIds.add(newOfferingId);
                    return OfferingReplacementDto.builder()
                            .newOfferingId(newOfferingId)
                            .index(index)
                            .build();
                }).collect(Collectors.toList());

        // 2) 實際把供品換進供桌（維持你原本的行為）
        List<OfferingStateVO> offeringInfoList = this.addOffering(godInfo.getOfferingList(), dtoList);
        godInfo.setOfferingList(JsonUtil.toJson(offeringInfoList));
        godInfo.setOfferingReplacement("");

        // 3) 只計算「本次更換的新供品」的分數總和（delta）
        List<Offering> newOfferings = offeringService.getOfferingByIds(newIds);
        int deltaPoints = newOfferings.stream().mapToInt(Offering::getPoints).sum();

        // 4) 用 deltaPoints 來升級
        int newExp = godInfo.getExp() + deltaPoints;
        int nextLevel = newExp / 10;
        int newGodLevel = nextLevel > 0 ? godInfo.getLevel() + nextLevel : godInfo.getLevel();

        if (newGodLevel >= 5) {
            // 進入金身
            godInfo.setLevel((byte) 1);
            godInfo.setGoldenExpiration(DateUtil.adjustDate(DateUtil.getCurrentDate(), 1, Date.class));
        } else {
            godInfo.setLevel((byte) newGodLevel);
        }
        newExp = newExp % 10;
        godInfo.setExp((byte) newExp);

        godInfoService.updateGodInfo(godInfo);
        return true;
    }


	private List<OfferingStateVO> addOffering(String offeringListJson, List<OfferingReplacementDto> replacementDtos) {
	    // 1) 初始化 slots（至少 2 格）
	    List<OfferingStateVO> slots;
	    if (StringUtils.isBlank(offeringListJson)) {
	        slots = new ArrayList<>(Arrays.asList(
	                OfferingStateVO.builder().build(),
	                OfferingStateVO.builder().build()
	        ));
	    } else {
	        slots = new ArrayList<>(JsonUtil.fromJsonList(offeringListJson, OfferingStateVO.class));
	        while (slots.size() < 2) slots.add(OfferingStateVO.builder().build());
	    }
	
	    // 2) 依 index 排序，確保套用順序穩定
	    replacementDtos.sort(Comparator.comparingInt(OfferingReplacementDto::getIndex));
	
	    // 3) 逐筆套用：補空位到目標 index，再 set(index, ...)
	    String now = DateFormatUtils.format(DateUtil.getCurrentDate(), "yyyy/MM/dd HH:mm");
	    for (OfferingReplacementDto dto : replacementDtos) {
	        int idx = dto.getIndex();
	
	        // 若 index 超出目前長度，補空位直到可索引到 idx
	        while (slots.size() <= idx) {
	            slots.add(OfferingStateVO.builder().build());
	        }
	
	        OfferingStateVO target = slots.get(idx);
	        if (target == null) {
	            target = OfferingStateVO.builder().build();
	            slots.set(idx, target);
	        }
	        target.setId(dto.getNewOfferingId());
	        target.setBuyTime(now);
	    }
	
	    return slots;
	}

    public God getGodByCode(String godCode) {
        GodExample e = new GodExample();
        e.createCriteria().andImageCodeEqualTo(godCode);
        return godMapper.selectByExample(e).get(0);
    }


}
