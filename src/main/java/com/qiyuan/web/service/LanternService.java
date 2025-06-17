package com.qiyuan.web.service;

import com.qiyuan.web.dao.LanternMapper;
import com.qiyuan.web.entity.Lantern;
import com.qiyuan.web.entity.example.LanternExample;
import com.qiyuan.web.util.JsonUtil;
import com.qiyuan.web.vo.QaItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanternService {

    @Autowired
    private LanternMapper lanternMapper;

    public List<Lantern> getLanternList() {
        LanternExample e = new LanternExample();
        e.setOrderByClause("sort ASC");
        return lanternMapper.selectByExample(e);
    }
}
