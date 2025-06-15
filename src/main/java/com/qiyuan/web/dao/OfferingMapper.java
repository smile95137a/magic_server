package com.qiyuan.web.dao;

import com.qiyuan.web.entity.Offering;
import com.qiyuan.web.entity.example.OfferingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OfferingMapper {
    long countByExample(OfferingExample example);

    int deleteByExample(OfferingExample example);

    int deleteByPrimaryKey(String id);

    int insert(Offering record);

    int insertSelective(Offering record);

    List<Offering> selectByExample(OfferingExample example);

    Offering selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Offering record, @Param("example") OfferingExample example);

    int updateByExample(@Param("record") Offering record, @Param("example") OfferingExample example);

    int updateByPrimaryKeySelective(Offering record);

    int updateByPrimaryKey(Offering record);
}