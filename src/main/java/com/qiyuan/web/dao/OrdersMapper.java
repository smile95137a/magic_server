package com.qiyuan.web.dao;

import com.qiyuan.web.dto.request.QueryOrderAdminRequest;
import com.qiyuan.web.dto.response.OrderQueryResultVO;
import com.qiyuan.web.dto.response.OrderVO;
import com.qiyuan.web.entity.Orders;
import com.qiyuan.web.entity.example.OrdersExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdersMapper {
    long countByExample(OrdersExample example);

    int deleteByExample(OrdersExample example);

    int deleteByPrimaryKey(String id);

    int insert(Orders record);

    int insertSelective(Orders record);

    List<Orders> selectByExample(OrdersExample example);

    List<Orders> selectByExampleWithPage(@Param("example") OrdersExample example,
                                         @Param("offset") int offset,
                                         @Param("size") int size);

    List<OrderQueryResultVO> listOrderDetailsAdmin(QueryOrderAdminRequest request);


    void updateStatusToPaid(String orderId);

    Orders selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Orders record, @Param("example") OrdersExample example);

    int updateByExample(@Param("record") Orders record, @Param("example") OrdersExample example);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);
    
    Orders selectByExternalOrderNo(@Param("externalOrderNo") String externalOrderNo);

}