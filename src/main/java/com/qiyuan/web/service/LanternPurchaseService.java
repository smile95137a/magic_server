package com.qiyuan.web.service;

import com.qiyuan.web.dao.LanternMapper;
import com.qiyuan.web.dao.LanternPurchaseMapper;
import com.qiyuan.web.dto.LanternBlessingDTO;
import com.qiyuan.web.entity.Lantern;
import com.qiyuan.web.entity.LanternPurchase;
import com.qiyuan.web.entity.example.LanternExample;
import com.qiyuan.web.entity.example.LanternPurchaseExample;
import com.qiyuan.web.dto.request.LanternPurchaseRequest;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.dto.response.LanternBlessingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        LanternExample e = new LanternExample();
        e.createCriteria().andIconNameEqualTo(lanternCode);
        List<Lantern> lanterns = lanternMapper.selectByExample(e);
        Lantern lantern = lanterns.get(0);
        Calendar now = Calendar.getInstance();
        req.getList()
                .stream()
                .map(lpi ->
                        LanternPurchase.builder()
                                .name(lpi.getName())
                                .message(lpi.getMessage())
                                .birthday(DateUtil.parseStringToDate(lpi.getBirthday()))
                                .createTime(now.getTime())
                                .expiredTime(DateUtil.adjustDate(now, 365, Date.class))
                                .lanternId(lantern.getId())
                                .userId(userId)
                                .build()
                ).forEach(l -> lanternPurchaseMapper.insertSelective(l));
        return true;
    }
}
