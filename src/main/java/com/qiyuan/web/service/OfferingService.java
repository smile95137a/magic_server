package com.qiyuan.web.service;

import com.qiyuan.web.dao.OfferingMapper;
import com.qiyuan.web.dao.OfferingPurchaseMapper;
import com.qiyuan.web.dto.response.OfferingVO;
import com.qiyuan.web.entity.Offering;
import com.qiyuan.web.entity.OfferingPurchase;
import com.qiyuan.web.entity.example.OfferingExample;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class OfferingService {

    @Value("${upload.image-path.offering}")
    private String offeringDir;

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

    public List<OfferingVO> getOfferingVo() {
        OfferingExample e = new OfferingExample();
        List<Offering> offeringList = offeringMapper.selectByExample(e);
        return offeringList.stream().map(this::convertToVo).collect(Collectors.toList());
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

    public OfferingVO convertToVo(Offering o) {
        return OfferingVO.builder()
                .name(o.getName())
                .price(o.getPrice())
                .points(o.getPoints())
                .id(o.getId())
                .imageBase64(FileUtil.imageToBase64(FileUtil.concatFilePath(offeringDir, String.format("%s.%s", o.getId(), o.getImageExt().toLowerCase(Locale.ROOT)))))
                .build();
    }

}
