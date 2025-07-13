package com.qiyuan.web.dao;

import com.qiyuan.web.entity.ProductSpec;
import com.qiyuan.web.entity.ProductSpecExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductSpecMapper {
    long countByExample(ProductSpecExample example);

    int deleteByExample(ProductSpecExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductSpec row);

    int insertSelective(ProductSpec row);

    List<ProductSpec> selectByExample(ProductSpecExample example);

    ProductSpec selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") ProductSpec row, @Param("example") ProductSpecExample example);

    int updateByExample(@Param("row") ProductSpec row, @Param("example") ProductSpecExample example);

    int updateByPrimaryKeySelective(ProductSpec row);

    int updateByPrimaryKey(ProductSpec row);
}