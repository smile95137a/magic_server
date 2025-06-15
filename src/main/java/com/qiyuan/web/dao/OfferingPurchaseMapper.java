package com.qiyuan.web.dao;

import com.qiyuan.web.entity.OfferingPurchase;
import com.qiyuan.web.entity.example.OfferingPurchaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OfferingPurchaseMapper {
    long countByExample(OfferingPurchaseExample example);

    int deleteByExample(OfferingPurchaseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OfferingPurchase record);

    int insertSelective(OfferingPurchase record);

    List<OfferingPurchase> selectByExample(OfferingPurchaseExample example);

    OfferingPurchase selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OfferingPurchase record, @Param("example") OfferingPurchaseExample example);

    int updateByExample(@Param("record") OfferingPurchase record, @Param("example") OfferingPurchaseExample example);

    int updateByPrimaryKeySelective(OfferingPurchase record);

    int updateByPrimaryKey(OfferingPurchase record);
}