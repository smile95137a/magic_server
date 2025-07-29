package com.qiyuan.web.dao;

import com.qiyuan.web.entity.GodPurchase;
import com.qiyuan.web.entity.example.GodPurchaseExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GodPurchaseMapper {
    long countByExample(GodPurchaseExample example);

    int deleteByExample(GodPurchaseExample example);

    int deleteByPrimaryKey(String id);

    int insert(GodPurchase row);

    int insertSelective(GodPurchase row);

    List<GodPurchase> selectByExample(GodPurchaseExample example);

    GodPurchase selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") GodPurchase row, @Param("example") GodPurchaseExample example);

    int updateByExample(@Param("row") GodPurchase row, @Param("example") GodPurchaseExample example);

    int updateByPrimaryKeySelective(GodPurchase row);

    int updateByPrimaryKey(GodPurchase row);
}