package com.qiyuan.web.dao;


import com.qiyuan.web.entity.PaymentTransaction;
import com.qiyuan.web.entity.example.PaymentTransactionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaymentTransactionMapper {
    long countByExample(PaymentTransactionExample example);

    int deleteByExample(PaymentTransactionExample example);

    int deleteByPrimaryKey(String id);

    int insert(PaymentTransaction row);

    int insertSelective(PaymentTransaction row);

    PaymentTransaction selectByExternalOrderNo(String externalOrderNo);

    List<PaymentTransaction> selectByExample(PaymentTransactionExample example);

    PaymentTransaction selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") PaymentTransaction row, @Param("example") PaymentTransactionExample example);

    int updateByExample(@Param("row") PaymentTransaction row, @Param("example") PaymentTransactionExample example);

    int updateByPrimaryKeySelective(PaymentTransaction row);

    int updateByPrimaryKey(PaymentTransaction row);
}