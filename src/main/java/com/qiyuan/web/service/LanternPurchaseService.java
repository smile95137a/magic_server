package com.qiyuan.web.service;

import com.qiyuan.web.dao.LanternMapper;
import com.qiyuan.web.dao.LanternPurchaseMapper;
import com.qiyuan.web.dto.LanternBlessingDTO;
import com.qiyuan.web.dto.request.LanternPurchaseInfo;
import com.qiyuan.web.dto.request.RecordPeriodRequest;
import com.qiyuan.web.dto.response.PaymentCreateResult;
import com.qiyuan.web.dto.response.RecordVO;
import com.qiyuan.web.entity.Lantern;
import com.qiyuan.web.entity.LanternPurchase;
import com.qiyuan.web.entity.LanternRecord;
import com.qiyuan.web.entity.OfferingRecord;
import com.qiyuan.web.entity.example.LanternExample;
import com.qiyuan.web.entity.example.LanternPurchaseExample;
import com.qiyuan.web.dto.request.LanternPurchaseRequest;
import com.qiyuan.web.enums.RecordItem;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.dto.response.LanternBlessingVO;
import com.qiyuan.web.util.RandomGenerator;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanternPurchaseService {
    @Autowired
    private LanternPurchaseMapper lanternPurchaseMapper;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private LanternMapper lanternMapper;

    @Autowired
    private PaymentService paymentService;

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
    public boolean addLanternPurchaseRecord(LanternPurchaseRequest req) {
        String userId = req.getUserId();
        String lanternCode = req.getLanternCode();

        BigDecimal totalAmount = BigDecimal.ZERO;

        // 查單價
        LanternExample e = new LanternExample();
        e.createCriteria().andIconNameEqualTo(lanternCode);
        Lantern lantern = lanternMapper.selectByExample(e).get(0);

        for (LanternPurchaseInfo info : req.getList()) {
            LanternPurchase entity = new LanternPurchase();
            String id = RandomGenerator.getUUID();
            entity.setId(id);
            entity.setExternalOrderNo(id);
            entity.setLanternId(lantern.getId());
            entity.setUserId(userId);
            entity.setName(info.getName());
            entity.setBirthday(DateUtil.parseStringToDate(info.getBirthday()));
            entity.setMessage(info.getMessage());
            entity.setBlessingTimes((short)0);
            entity.setCreateTime(new Date());
            entity.setExpiredTime(DateUtil.adjustDate(new Date(), 365, Date.class));
            // TODO: 待補價格
//            totalAmount = totalAmount.add(lantern.getPrice());
            lanternPurchaseMapper.insertSelective(entity);
        }

        return true;
    }
}
