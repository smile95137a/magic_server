package com.qiyuan.web.dao;

import com.qiyuan.web.entity.Product;
import com.qiyuan.web.entity.example.ProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductMapper {
    long countByExample(ProductExample example);

    int deleteByExample(ProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int decreaseStock(@Param("productId") Integer productId, @Param("qty") int qty);


    int insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    Product selectByPrimaryKey(Integer id);

    List<Product> selectPage(@Param("offset") int offset, @Param("size") int size, @Param("categoryId") String categoryId);

    List<Product> selectLimitsByExample(@Param("example")ProductExample example, @Param("limits") int limits);

    int updateByExampleSelective(@Param("record") Product record, @Param("example") ProductExample example);

    int updateByExample(@Param("record") Product record, @Param("example") ProductExample example);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}