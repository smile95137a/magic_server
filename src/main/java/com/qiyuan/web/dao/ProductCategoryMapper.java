package com.qiyuan.web.dao;

import com.qiyuan.web.entity.ProductCategory;
import com.qiyuan.web.entity.example.ProductCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductCategoryMapper {
    long countByExample(ProductCategoryExample example);

    int deleteByExample(ProductCategoryExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    List<ProductCategory> selectByExample(ProductCategoryExample example);

    ProductCategory selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProductCategory record, @Param("example") ProductCategoryExample example);

    int updateByExample(@Param("record") ProductCategory record, @Param("example") ProductCategoryExample example);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);
}