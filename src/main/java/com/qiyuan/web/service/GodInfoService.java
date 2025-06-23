package com.qiyuan.web.service;

import com.qiyuan.web.dao.GodInfoMapper;
import com.qiyuan.web.entity.GodInfo;
import com.qiyuan.web.entity.example.GodInfoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GodInfoService {

    @Autowired
    private GodInfoMapper godInfoMapper;

    public GodInfo getGodInfo(String userId, String godId) {
        GodInfoExample e = new GodInfoExample();
        e.createCriteria().andUserIdEqualTo(userId).andGodIdEqualTo(godId);
        List<GodInfo> godInfos = godInfoMapper.selectByExample(e);
        return godInfos != null && !godInfos.isEmpty() ? godInfos.get(0) : null;
    }

    @Transactional
    public boolean addGodInfo(GodInfo god) {
        return godInfoMapper.insertSelective(god) > 0;
    }

    @Transactional
    public boolean updateGodInfo(GodInfo god) {
        return godInfoMapper.updateByPrimaryKey(god) > 0;
    }

}
