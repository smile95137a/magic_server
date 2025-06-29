package com.qiyuan.web.dao;

import com.qiyuan.web.entity.MasterServiceRequest;
import com.qiyuan.web.entity.example.MasterServiceRequestExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MasterServiceRequestMapper {
    long countByExample(MasterServiceRequestExample example);

    int deleteByExample(MasterServiceRequestExample example);

    int deleteByPrimaryKey(Integer serial);

    int insert(MasterServiceRequest record);

    int insertSelective(MasterServiceRequest record);

    List<MasterServiceRequest> selectByExample(MasterServiceRequestExample example);

    MasterServiceRequest selectByPrimaryKey(Integer serial);

    int updateByExampleSelective(@Param("record") MasterServiceRequest record, @Param("example") MasterServiceRequestExample example);

    int updateByExample(@Param("record") MasterServiceRequest record, @Param("example") MasterServiceRequestExample example);

    int updateByPrimaryKeySelective(MasterServiceRequest record);

    int updateByPrimaryKey(MasterServiceRequest record);
}