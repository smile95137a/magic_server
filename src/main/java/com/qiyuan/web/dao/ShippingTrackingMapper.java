package com.qiyuan.web.dao;


import com.qiyuan.web.entity.ShippingTracking;
import java.util.List;

import com.qiyuan.web.entity.example.ShippingTrackingExample;
import org.apache.ibatis.annotations.Param;

public interface ShippingTrackingMapper {
    long countByExample(ShippingTrackingExample example);

    int deleteByExample(ShippingTrackingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ShippingTracking row);

    int insertSelective(ShippingTracking row);

    List<ShippingTracking> selectByExample(ShippingTrackingExample example);

    ShippingTracking selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") ShippingTracking row, @Param("example") ShippingTrackingExample example);

    int updateByExample(@Param("row") ShippingTracking row, @Param("example") ShippingTrackingExample example);

    int updateByPrimaryKeySelective(ShippingTracking row);

    int updateByPrimaryKey(ShippingTracking row);
}