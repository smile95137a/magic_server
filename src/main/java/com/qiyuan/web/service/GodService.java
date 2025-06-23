package com.qiyuan.web.service;

import com.chl.security.exception.ApiException;
import com.qiyuan.web.dao.GodMapper;
import com.qiyuan.web.dao.UserMapper;
import com.qiyuan.web.dto.request.PresentOfferingRequest;
import com.qiyuan.web.dto.response.GodInfoVO;
import com.qiyuan.web.entity.God;
import com.qiyuan.web.entity.GodInfo;
import com.qiyuan.web.entity.Offering;
import com.qiyuan.web.entity.User;
import com.qiyuan.web.entity.example.GodExample;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GodService {
    private final GodMapper godMapper;
    private final UserMapper userMapper;
    private final GodInfoService godInfoService;
    private final OfferingService offeringService;

    public GodService(GodMapper godMapper, UserMapper userMapper, GodInfoService godInfoService, OfferingService offeringService) {
        this.godMapper = godMapper;
        this.userMapper = userMapper;
        this.godInfoService = godInfoService;
        this.offeringService = offeringService;
    }

    public List<God> getGodList() {
        GodExample e = new GodExample();
        e.setOrderByClause("sort ASC");
        e.createCriteria();
        return godMapper.selectByExample(e);
    }

    @Transactional
    public boolean godDescend(String godCode) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(username);
        God god = getGodByCode(godCode);
        GodInfo godInfo = godInfoService.getGodInfo(user.getId(), god.getId());
        Date now = DateUtil.getCurrentDate();
        if (godInfo == null) {
            godInfo = GodInfo.builder()
                    .godId(god.getId())
                    .userId(user.getId())
                    .exp((byte) 0)
                    .level((byte) 1)
                    .jiaobeiLastTime(now)
                    .onshelfTime(now)
                    .offshelfTime(DateUtil.adjustDate(now, 1, Date.class))
                    .cooldownTime(DateUtil.adjustDate(now, 2, Date.class))
                    .build();
            return godInfoService.addGodInfo(godInfo);
        } else {
            godInfo.setJiaobeiLastTime(now);
            godInfo.setOnshelfTime(now);
            godInfo.setOffshelfTime(DateUtil.adjustDate(now, 1, Date.class));
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

        boolean isGolden = godInfo.getGoldenExpiration() != null
                && godInfo.getGoldenExpiration().after(DateUtil.getCurrentDate());
        String offeringStr = godInfo.getOfferingList();
        List<Offering> offeringList = Collections.EMPTY_LIST;
        if (StringUtils.isNotBlank(offeringStr) && offeringStr.indexOf(",") > 0) {
            List<String> offeringIds = Arrays.asList(offeringStr.split(","));
            offeringList = offeringService.getOfferingByIds(offeringIds);
        }

        return GodInfoVO.builder()
                .imageCode(god.getImageCode())
                .name(god.getName())
                .isGolden(isGolden)
                .cooldownTime(godInfo.getCooldownTime())
                .onshelfTime(godInfo.getOnshelfTime())
                .offshelfTime(godInfo.getOffshelfTime())
                .offerings(offeringList)
                .build();
    }

    public boolean extendGodPeriod(String godCode, int day) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(username);
        God god = getGodByCode(godCode);
        GodInfo godInfo = godInfoService.getGodInfo(user.getId(), god.getId());
        Date now = DateUtil.getCurrentDate();
        godInfo.setJiaobeiLastTime(now);
        Date newOffshelf = DateUtil.adjustDate(godInfo.getOffshelfTime(), day, Date.class);
        Date newCooldown = DateUtil.adjustDate(godInfo.getOffshelfTime(), day + 1, Date.class);
        godInfo.setOffshelfTime(newOffshelf);
        godInfo.setCooldownTime(newCooldown);
        return godInfoService.updateGodInfo(godInfo);
    }

    @Transactional
    public GodInfoVO addOffering(PresentOfferingRequest param) {
        String godCode = param.getGodCode();
        String prevOfferingId = param.getPrevOfferingId().toLowerCase(Locale.ROOT);
        String newOfferingId = param.getNewOfferingId().toLowerCase(Locale.ROOT);
        String username = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(username);
        God god = getGodByCode(godCode);
        GodInfo godInfo = godInfoService.getGodInfo(user.getId(), god.getId());
        List<String> offeringList = Arrays.asList(godInfo.getOfferingList().split(","));
        if (StringUtils.isNoneBlank(prevOfferingId, godInfo.getOfferingList()) && godInfo.getOfferingList().contains(prevOfferingId)) {
            int replacementTarget = offeringList.indexOf(prevOfferingId);
            offeringList.set(replacementTarget, newOfferingId);
            godInfo.setOfferingList(offeringList.stream().collect(Collectors.joining(",")));
        } else {
            if (offeringList.size() > 2) {
                throw new ApiException("購買供品發生系統錯誤，請聯繫客服！");
            }
            String newOfferingList = Stream.concat(
                    offeringList.stream(),
                    Stream.of(newOfferingId)
            ).collect(Collectors.joining(","));
            godInfo.setOfferingList(newOfferingList);

        }

        Offering offering = offeringService.getOfferingById(newOfferingId);
        int newExp = godInfo.getExp() + offering.getPoints();
        int nextLevel = newExp / 10;
        int newGodLevel = nextLevel > 0 ? godInfo.getLevel() + nextLevel : godInfo.getLevel();
        boolean isGolden = false;
        if (newGodLevel >= 5) {
            isGolden = true;
            godInfo.setLevel((byte) 1);
            godInfo.setGoldenExpiration(DateUtil.adjustDate(DateUtil.getCurrentDate(), 1, Date.class));
        }
        newExp = newExp % 10;
        godInfo.setExp((byte) newExp);

        if (!(godInfoService.updateGodInfo(godInfo) &&
                offeringService.addOfferingPurchase(user.getId(), offering.getId(), god.getId()))) {
            throw new ApiException("交易發生錯誤，請聯繫客服！");
        }


        List<String> offeringIds  = Arrays.asList(godInfo.getOfferingList().split(","))
                .stream()
                .filter(String::isBlank)
                .collect(Collectors.toList());

        return GodInfoVO.builder()
                .imageCode(god.getImageCode())
                .name(god.getName())
                .isGolden(isGolden)
                .cooldownTime(godInfo.getCooldownTime())
                .onshelfTime(godInfo.getOnshelfTime())
                .offshelfTime(godInfo.getOffshelfTime())
                .offerings(offeringService.getOfferingByIds(offeringIds))
                .build();
    }

    public God getGodByCode(String godCode) {
        GodExample e = new GodExample();
        e.createCriteria().andImageCodeEqualTo(godCode);
        return godMapper.selectByExample(e).get(0);
    }


}
