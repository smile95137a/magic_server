package com.qiyuan.web.dao;

import com.qiyuan.web.entity.LanternPurchase;
import com.qiyuan.web.entity.example.LanternPurchaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LanternPurchaseMapper {
    long countByExample(LanternPurchaseExample example);

    int deleteByExample(LanternPurchaseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LanternPurchase record);

    int insertSelective(LanternPurchase record);

    List<LanternPurchase> selectByExample(LanternPurchaseExample example);

    LanternPurchase selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LanternPurchase record, @Param("example") LanternPurchaseExample example);

    int updateByExample(@Param("record") LanternPurchase record, @Param("example") LanternPurchaseExample example);

    int updateByPrimaryKeySelective(LanternPurchase record);

    int updateByPrimaryKey(LanternPurchase record);
}