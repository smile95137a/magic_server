package com.qiyuan.web.service;

import com.qiyuan.web.dao.LanternPurchaseMapper;
import com.qiyuan.web.dao.OfferingPurchaseMapper;
import com.qiyuan.web.dao.UserMapper;
import com.qiyuan.web.dto.request.RecordPeriodRequest;
import com.qiyuan.web.dto.response.RecordVO;
import com.qiyuan.web.entity.*;
import com.qiyuan.web.enums.RecordItem;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.SecurityUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MemberService {
    private final OfferingPurchaseMapper offeringPurchaseMapper;
    private final LanternPurchaseMapper lanternPurchaseMapper;
    private final UserMapper userMapper;

    public MemberService(OfferingPurchaseMapper offeringPurchaseMapper, LanternPurchaseMapper lanternPurchaseMapper, UserMapper userMapper) {
        this.offeringPurchaseMapper = offeringPurchaseMapper;
        this.lanternPurchaseMapper = lanternPurchaseMapper;
        this.userMapper = userMapper;
    }

    public List<RecordVO> getPurchaseRecord(RecordPeriodRequest req) {
        Date startTime = req.getStartTime();
        Date endTime = DateUtil.getEndOfDate(req.getEndTime());

        String username = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(username);

        List<OfferingRecord> offeringRecords = offeringPurchaseMapper.selectRecordsByUserIdAndPeriod(user.getId(), startTime, endTime, 50);
        List<LanternRecord> lanternRecords = lanternPurchaseMapper.selectRecordsByUserIdAndPeriod(user.getId(), startTime, endTime, 50);

        return Stream.concat(
                offeringRecords
                .stream()
                .map(o -> RecordVO.builder()
                                .date(DateFormatUtils.format(o.getCreateTime(), "yyyy/MM/dd HH:mm"))
                                .item(RecordItem.Offering.getLabel())
                                .content(o.getOfferingName())
                                .build()
                ),
                lanternRecords.stream()
                        .map(l -> RecordVO.builder()
                                .date(DateFormatUtils.format(l.getCreateTime(), "yyyy/MM/dd HH:mm"))
                                .item(RecordItem.Lantern.getLabel())
                                .content(l.getLanternName())
                                .build())
                ).collect(Collectors.toList());
    }
}
