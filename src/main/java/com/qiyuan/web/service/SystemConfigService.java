package com.qiyuan.web.service;

import com.qiyuan.web.dao.SystemConfigMapper;
import com.qiyuan.web.dto.SenderInfoDto;
import com.qiyuan.web.entity.SystemConfig;
import com.qiyuan.web.entity.SystemConfigExample;
import com.qiyuan.web.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    public List<String> getLanternPromotion() {
        SystemConfig systemConfig = this.getSystemConfig("promotion_lantern");
        if (systemConfig != null) {
            String[] strings = systemConfig.getConfigValue().split(",");
            return Arrays.stream(strings).collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

    public SystemConfig getSystemConfig(String key) {
        SystemConfigExample e = new SystemConfigExample();
        e.createCriteria().andConfigKeyEqualTo(key);
        List<SystemConfig> configs = systemConfigMapper.selectByExample(e);
        if (!configs.isEmpty()) {
            return configs.get(0);
        }
        return null;
    }

    public long getLanternCount() {
        SystemConfig systemConfig = this.getSystemConfig("purchase_lantern_count");
        if (systemConfig != null) {
            return Long.valueOf(systemConfig.getConfigValue());
        }
        return 0l;
    }

    public boolean insertSystemConfig(SystemConfig config) {
        return systemConfigMapper.insert(config) > 0;
    }

    public boolean updateSystemConfig(SystemConfig config) {
        return systemConfigMapper.updateByPrimaryKeySelective(config) > 0;
    }

    public SenderInfoDto getSenderInfo() {
        try {
            SystemConfig systemConfig = this.getSystemConfig("sender_info");
            if (StringUtils.isNotBlank(systemConfig.getConfigValue())) {
                return JsonUtil.fromJson(systemConfig.getConfigValue(), SenderInfoDto.class);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return SenderInfoDto.builder().build();
    }

    public boolean updateSenderInfo(SenderInfoDto dto) {
        SystemConfig systemConfig = this.getSystemConfig("sender_info");
        String value = JsonUtil.toJson(dto);
        systemConfig.setConfigValue(value);
        updateSystemConfig(systemConfig);
        return true;
    }

}
