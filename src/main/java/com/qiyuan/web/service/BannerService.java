package com.qiyuan.web.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.BannerMapper;
import com.qiyuan.web.dto.request.ModifyBannerRequest;
import com.qiyuan.web.dto.request.NewBannerRequest;
import com.qiyuan.web.dto.response.BannerAdminVO;
import com.qiyuan.web.dto.response.BannerVO;
import com.qiyuan.web.entity.Banner;
import com.qiyuan.web.entity.example.BannerExample;
import com.qiyuan.web.util.FileUtil;

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
                        .url(b.getUrl())
                        .build())
                .collect(Collectors.toList());
    }

    public BannerAdminVO getBannerById(Integer id) {
        Banner banner = bannerMapper.selectByPrimaryKey(id);
        if (banner == null) throw new ApiException("橫幅不存在");
        return convertToAdminVO(banner);
    }

    private BannerAdminVO convertToAdminVO(Banner b) {
        return BannerAdminVO.builder()
                .id(b.getId())
                .type(b.getType())
                .description(b.getDescription())
                .imageName(b.getImageName())
                .imageBase64(FileUtil.imageToBase64(FileUtil.concatFilePath(bannerDir, b.getImageName())))
                .availableFrom(b.getAvailableFrom())
                .availableUntil(b.getAvailableUntil())
                .sort(b.getSort())
                .url(b.getUrl())
                .build();
    }


    public List<BannerAdminVO> getAllBannerByType(String type) {
        BannerExample e = new BannerExample();
        e.createCriteria().andTypeEqualTo(type);
        e.setOrderByClause("[sort] DESC");
        List<Banner> banners = bannerMapper.selectByExample(e);
        return banners.stream()
                .map(this::convertToAdminVO).collect(Collectors.toList());
    }

    public boolean addNewBanner(NewBannerRequest banner) {
        String path = FileUtil.base64ToImage(banner.getImageBase64(), bannerDir, banner.getFilename());
        logger.info("成功上傳banner: " + path);

        Banner b = Banner.builder()
                .availableUntil(banner.getAvailableUntil())
                .availableFrom(banner.getAvailableFrom())
                .sort(banner.getSort())
                .url(banner.getUrl())
                .type(banner.getType())
                .description(banner.getDescription())
                .imageName(banner.getFilename())
                .build();

        return bannerMapper.insertSelective(b) > 0;
    }

    @Transactional
    public boolean modifyBanner(ModifyBannerRequest req) {
        Banner target = bannerMapper.selectByPrimaryKey(req.getId());
        if (target == null) throw new ApiException("查無資料");

        boolean replaceImage = req.getImageBase64() != null && !req.getImageBase64().trim().isEmpty();
        String oldImageName = target.getImageName();
        String newFileName = null;
        String newFilePath = null;

        try {
            if (replaceImage) {
                // 若帶 filename 就用；否則依舊副檔名產生新檔名
                newFileName = (req.getFilename() != null && !req.getFilename().trim().isEmpty())
                        ? req.getFilename().trim()
                        : genNewFileName(oldImageName);

                // 先寫入新圖檔，避免先刪舊圖而中途失敗
                newFilePath = FileUtil.base64ToImage(req.getImageBase64(), bannerDir, newFileName);
                logger.info("編輯上傳新 banner 圖片成功: {}", newFilePath);
            }

            // 更新一般欄位
            target.setSort(req.getSort());
            target.setAvailableFrom(req.getAvailableFrom());
            target.setAvailableUntil(req.getAvailableUntil());
            target.setUrl(req.getUrl());
            target.setDescription(req.getDescription());
            // 若不允許修改 type，刪除下一行
            if (req.getType() != null && !req.getType().trim().isEmpty()) {
                target.setType(req.getType());
            }
            if (replaceImage) {
                target.setImageName(newFileName);
            }

            int updated = bannerMapper.updateByPrimaryKeySelective(target);
            if (updated <= 0) {
                // DB 失敗 → 回滾剛寫入的新圖
                if (replaceImage && newFilePath != null) {
                    try { java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get(newFilePath)); } catch (Exception ignore) {}
                }
                return false;
            }

            // DB 成功 → 若換圖，刪除舊圖
            if (replaceImage && oldImageName != null && !oldImageName.equals(newFileName)) {
                String oldPath = FileUtil.concatFilePath(bannerDir, oldImageName);
                try {
                    boolean deleted = java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get(oldPath));
                    if (!deleted) logger.warn("舊 banner 圖片刪除失敗或不存在: {}", oldPath);
                } catch (Exception ex) {
                    logger.warn("刪除舊 banner 圖片異常: {}", oldPath, ex);
                }
            }

            return true;

        } catch (Exception ex) {
            logger.error("修改 Banner 發生例外，嘗試回滾新檔: {}", ex.getMessage(), ex);
            if (replaceImage && newFilePath != null) {
                try { java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get(newFilePath)); } catch (Exception ignore) {}
            }
            throw ex; // 交給 @Transactional 回滾 DB
        }
    }

    /** 依舊檔名產生新檔名（保留副檔名） */
    private String genNewFileName(String oldImageName) {
        String ext = "";
        if (oldImageName != null) {
            int idx = oldImageName.lastIndexOf('.');
            if (idx >= 0) ext = oldImageName.substring(idx);
        }
        return System.currentTimeMillis() + ext; // 例如：1693212345678.png
    }



}
