package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.LanternMapper;
import com.qiyuan.web.entity.Lantern;
import com.qiyuan.web.entity.example.LanternExample;
import com.qiyuan.web.dto.response.LanternVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanternService {

    @Autowired
    private LanternMapper lanternMapper;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private LanternPurchaseService lanternPurchaseService;

    public List<Lantern> getLanternList() {
        LanternExample e = new LanternExample();
        e.setOrderByClause("sort ASC");
        return lanternMapper.selectByExample(e);
    }

    public LanternVO getLanternInfo(String code) {
        Lantern lantern = getLanternByCode(code);
        long count = lanternPurchaseService.countByLanternId(lantern.getId());
        long mask = systemConfigService.getLanternCount();

        return LanternVO.builder()
                .name(lantern.getName())
                .id(lantern.getId())
                .iconName(lantern.getIconName())
                .qaJson(lantern.getQaJson())
                .labelRight(lantern.getLabelRight())
                .count((int) (count + mask))
                .subtitle(lantern.getSubtitle())
                .build();
    }

    public Lantern getLanternByCode(String code) {
        LanternExample e = new LanternExample();
        e.createCriteria().andIconNameEqualTo(code);
        List<Lantern> lanterns = lanternMapper.selectByExample(e);
        if(lanterns.isEmpty()) {
            throw new ApiException("查無編號");
        }
        return lanterns.get(0);
    }
}
