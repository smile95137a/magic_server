package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.MasterMapper;
import com.qiyuan.web.dto.QapItemVO;
import com.qiyuan.web.dto.request.MasterRequest;
import com.qiyuan.web.dto.response.MasterAdminVO;
import com.qiyuan.web.entity.Master;
import com.qiyuan.web.entity.example.MasterExample;
import com.qiyuan.web.util.FileUtil;
import com.qiyuan.web.util.JsonUtil;
import com.qiyuan.web.dto.response.MasterVO;
import com.qiyuan.web.dto.response.QaItemVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class MasterService {

    private Logger logger = LoggerFactory.getLogger(MasterService.class);

   private final MasterMapper masterMapper;

    public MasterService(MasterMapper masterMapper) {
        this.masterMapper = masterMapper;
    }

    @Value("${upload.image-path.master}")
    private String masterDir;

    public List<MasterVO> getAvailableMasterList() {
        MasterExample e = new MasterExample();
        e.createCriteria().andStatusEqualTo(true);
        e.setOrderByClause("sort ASC");
        return masterMapper.selectByExample(e).stream().map(this::convertMasterToVo).collect(Collectors.toList());
    }

    public List<MasterAdminVO> getAllMasterList() {
        MasterExample e = new MasterExample();
        e.setOrderByClause("sort ASC");
        return masterMapper.selectByExample(e).stream().map(this::convertMasterToAdminVo).collect(Collectors.toList());
    }

    public MasterAdminVO getMasterByCode(String code) {
        Master entity = masterMapper.selectByPrimaryKey(code);
        if (entity == null) throw new ApiException("老師不存在");
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
            ext = fileName.indexOf(".") > 0 ? fileName.substring(fileName.indexOf(".") + 1) : "";
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
                .imageExt(ext.toLowerCase(Locale.ROOT))
                .build();

        return masterMapper.insert(m) > 0;
    }

    @Transactional
    public boolean modifyMaster(MasterRequest r) {
        Master master = masterMapper.selectByPrimaryKey(r.getCode());

        master.setName(r.getName());
        master.setTitle(r.getTitle());
        master.setMainStar(r.getMainStar());
        master.setBio(r.getBio());
        master.setExperience(r.getExperience());
        master.setPersonalItems(r.getPersonalItems());
        if (r.getServiceItem() != null) {
            master.setServicesJson(JsonUtil.toJson(r.getServiceItem()));
        }
        master.setStatus(r.getStatus());
        try {
            if (StringUtils.isNotBlank(r.getImageBase64())) {
                if (StringUtils.isNotBlank(master.getImageExt())) {
                    String fileToDelete = FileUtil.concatFilePath(masterDir, String.format("%s.%s", master.getCode(), master.getImageExt()));
                    Files.deleteIfExists(Paths.get(fileToDelete));
                }

                String path = FileUtil.base64ToImage(r.getImageBase64(), masterDir, master.getCode());
                String fileName = Paths.get(path).getFileName().toString();
                String ext = fileName.indexOf(".") > 0 ? fileName.substring(fileName.indexOf(".") + 1) : "";
                master.setImageExt(ext.toLowerCase(Locale.ROOT));
            }
        } catch (Exception ex) {
            logger.error("刪除老師舊照片失敗", ex);
        }

        return masterMapper.updateByPrimaryKeySelective(master) > 0;
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
                .sort(m.getSort())
                .imageBase64(FileUtil.imageToBase64(FileUtil.concatFilePath(masterDir,
                        String.format("%s.%s", m.getCode(), m.getImageExt().toLowerCase(Locale.ROOT)))))
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
                .sort(m.getSort())
                .imageBase64(FileUtil.imageToBase64(FileUtil.concatFilePath(masterDir,
                        String.format("%s.%s", m.getCode(), m.getImageExt().toLowerCase(Locale.ROOT)))))
                .status(m.getStatus())
                .build();

    }
}
