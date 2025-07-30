package com.qiyuan.web.dao;

import com.qiyuan.web.entity.PoeThrow;
import com.qiyuan.web.entity.example.PoeThrowExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PoeThrowMapper {
    long countByExample(PoeThrowExample example);

    int deleteByExample(PoeThrowExample example);

    int deleteByPrimaryKey(String userId);

    int insert(PoeThrow row);

    int insertSelective(PoeThrow row);

    List<PoeThrow> selectByExample(PoeThrowExample example);

    PoeThrow selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("row") PoeThrow row, @Param("example") PoeThrowExample example);

    int updateByExample(@Param("row") PoeThrow row, @Param("example") PoeThrowExample example);

    int updateByPrimaryKeySelective(PoeThrow row);

    int updateByPrimaryKey(PoeThrow row);
}