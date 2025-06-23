package com.qiyuan.web.service;

import com.qiyuan.web.dao.OfferingMapper;
import com.qiyuan.web.dao.OfferingPurchaseMapper;
import com.qiyuan.web.entity.Offering;
import com.qiyuan.web.entity.OfferingPurchase;
import com.qiyuan.web.entity.example.OfferingExample;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.RandomGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OfferingService {
    private final OfferingMapper offeringMapper;
    private final OfferingPurchaseMapper offeringPurchaseMapper;

    public OfferingService(OfferingMapper offeringMapper, OfferingPurchaseMapper offeringPurchaseMapper) {
        this.offeringMapper = offeringMapper;
        this.offeringPurchaseMapper = offeringPurchaseMapper;
    }

    public Offering getOfferingById(String id) {
        return offeringMapper.selectByPrimaryKey(id);
    }

    public List<Offering> getOfferingByIds(List<String> id) {
        OfferingExample e = new OfferingExample();
        e.createCriteria().andIdIn(id);
        return offeringMapper.selectByExample(e);
    }

    public List<Offering> getOffering() {
        OfferingExample e = new OfferingExample();
        return offeringMapper.selectByExample(e);
    }

    @Transactional
    public boolean addOfferingPurchase(String userId, String offeringId, String godId) {
        OfferingPurchase purchase = OfferingPurchase.builder()
                .offeringId(offeringId)
                .userId(userId)
                .godId(godId)
                .createTime(DateUtil.getCurrentDate())
                .build();
        return offeringPurchaseMapper.insertSelective(purchase) > 0;
    }

}
