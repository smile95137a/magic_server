package com.qiyuan.web.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.MasterMapper;
import com.qiyuan.web.dto.QapItemVO;
import com.qiyuan.web.dto.request.MasterRequest;
import com.qiyuan.web.dto.response.MasterAdminVO;
import com.qiyuan.web.dto.response.MasterVO;
import com.qiyuan.web.entity.Master;
import com.qiyuan.web.entity.example.MasterExample;
import com.qiyuan.web.util.FileUtil;
import com.qiyuan.web.util.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MasterService {

    private final MasterMapper masterMapper;

    @Value("${upload.image-path.master}")
    private String masterDir;

    public List<MasterVO> getAvailableMasterList() {
        MasterExample e = new MasterExample();
        e.createCriteria().andStatusEqualTo(true);
        e.setOrderByClause("sort ASC");
        return masterMapper.selectByExample(e).stream()
                .map(this::convertMasterToVo)
                .collect(Collectors.toList());
    }

    public List<MasterAdminVO> getAllMasterList() {
        MasterExample e = new MasterExample();
        e.setOrderByClause("sort ASC");
        return masterMapper.selectByExample(e).stream()
                .map(this::convertMasterToAdminVo)
                .collect(Collectors.toList());
    }

    public MasterAdminVO getMasterByCode(String code) {
        Master entity = masterMapper.selectByPrimaryKey(code);
        if (entity == null) {
            throw new ApiException("老師不存在");
        }
        return convertMasterToAdminVo(entity);
    }

    @Transactional
    public boolean addMaster(MasterRequest r) {
        MasterExample e = new MasterExample();
        e.createCriteria().andCodeEqualTo(r.getCode());
        List<Master> existed = masterMapper.selectByExample(e);
        if (existed != null && !existed.isEmpty()) {
            throw new ApiException("請勿設定重複的代號");
        }

        String ext = "";
        if (r.getImageBase64() != null) {
            String path = FileUtil.base64ToImage(r.getImageBase64(), masterDir, r.getCode());
            String fileName = Paths.get(path).getFileName().toString();
            ext = fileName.contains(".") ? fileName.substring(fileName.indexOf(".") + 1) : "";
        }

        Master m = Master.builder()
                .code(r.getCode())
                .name(r.getName())
                .title(StringUtils.defaultString(r.getTitle()))
                .mainStar(StringUtils.defaultString(r.getMainStar()))
                .bio(StringUtils.defaultString(r.getBio()))
                .experience(StringUtils.defaultString(r.getExperience()))
                .personalItems(StringUtils.defaultString(r.getPersonalItems()))
                .sort(r.getSort())
                .status(r.getStatus())
                .servicesJson(JsonUtil.toJson(r.getServiceItem()))
                .serviceTime(StringUtils.defaultString(r.getServiceTime()))
                .imageExt(ext.toLowerCase(Locale.ROOT))
                .build();

        boolean inserted = masterMapper.insert(m) > 0;
        log.info("新增老師 [{}]，結果: {}", r.getCode(), inserted ? "成功" : "失敗");
        return inserted;
    }

    @Transactional
    public boolean modifyMaster(MasterRequest r) {
        Master master = masterMapper.selectByPrimaryKey(r.getCode());
        if (master == null) {
            throw new ApiException("老師不存在，無法修改");
        }

        master.setName(r.getName());
        master.setTitle(r.getTitle());
        master.setMainStar(r.getMainStar());
        master.setBio(r.getBio());
        master.setExperience(r.getExperience());
        master.setPersonalItems(r.getPersonalItems());
        master.setServiceTime(r.getServiceTime());

        if (r.getServiceItem() != null) {
            master.setServicesJson(JsonUtil.toJson(r.getServiceItem()));
        }
        master.setStatus(r.getStatus());

        try {
            if (StringUtils.isNotBlank(r.getImageBase64())) {
                if (StringUtils.isNotBlank(master.getImageExt())) {
                    String fileToDelete = FileUtil.concatFilePath(
                            masterDir, String.format("%s.%s", master.getCode(), master.getImageExt()));
                    Files.deleteIfExists(Paths.get(fileToDelete));
                    log.debug("刪除了舊照片: {}", fileToDelete);
                }

                String path = FileUtil.base64ToImage(r.getImageBase64(), masterDir, master.getCode());
                String fileName = Paths.get(path).getFileName().toString();
                String ext = fileName.contains(".") ? fileName.substring(fileName.indexOf(".") + 1) : "";
                master.setImageExt(ext.toLowerCase(Locale.ROOT));
                log.info("老師 [{}] 新照片已上傳: {}", master.getCode(), path);
            }
        } catch (Exception ex) {
            log.error("刪除或更新老師 [{}] 照片失敗", master.getCode(), ex);
        }

        boolean updated = masterMapper.updateByPrimaryKeySelective(master) > 0;
        log.info("修改老師 [{}]，結果: {}", r.getCode(), updated ? "成功" : "失敗");
        return updated;
    }

    private MasterVO convertMasterToVo(Master m) {
        return MasterVO.builder()
                .name(m.getName())
                .code(m.getCode())
                .title(m.getTitle())
                .mainStar(m.getMainStar())
                .bio(m.getBio())
                .experience(m.getExperience())
                .personalItems(m.getPersonalItems().replace(",", "、"))
                .serviceItem(JsonUtil.fromJsonList(m.getServicesJson(), QapItemVO.class))
                .serviceTime(m.getServiceTime())
                .sort(m.getSort())
                .imageBase64(FileUtil.imageToBase64(FileUtil.concatFilePath(
                        masterDir, String.format("%s.%s", m.getCode(), m.getImageExt().toLowerCase(Locale.ROOT)))))
                .build();
    }

    private MasterAdminVO convertMasterToAdminVo(Master m) {
        return MasterAdminVO.builder()
                .name(m.getName())
                .code(m.getCode())
                .title(m.getTitle())
                .mainStar(m.getMainStar())
                .bio(m.getBio())
                .experience(m.getExperience())
                .personalItems(m.getPersonalItems())
                .serviceItem(JsonUtil.fromJsonList(m.getServicesJson(), QapItemVO.class))
                .serviceTime(m.getServiceTime())
                .sort(m.getSort())
                .imageBase64(FileUtil.imageToBase64(FileUtil.concatFilePath(
                        masterDir, String.format("%s.%s", m.getCode(), m.getImageExt().toLowerCase(Locale.ROOT)))))
                .status(m.getStatus())
                .build();
    }
}
