package com.qiyuan.web.dao;

import com.qiyuan.web.entity.GodInfo;
import com.qiyuan.web.entity.example.GodInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GodInfoMapper {
    long countByExample(GodInfoExample example);

    int deleteByExample(GodInfoExample example);

    int deleteByPrimaryKey(@Param("userId") String userId, @Param("godId") String godId);

    int insert(GodInfo record);

    int insertSelective(GodInfo record);

    List<GodInfo> selectByExample(GodInfoExample example);

    GodInfo selectByPrimaryKey(@Param("userId") String userId, @Param("godId") String godId);

    int updateByExampleSelective(@Param("record") GodInfo record, @Param("example") GodInfoExample example);

    int updateByExample(@Param("record") GodInfo record, @Param("example") GodInfoExample example);

    int updateByPrimaryKeySelective(GodInfo record);

    int updateByPrimaryKey(GodInfo record);
}