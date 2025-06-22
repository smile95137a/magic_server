package com.qiyuan.web.service;

import com.qiyuan.web.dao.BannerMapper;
import com.qiyuan.web.entity.Banner;
import com.qiyuan.web.entity.example.BannerExample;
import com.qiyuan.web.util.FileUtil;
import com.qiyuan.web.dto.response.BannerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BannerService {

    @Autowired
    private BannerMapper bannerMapper;

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
}
