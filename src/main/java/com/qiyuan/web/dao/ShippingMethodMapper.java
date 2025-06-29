package com.qiyuan.web.dao;

import com.qiyuan.web.entity.ShippingMethod;
import com.qiyuan.web.entity.example.ShippingMethodExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShippingMethodMapper {
    long countByExample(ShippingMethodExample example);

    int deleteByExample(ShippingMethodExample example);

    int deleteByPrimaryKey(String id);

    int insert(ShippingMethod record);

    int insertSelective(ShippingMethod record);

    List<ShippingMethod> selectByExample(ShippingMethodExample example);

    ShippingMethod selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ShippingMethod record, @Param("example") ShippingMethodExample example);

    int updateByExample(@Param("record") ShippingMethod record, @Param("example") ShippingMethodExample example);

    int updateByPrimaryKeySelective(ShippingMethod record);

    int updateByPrimaryKey(ShippingMethod record);
}