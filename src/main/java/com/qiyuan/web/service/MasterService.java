package com.qiyuan.web.service;

import com.qiyuan.web.dao.MasterMapper;
import com.qiyuan.web.entity.Master;
import com.qiyuan.web.entity.MasterServiceRequest;
import com.qiyuan.web.entity.example.MasterExample;
import com.qiyuan.web.util.FileUtil;
import com.qiyuan.web.util.JsonUtil;
import com.qiyuan.web.vo.MasterVO;
import com.qiyuan.web.vo.QaItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterService {
    @Autowired
    private MasterMapper masterMapper;

    public List<MasterVO> getMasterList() {
        MasterExample e = new MasterExample();
        e.createCriteria().andStatusEqualTo(true);
        e.setOrderByClause("sort ASC");
        return masterMapper.selectByExample(e).stream().map(this::convertMasterToVo).collect(Collectors.toList());
    }

    private MasterVO convertMasterToVo(Master m) {
        return MasterVO.builder()
                .personalItems(m.getPersonalItems())
                .title(m.getTitle())
                .code(m.getCode())
                .bio(m.getBio())
                .experience(m.getExperience())
                .mainStar(m.getMainStar())
                .name(m.getName())
                .serviceItem(JsonUtil.fromJsonList(m.getServicesJson(), QaItemVO.class))
                .sort(m.getSort())
                .imageBase64(FileUtil.imageToBase64(m.getImageUrl()))
                .build();
    }

}
