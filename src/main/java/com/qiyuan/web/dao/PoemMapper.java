package com.qiyuan.web.dao;

import com.qiyuan.web.entity.Poem;
import com.qiyuan.web.entity.example.PoemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PoemMapper {
    long countByExample(PoemExample example);

    int deleteByExample(PoemExample example);

    int deleteByPrimaryKey(String sequence);

    int insert(Poem record);

    int insertSelective(Poem record);

    List<Poem> selectByExample(PoemExample example);

    Poem selectByPrimaryKey(String sequence);

    int updateByExampleSelective(@Param("record") Poem record, @Param("example") PoemExample example);

    int updateByExample(@Param("record") Poem record, @Param("example") PoemExample example);

    int updateByPrimaryKeySelective(Poem record);

    int updateByPrimaryKey(Poem record);
}