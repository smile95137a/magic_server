package com.qiyuan.web.dao;

import com.qiyuan.web.entity.PoeRank;
import com.qiyuan.web.entity.example.PoeRankExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PoeRankMapper {
    long countByExample(PoeRankExample example);

    int deleteByExample(PoeRankExample example);

    int deleteByPrimaryKey(String userId);

    int insert(PoeRank record);

    int insertSelective(PoeRank record);

    List<PoeRank> selectByExample(PoeRankExample example);

    PoeRank selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") PoeRank record, @Param("example") PoeRankExample example);

    int updateByExample(@Param("record") PoeRank record, @Param("example") PoeRankExample example);

    int updateByPrimaryKeySelective(PoeRank record);

    int updateByPrimaryKey(PoeRank record);
}