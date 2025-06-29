package com.qiyuan.web.dao;

import com.qiyuan.web.entity.ProductImage;
import com.qiyuan.web.entity.example.ProductImageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductImageMapper {
    long countByExample(ProductImageExample example);

    int deleteByExample(ProductImageExample example);

    int deleteByPrimaryKey(Long id);

    int deleteByProductId(Integer productId);

    int updateSortById(@Param("id") Long id, @Param("sort") Short sort);

    int deleteByProductIdAndType(@Param("productId") Integer productId, @Param("type")String type);

    List<ProductImage> selectByProductIdAndType(@Param("productId") Integer productId, @Param("type") String type);

    int insert(ProductImage record);

    int insertSelective(ProductImage record);

    Short selectMaxSortByProductId(Integer productId);

    List<ProductImage> selectByExample(ProductImageExample example);

    ProductImage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductImage record, @Param("example") ProductImageExample example);

    int updateByExample(@Param("record") ProductImage record, @Param("example") ProductImageExample example);

    int updateByPrimaryKeySelective(ProductImage record);

    int updateByPrimaryKey(ProductImage record);
}