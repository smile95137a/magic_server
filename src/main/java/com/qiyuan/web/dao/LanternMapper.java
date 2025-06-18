package com.qiyuan.web.dao;

import com.qiyuan.web.entity.Lantern;
import com.qiyuan.web.entity.example.LanternExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LanternMapper {
    long countByExample(LanternExample example);

    int deleteByExample(LanternExample example);

    int deleteByPrimaryKey(String id);

    int insert(Lantern record);

    int insertSelective(Lantern record);

    List<Lantern> selectByExample(LanternExample example);

    Lantern selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Lantern record, @Param("example") LanternExample example);

    int updateByExample(@Param("record") Lantern record, @Param("example") LanternExample example);

    int updateByPrimaryKeySelective(Lantern record);

    int updateByPrimaryKey(Lantern record);
}