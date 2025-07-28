package com.qiyuan.web.dao;

import com.qiyuan.web.dto.LanternAdminRecord;
import com.qiyuan.web.dto.LanternBlessingDTO;
import com.qiyuan.web.entity.LanternPurchase;
import com.qiyuan.web.entity.LanternRecord;
import com.qiyuan.web.entity.OfferingRecord;
import com.qiyuan.web.entity.example.LanternPurchaseExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LanternPurchaseMapper {
    long countByExample(LanternPurchaseExample example);

    int deleteByExample(LanternPurchaseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LanternPurchase record);

    int insertSelective(LanternPurchase record);

    List<LanternBlessingDTO> selectMyActiveLanterns(@Param("userId")String userId);

    List<LanternPurchase> selectByExample(LanternPurchaseExample example);
    List<LanternBlessingDTO> selectLimitByExample(@Param("example") LanternPurchaseExample example, @Param("limit") int limit);

    List<LanternBlessingDTO> selectDistinctLimitByExample(@Param("example") LanternPurchaseExample example, @Param("limit") int limit);

    LanternPurchase selectByPrimaryKey(Integer id);

    List<LanternRecord> selectRecordsByUserIdAndPeriod(@Param("userId")String userId, @Param("startTime") Date startTime, @Param("endTime")Date endTime, @Param("limits") Integer limits);
    List<LanternRecord> selectRecordsByPeriod(@Param("startTime") Date startTime, @Param("endTime")Date endTime, @Param("limits") Integer limits);
    List<LanternAdminRecord> selectAdminRecordsByPeriod(@Param("startTime") Date startTime, @Param("endTime")Date endTime);

    int updateByExampleSelective(@Param("record") LanternPurchase record, @Param("example") LanternPurchaseExample example);

    int updateByExample(@Param("record") LanternPurchase record, @Param("example") LanternPurchaseExample example);

    int updateByPrimaryKeySelective(LanternPurchase record);

    int updateByPrimaryKey(LanternPurchase record);
}