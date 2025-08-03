package com.qiyuan.web.dao;

import com.qiyuan.web.entity.VirtualOrders;
import com.qiyuan.web.entity.example.VirtualOrdersExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VirtualOrdersMapper {
    long countByExample(VirtualOrdersExample example);

    int deleteByExample(VirtualOrdersExample example);

    int deleteByPrimaryKey(String id);

    int insert(VirtualOrders row);

    int insertSelective(VirtualOrders row);

    List<VirtualOrders> selectByExample(VirtualOrdersExample example);

    VirtualOrders selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") VirtualOrders row, @Param("example") VirtualOrdersExample example);

    int updateByExample(@Param("row") VirtualOrders row, @Param("example") VirtualOrdersExample example);

    int updateByPrimaryKeySelective(VirtualOrders row);

    int updateByPrimaryKey(VirtualOrders row);
}