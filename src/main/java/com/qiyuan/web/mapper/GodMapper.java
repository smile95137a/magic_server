package com.qiyuan.web.mapper;

import com.qiyuan.web.model.God;
import com.qiyuan.web.model.GodExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GodMapper {
    long countByExample(GodExample example);

    int deleteByExample(GodExample example);

    int deleteByPrimaryKey(String id);

    int insert(God record);

    int insertSelective(God record);

    List<God> selectByExample(GodExample example);

    God selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") God record, @Param("example") GodExample example);

    int updateByExample(@Param("record") God record, @Param("example") GodExample example);

    int updateByPrimaryKeySelective(God record);

    int updateByPrimaryKey(God record);
}