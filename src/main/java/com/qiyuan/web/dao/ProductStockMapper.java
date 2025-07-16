package com.qiyuan.web.dao;

import com.qiyuan.web.entity.ProductStock;
import com.qiyuan.web.entity.example.ProductStockExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductStockMapper {
    long countByExample(ProductStockExample example);

    int deleteByExample(ProductStockExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductStock row);

    int insertSelective(ProductStock row);

    List<ProductStock> selectByExample(ProductStockExample example);

    ProductStock selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") ProductStock row, @Param("example") ProductStockExample example);

    int updateByExample(@Param("row") ProductStock row, @Param("example") ProductStockExample example);

    int updateByPrimaryKeySelective(ProductStock row);

    int updateByPrimaryKey(ProductStock row);
}