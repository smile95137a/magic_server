package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.GodMapper;
import com.qiyuan.web.dao.GodPurchaseMapper;
import com.qiyuan.web.dao.PaymentTransactionMapper;
import com.qiyuan.web.dao.UserMapper;
import com.qiyuan.web.dto.request.GodExtendPeriodRequest;
import com.qiyuan.web.dto.request.PresentOfferingRequest;
import com.qiyuan.web.dto.response.*;
import com.qiyuan.web.entity.*;
import com.qiyuan.web.entity.example.GodExample;
import com.qiyuan.web.entity.example.GodInfoExample;
import com.qiyuan.web.entity.example.GodPurchaseExample;
import com.qiyuan.web.entity.example.OfferingPurchaseExample;
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

@Service
public class GodService {

    private final Logger log = LoggerFactory.getLogger(GodService.class);
    private final GodMapper godMapper;
    private final UserMapper userMapper;
    private final GodInfoService godInfoService;
    private final OfferingService offeringService;
    private final PaymentTransactionMapper paymentTransactionMapper;
    private final GodPurchaseMapper godPurchaseMapper;

    public GodService(GodMapper godMapper, UserMapper userMapper, GodInfoService godInfoService, OfferingService offeringService, PaymentTransactionMapper paymentTransactionMapper, GodPurchaseMapper godPurchaseMapper) {
        this.godMapper = godMapper;
        this.userMapper = userMapper;
        this.godInfoService = godInfoService;
        this.offeringService = offeringService;
        this.paymentTransactionMapper = paymentTransactionMapper;
        this.godPurchaseMapper = godPurchaseMapper;
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
        String prevOfferingId = Optional.ofNullable(param.getPrevOfferingId())
            .map(id -> id.toLowerCase(Locale.ROOT))
            .orElse("");
        String newOfferingId = param.getNewOfferingId().toLowerCase(Locale.ROOT);
        String username = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(username);
        God god = getGodByCode(godCode);
        GodInfo godInfo = godInfoService.getGodInfo(user.getId(), god.getId());
        if (godInfo == null) throw new ApiException("請先請神成功!");

        Offering offering = offeringService.getOfferingById(newOfferingId);
        if (offering == null) throw new ApiException("不存在的供品，請重新選擇");

        // 免費供品直接更新
        if (offering.getPrice() == 0) {
            List<OfferingStateVO> offeringInfoList = this.addOffering(godInfo.getOfferingList(), newOfferingId, prevOfferingId);
            godInfo.setOfferingList(JsonUtil.toJson(offeringInfoList));
            godInfoService.updateGodInfo(godInfo);
            return PaymentNoVO.builder()
                    .externalPaymentNo(null)
                    .price(BigDecimal.valueOf(offering.getPrice()))
                    .build();
        }

        String replacement = String.format("%s:%s", prevOfferingId, newOfferingId);
        godInfo.setOfferingReplacement(replacement);
        godInfoService.updateGodInfo(godInfo);
        
        String id = RandomGenerator.getUUID().toLowerCase(Locale.ROOT);
        String paymentId = id.substring(0, 25);

        // 新增購買紀錄
        offeringService.addOfferingPurchase(OfferingPurchase.builder()
                .id(RandomGenerator.getUUID().toLowerCase(Locale.ROOT))
                .externalOrderNo(paymentId)
                .offeringId(newOfferingId)
                .godId(god.getId())
                .userId(user.getId())
                .createTime(new Date())
                .build());

        paymentTransactionMapper.insertSelective(
                PaymentTransaction.builder()
                        .id(paymentId)
                        .userId(user.getId())
                        .createTime(new Date())
                        .sourceType("O")
                        .amount(BigDecimal.valueOf(offering.getPrice()))
                        .status(OrderStatus.CREATED.getValue())
                        .payMethod(param.getPaymentMethod())
                        .build()
        );
        return PaymentNoVO.builder()
                .externalPaymentNo(paymentId)
                .price(BigDecimal.valueOf(offering.getPrice()))
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

        if (!replacement.contains(":")) throw new ApiException("查無供品購買紀錄，請聯繫客服。");
        String[] tempSplit = replacement.split(":");
        String newOfferingId = tempSplit[1];
        String prevOfferingId = tempSplit[0];

        // 開始新增/置換供品
        List<OfferingStateVO> offeringInfoList = this.addOffering(godInfo.getOfferingList(), newOfferingId, prevOfferingId);

        godInfo.setOfferingList(JsonUtil.toJson(offeringInfoList));
        godInfo.setOfferingReplacement("");
        Offering offering = offeringService.getOfferingById(newOfferingId);
        int newExp = godInfo.getExp() + offering.getPoints();
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

    private List<OfferingStateVO> addOffering(String offeringList, String newOfferingId, String prevOfferingId) {
        // 開始新增/置換供品
        List<OfferingStateVO> offeringInfoList = null;
        // 首次購買供品
        if (StringUtils.isBlank(offeringList)) {
            OfferingStateVO stateVO = OfferingStateVO.builder().id(newOfferingId).buyTime(DateFormatUtils.format(DateUtil.getCurrentDate(), "yyyy/MM/dd HH:mm")).build();
            offeringInfoList = Arrays.asList(stateVO);
        } else {
            offeringInfoList = JsonUtil.fromJsonList(offeringList, OfferingStateVO.class);

            int target = -1;
            if (StringUtils.isNotBlank(prevOfferingId)) {
                // 舊換新
                for (int i = 0; i < offeringInfoList.size(); i++) {
                    OfferingStateVO vo = offeringInfoList.get(i);
                    if (StringUtils.equalsIgnoreCase(vo.getId(), prevOfferingId)) {
                        target = i;
                        break;
                    }
                }
            }

            if (target == -1) {
                // 查無舊供品或是新增
                if (offeringInfoList.size() > 2) {
                    throw new ApiException("購買供品發生系統錯誤，請聯繫客服！");
                }

                OfferingStateVO stateVO = OfferingStateVO
                        .builder()
                        .id(newOfferingId)
                        .buyTime(DateFormatUtils.format(DateUtil.getCurrentDate(), "yyyy/MM/dd HH:mm"))
                        .build();
                offeringInfoList.add(stateVO);
            } else {
                OfferingStateVO replacementTarget = offeringInfoList.get(target);
                replacementTarget.setId(newOfferingId);
                replacementTarget.setBuyTime(DateFormatUtils.format(DateUtil.getCurrentDate(), "yyyy/MM/dd HH:mm"));
            }
        }
        return offeringInfoList;
    }


    public God getGodByCode(String godCode) {
        GodExample e = new GodExample();
        e.createCriteria().andImageCodeEqualTo(godCode);
        return godMapper.selectByExample(e).get(0);
    }


}
