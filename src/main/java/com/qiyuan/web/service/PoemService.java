package com.qiyuan.web.service;

import com.qiyuan.web.dao.PoemMapper;
import com.qiyuan.web.entity.Poem;
import com.qiyuan.web.entity.example.PoemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoemService {

    @Autowired
    private PoemMapper poemMapper;

    public List<Poem> getPoemList() {
        PoemExample e = new PoemExample();
        e.setOrderByClause("sort ASC");
        return poemMapper.selectByExample(e);
    }

}
