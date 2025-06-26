package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.BannerMapper;
import com.qiyuan.web.dto.request.BannerRequest;
import com.qiyuan.web.entity.Banner;
import com.qiyuan.web.entity.example.BannerExample;
import com.qiyuan.web.util.FileUtil;
import com.qiyuan.web.dto.response.BannerVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Value("${}")
    private String bannerDir;

    public List<BannerVO> getAvailableBannerByType(String type) {
        Date now = Calendar.getInstance().getTime();
        BannerExample e = new BannerExample();
        e.createCriteria().andAvailableFromLessThanOrEqualTo(now)
                .andAvailableUntilGreaterThanOrEqualTo(now)
                .andTypeEqualTo(type);
        e.setOrderByClause("sort ASC");
        List<Banner> banners = bannerMapper.selectByExample(e);

        return banners.stream()
                .map(b -> BannerVO.builder()
                        .imgBase64(FileUtil.imageToBase64(b.getImageUrl()))
                        .sort(b.getSort())
                        .build())
                .collect(Collectors.toList());
    }

    public List<Banner> getAllBannerByType(String type) {
        BannerExample e = new BannerExample();
        e.createCriteria().andTypeEqualTo(type);
        e.setOrderByClause("[order] DESC");
        return bannerMapper.selectByExample(e);
    }

    public boolean addNewBanner(BannerRequest banner) {
        BannerExample e = new BannerExample();
        e.createCriteria().andTypeEqualTo(banner.getType()).andOrderEqualTo(banner.getSort());
        List<Banner> existed = bannerMapper.selectByExample(e);
        if (existed != null && !existed.isEmpty()) {
            throw new ApiException("請勿重複設定相同的序列");
        }

        String path = FileUtil.base64ToImage(banner.getImageBase64(), bannerDir, banner.getFilename());

        Banner b = Banner.builder()
                .availableUntil(banner.getAvailableUntil())
                .availableFrom(banner.getAvailableFrom())
                .sort(banner.getSort())
                .type(banner.getType())
                .imageUrl(path)
                .build();

        return bannerMapper.insertSelective(b) > 0;
    }


}
