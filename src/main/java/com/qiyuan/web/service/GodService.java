package com.qiyuan.web.service;

import com.qiyuan.web.dao.GodMapper;
import com.qiyuan.web.entity.God;
import com.qiyuan.web.entity.example.GodExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GodService {

    @Autowired
    private GodMapper godMapper;

    public List<God> getGodList() {
        GodExample e = new GodExample();
        e.setOrderByClause("sort ASC");
        e.createCriteria();
        return godMapper.selectByExample(e);
    }
}
