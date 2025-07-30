package com.qiyuan.web.service;

import com.qiyuan.web.dao.PoeRankMapper;
import com.qiyuan.web.dao.PoeThrowMapper;
import com.qiyuan.web.dao.UserMapper;
import com.qiyuan.web.dto.response.PoeRankVO;
import com.qiyuan.web.entity.PoeRank;
import com.qiyuan.web.entity.PoeThrow;
import com.qiyuan.web.entity.User;
import com.qiyuan.web.entity.example.PoeRankExample;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PoeService {

    private static Logger logger = LoggerFactory.getLogger(PoeService.class);

    @Autowired
    private PoeRankMapper poeRankMapper;

    @Autowired
    private PoeThrowMapper poeThrowMapper;

    @Autowired
    private UserMapper userMapper;

    public List<PoeRankVO> getPoeRank(int num) {
        PoeRankExample e = new PoeRankExample();
        e.setOrderByClause("times DESC");
        List<PoeRank> poeRanks = poeRankMapper.selectTopByExample(e, num);
        return poeRanks.stream().map(rank -> {
            User user = userMapper.selectByPrimaryKey(rank.getUserId());
            return PoeRankVO.builder()
                            .name(user.getNickname())
                            .times(rank.getTimes())
                            .build();

        }).collect(Collectors.toList());
    }


    public boolean addSiunnPoe(int n) {
        String currentUsername = SecurityUtils.getCurrentUsername();
        if (StringUtils.isBlank(currentUsername)) {
            logger.debug("anonymous user gets SiunnPoe");
            return false;
        }
        User user = userMapper.selectByUsername(currentUsername);
        PoeRank poeRank = poeRankMapper.selectByPrimaryKey(user.getId());
        Date now = DateUtil.getCurrentDate();
        if (poeRank == null) {
            PoeRank rank = PoeRank.builder().userId(user.getId())
                    .times(n)
                    .createTime(now)
                    .updateTime(now)
                    .build();
            return poeRankMapper.insertSelective(rank) > 0;
        } else {
            poeRank.setTimes(poeRank.getTimes() + n);
            poeRank.setUpdateTime(now);
            return poeRankMapper.updateByPrimaryKey(poeRank) > 0;
        }
    }

    public boolean addNormalPoe(int n) {
        String currentUsername = SecurityUtils.getCurrentUsername();
        if (StringUtils.isBlank(currentUsername)) {
            logger.debug("anonymous user gets SiunnPoe");
            return false;
        }
        User user = userMapper.selectByUsername(currentUsername);
        PoeThrow poeThrow = poeThrowMapper.selectByPrimaryKey(user.getId());
        Date now = DateUtil.getCurrentDate();
        if (poeThrow == null) {
            PoeThrow t = PoeThrow.builder()
                    .userId(user.getId())
                    .times(n)
                    .createTime(now)
                    .updateTime(now)
                    .build();
            return poeThrowMapper.insertSelective(t) > 0;
        } else {
            poeThrow.setTimes(poeThrow.getTimes() + n);
            poeThrow.setUpdateTime(now);
            return poeThrowMapper.updateByPrimaryKey(poeThrow) > 0;
        }
    }

    public PoeThrow getNormalPoe() {
        String currentUsername = SecurityUtils.getCurrentUsername();
        if (StringUtils.isBlank(currentUsername)) {
            logger.debug("anonymous user queries throw");
            return null;
        }
        User user = userMapper.selectByUsername(currentUsername);
        return poeThrowMapper.selectByPrimaryKey(user.getId());
    }

    public PoeRank getSiunnPoe() {
        String currentUsername = SecurityUtils.getCurrentUsername();
        if (StringUtils.isBlank(currentUsername)) {
            logger.debug("anonymous user queries siunnPoe");
            return null;
        }
        User user = userMapper.selectByUsername(currentUsername);
        return poeRankMapper.selectByPrimaryKey(user.getId());
    }

}
