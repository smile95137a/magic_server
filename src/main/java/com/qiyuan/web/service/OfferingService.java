package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.GodMapper;
import com.qiyuan.web.dao.OfferingMapper;
import com.qiyuan.web.dao.OfferingPurchaseMapper;
import com.qiyuan.web.dao.UserMapper;
import com.qiyuan.web.dto.request.RecordPeriodRequest;
import com.qiyuan.web.dto.response.OfferingVO;
import com.qiyuan.web.dto.response.RecordVO;
import com.qiyuan.web.entity.God;
import com.qiyuan.web.entity.Offering;
import com.qiyuan.web.entity.OfferingPurchase;
import com.qiyuan.web.entity.User;
import com.qiyuan.web.entity.example.GodExample;
import com.qiyuan.web.entity.example.OfferingExample;
import com.qiyuan.web.entity.example.OfferingPurchaseExample;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.FileUtil;
import com.qiyuan.web.util.RandomGenerator;
import com.qiyuan.web.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OfferingService {

    @Value("${upload.image-path.offering}")
    private String offeringDir;

    private final OfferingMapper offeringMapper;
    private final OfferingPurchaseMapper offeringPurchaseMapper;
    private final GodMapper godMapper;
    private final UserMapper userMapper;

    public OfferingService(OfferingMapper offeringMapper, OfferingPurchaseMapper offeringPurchaseMapper, GodMapper godMapper, UserMapper userMapper) {
        this.offeringMapper = offeringMapper;
        this.offeringPurchaseMapper = offeringPurchaseMapper;
        this.godMapper = godMapper;
        this.userMapper = userMapper;
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

    public boolean addOffering(OfferingVO r) {
        if (!StringUtils.isNoneEmpty(r.getName(), r.getImageBase64())) {
            throw new ApiException("新增失敗, 參數不可為空");
        }

        String uuid = RandomGenerator.getUUID();

        String path = FileUtil.base64ToImage(r.getImageBase64(), offeringDir, uuid);
        String fileName = Paths.get(path).getFileName().toString();
        Offering offering = Offering.builder()
                .id(uuid)
                .name(r.getName())
                .points(r.getPoints() == null ? 0 : r.getPoints())
                .price(r.getPrice() == null ? 0 : r.getPrice())
                .imageExt(fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(Locale.ROOT)).build();
        return offeringMapper.insert(offering) > 0;
    }

    public boolean modifyOffering(OfferingVO r) {
        if (StringUtils.isBlank(r.getId())) {
            throw new ApiException("新增失敗, 查無資料");
        }
        Offering offering1 = offeringMapper.selectByPrimaryKey(r.getId());
        if (StringUtils.isNotBlank(r.getName())) {
            offering1.setName(r.getName());
        }

        if (StringUtils.isNotBlank(r.getImageBase64())) {
            String path = FileUtil.base64ToImage(r.getImageBase64(), offeringDir, r.getId());
            String fileName = Paths.get(path).getFileName().toString();
            offering1.setImageExt(fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(Locale.ROOT));
        }

        if (r.getPoints() != null) {
            offering1.setPoints(r.getPoints());
        }

        if (r.getPrice() != null) {
            offering1.setPrice(r.getPrice());
        }

        return offeringMapper.updateByPrimaryKeySelective(offering1) > 0;
    }

    public boolean deleteById(String id) {
        return offeringMapper.deleteByPrimaryKey(id) > 0;
    }

    @Transactional
    public boolean addOfferingPurchase(String userId, String offeringId, String godId) {
        String id = RandomGenerator.getUUID();
        OfferingPurchase purchase = OfferingPurchase.builder()
                .id(id)
                .externalOrderNo(id)
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

    public List<RecordVO> getOfferingRecord(@RequestBody @Validated RecordPeriodRequest req) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(username);

        OfferingPurchaseExample e = new OfferingPurchaseExample();
        e.createCriteria().andUserIdEqualTo(user.getId()).andCreateTimeBetween(req.getStartTime(), req.getEndTime());
        List<OfferingPurchase> purchases = offeringPurchaseMapper.selectByExample(e);

        List<String> offeringIds = purchases.stream().map(OfferingPurchase::getOfferingId).collect(Collectors.toList());
        List<String> godIds = purchases.stream().map(OfferingPurchase::getGodId).collect(Collectors.toList());

        OfferingExample oe = new OfferingExample();
        oe.createCriteria().andIdIn(offeringIds);
        List<Offering> offeringList = offeringMapper.selectByExample(oe);
        Map<String, String> offeringMap = offeringList.stream()
                .collect(Collectors.toMap(Offering::getId, Offering::getName));


        GodExample ge = new GodExample();
        ge.createCriteria().andIdIn(godIds);
        List<God> gods = godMapper.selectByExample(ge);
        Map<String, String> godMap = gods.stream()
                .collect(Collectors.toMap(God::getId, God::getName));

        return purchases.stream().map(p -> RecordVO.builder()
                .date(DateFormatUtils.format(p.getCreateTime(), "yyyy/MM/dd HH:mm"))
                .item(offeringMap.get(p.getOfferingId()))
                .content(String.format("%s供品", godMap.get(p.getGodId())))
                .build()
        ).collect(Collectors.toList());
    }

}
