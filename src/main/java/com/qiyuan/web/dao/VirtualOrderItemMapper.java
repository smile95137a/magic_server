package com.qiyuan.web.dao;

import com.qiyuan.web.entity.VirtualOrderItem;
import com.qiyuan.web.entity.example.VirtualOrderItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VirtualOrderItemMapper {
    long countByExample(VirtualOrderItemExample example);

    int deleteByExample(VirtualOrderItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VirtualOrderItem row);

    int insertSelective(VirtualOrderItem row);

    List<VirtualOrderItem> selectByExample(VirtualOrderItemExample example);

    VirtualOrderItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") VirtualOrderItem row, @Param("example") VirtualOrderItemExample example);

    int updateByExample(@Param("row") VirtualOrderItem row, @Param("example") VirtualOrderItemExample example);

    int updateByPrimaryKeySelective(VirtualOrderItem row);

    int updateByPrimaryKey(VirtualOrderItem row);
}