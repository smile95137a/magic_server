package com.qiyuan.web.dao;

import com.qiyuan.web.entity.SystemConfig;
import com.qiyuan.web.entity.SystemConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemConfigMapper {
    long countByExample(SystemConfigExample example);

    int deleteByExample(SystemConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SystemConfig record);

    int insertSelective(SystemConfig record);

    List<SystemConfig> selectByExample(SystemConfigExample example);

    SystemConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SystemConfig record, @Param("example") SystemConfigExample example);

    int updateByExample(@Param("record") SystemConfig record, @Param("example") SystemConfigExample example);

    int updateByPrimaryKeySelective(SystemConfig record);

    int updateByPrimaryKey(SystemConfig record);
}