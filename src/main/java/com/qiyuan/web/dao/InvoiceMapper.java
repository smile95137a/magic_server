package com.qiyuan.web.dao;

import com.qiyuan.web.entity.Invoice;
import com.qiyuan.web.entity.example.InvoiceExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InvoiceMapper {
    long countByExample(InvoiceExample example);

    int deleteByExample(InvoiceExample example);

    int deleteByPrimaryKey(String id);

    int insert(Invoice row);

    int insertSelective(Invoice row);

    List<Invoice> selectByExample(InvoiceExample example);

    Invoice selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") Invoice row, @Param("example") InvoiceExample example);

    int updateByExample(@Param("row") Invoice row, @Param("example") InvoiceExample example);

    int updateByPrimaryKeySelective(Invoice row);

    int updateByPrimaryKey(Invoice row);
}