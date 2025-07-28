package com.qiyuan.web.dao;


import com.qiyuan.web.dto.MoneyReportVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ReportMapper {
    List<MoneyReportVO> getAllReportByPeriod(@Param("startTime")Date startTime, @Param("endTime")Date endTime);
}

