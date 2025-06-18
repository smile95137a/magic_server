package com.qiyuan.web.service;

import com.qiyuan.web.dao.LanternPurchaseMapper;
import com.qiyuan.web.dao.SystemConfigMapper;
import com.qiyuan.web.dto.LanternBlessingDTO;
import com.qiyuan.web.entity.LanternPurchase;
import com.qiyuan.web.entity.SystemConfig;
import com.qiyuan.web.entity.SystemConfigExample;
import com.qiyuan.web.entity.example.LanternPurchaseExample;
import com.qiyuan.web.vo.LanternBlessingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanternPurchaseService {
    @Autowired
    private LanternPurchaseMapper lanternPurchaseMapper;

    @Autowired
    private SystemConfigMapper systemConfigMapper;

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
        SystemConfigExample e = new SystemConfigExample();
        e.createCriteria().andConfigKeyEqualTo("promotion_lantern");
        List<SystemConfig> configs = systemConfigMapper.selectByExample(e);
        LanternPurchaseExample e2 = new LanternPurchaseExample();
        if (!configs.isEmpty()) {
            String lanternIds = configs.get(0).getConfigValue();
            e2.createCriteria().andLanternIdIn(Arrays.stream(lanternIds.split(",")).collect(Collectors.toList()));
        }
        e2.setOrderByClause("create_time ASC");
        List<LanternBlessingDTO> list = lanternPurchaseMapper.selectDistinctLimitByExample(e2, num);
        
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
}
