package com.qiyuan.web.service;

import com.qiyuan.web.dao.SystemConfigMapper;
import com.qiyuan.web.entity.SystemConfig;
import com.qiyuan.web.entity.SystemConfigExample;
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

}
