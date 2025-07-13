package com.qiyuan.web.dao;

import com.qiyuan.web.entity.ProductSpecStock;
import com.qiyuan.web.entity.example.ProductSpecStockExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSpecStockMapper {
    long countByExample(ProductSpecStockExample example);

    int deleteByExample(ProductSpecStockExample example);

    int deleteByPrimaryKey(Integer specId);

    int insert(ProductSpecStock row);

    int insertSelective(ProductSpecStock row);

    List<ProductSpecStock> selectByExample(ProductSpecStockExample example);

    ProductSpecStock selectByPrimaryKey(Integer specId);

    int updateByExampleSelective(@Param("row") ProductSpecStock row, @Param("example") ProductSpecStockExample example);

    int updateByExample(@Param("row") ProductSpecStock row, @Param("example") ProductSpecStockExample example);

    int updateByPrimaryKeySelective(ProductSpecStock row);

    int updateByPrimaryKey(ProductSpecStock row);
}