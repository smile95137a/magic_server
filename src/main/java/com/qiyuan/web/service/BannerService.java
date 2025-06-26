package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.BannerMapper;
import com.qiyuan.web.dto.request.BannerRequest;
import com.qiyuan.web.dto.request.ModifyBannerRequest;
import com.qiyuan.web.dto.request.NewBannerRequest;
import com.qiyuan.web.dto.response.BannerAdminVO;
import com.qiyuan.web.entity.Banner;
import com.qiyuan.web.entity.example.BannerExample;
import com.qiyuan.web.util.FileUtil;
import com.qiyuan.web.dto.response.BannerVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BannerService {

    private Logger logger = LoggerFactory.getLogger(BannerService.class);

    @Autowired
    private BannerMapper bannerMapper;

    @Value("${upload.image-path.banner}")
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
                        .imgBase64(FileUtil.imageToBase64(FileUtil.concatFilePath(bannerDir, b.getImageName())))
                        .sort(b.getSort())
                        .build())
                .collect(Collectors.toList());
    }

    public List<BannerAdminVO> getAllBannerByType(String type) {
        BannerExample e = new BannerExample();
        e.createCriteria().andTypeEqualTo(type);
        e.setOrderByClause("[sort] DESC");
        List<Banner> banners = bannerMapper.selectByExample(e);
        return banners.stream()
                .map(b -> BannerAdminVO.builder()
                        .id(b.getId())
                        .type(b.getType())
                        .description(b.getDescription())
                        .imageName(b.getImageName())
                        .imageBase64(FileUtil.imageToBase64(FileUtil.concatFilePath(bannerDir, b.getImageName())))
                        .availableFrom(b.getAvailableFrom())
                        .availableUntil(b.getAvailableUntil())
                        .sort(b.getSort())
                        .build()
                ).collect(Collectors.toList());
    }

    public boolean addNewBanner(NewBannerRequest banner) {
        BannerExample e = new BannerExample();
        e.createCriteria().andTypeEqualTo(banner.getType()).andSortEqualTo(banner.getSort());
        List<Banner> existed = bannerMapper.selectByExample(e);
        if (existed != null && !existed.isEmpty()) {
            throw new ApiException("請勿重複設定相同的序列");
        }
        

        String path = FileUtil.base64ToImage(banner.getImageBase64(), bannerDir, banner.getFilename());
        logger.info("成功上傳banner: " + path);

        Banner b = Banner.builder()
                .availableUntil(banner.getAvailableUntil())
                .availableFrom(banner.getAvailableFrom())
                .sort(banner.getSort())
                .type(banner.getType())
                .description(banner.getDescription())
                .imageName(banner.getFilename())
                .build();

        return bannerMapper.insertSelective(b) > 0;
    }

    public boolean modifyBanner(ModifyBannerRequest banner) {
        BannerExample e = new BannerExample();
        e.createCriteria().andTypeEqualTo(banner.getType()).andSortEqualTo(banner.getSort());
        List<Banner> existed = bannerMapper.selectByExample(e);
        if (existed != null && !existed.isEmpty() && existed.get(0).getId() != banner.getId()) {
            throw new ApiException("請勿重複設定相同的序列");
        }

        Banner target = bannerMapper.selectByPrimaryKey(banner.getId());
        if (target == null) throw new ApiException("查無資料");

        target.setSort(banner.getSort());
        target.setAvailableFrom(banner.getAvailableFrom());
        target.setAvailableUntil(banner.getAvailableFrom());

        return bannerMapper.updateByPrimaryKeySelective(target) > 0;

    }



}
