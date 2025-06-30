package com.qiyuan.web.controller.admin;

import com.qiyuan.web.dao.OfferingPurchaseMapper;
import com.qiyuan.web.dto.request.MasterReservationFilter;
import com.qiyuan.web.dto.request.RecordPeriodRequest;
import com.qiyuan.web.dto.response.MasterServiceRequestVO;
import com.qiyuan.web.dto.response.RecordVO;
import com.qiyuan.web.entity.OfferingRecord;
import com.qiyuan.web.enums.RecordItem;
import com.qiyuan.web.security.RoleExpressions;
import com.qiyuan.web.service.LanternPurchaseService;
import com.qiyuan.web.service.MasterRequestService;
import com.qiyuan.web.util.DateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/report")
@PreAuthorize(RoleExpressions.ONLY_ADMIN)
@Tag(name = "報表", description = "提供後台報表查詢")
public class ReportController {
    private final LanternPurchaseService lanternPurchaseService;
    private final OfferingPurchaseMapper offeringPurchaseMapper;
    private final MasterRequestService masterRequestService;

    public ReportController(LanternPurchaseService lanternPurchaseService, OfferingPurchaseMapper offeringPurchaseMapper, MasterRequestService masterRequestService) {
        this.lanternPurchaseService = lanternPurchaseService;
        this.offeringPurchaseMapper = offeringPurchaseMapper;
        this.masterRequestService = masterRequestService;
    }

    @PostMapping("/lantern")
    @Operation(summary = "查詢點燈購買紀錄報表", description = "根據日期區間，回傳點燈購買的報表資料")
    public List<RecordVO> getLanternPurchaseReport(@Validated @RequestBody RecordPeriodRequest req) {
        return lanternPurchaseService.getLanternPurchaseList(req);
    }

    @PostMapping("/offering")
    @Operation(summary = "查詢供品購買紀錄報表", description = "根據日期區間，回傳供品購買的報表資料")
    public List<RecordVO>  getOfferingPurchaseReport(@Validated @RequestBody RecordPeriodRequest req) {
        Date startTime = req.getStartTime();
        Date endTime = DateUtil.getEndOfDate(req.getEndTime());

        List<OfferingRecord> offeringRecords = offeringPurchaseMapper.selectRecordsByPeriod(startTime, endTime, 100);
        return offeringRecords
                .stream()
                .map(o -> RecordVO.builder()
                        .date(DateFormatUtils.format(o.getCreateTime(), "yyyy/MM/dd HH:mm"))
                        .item(RecordItem.Offering.getLabel())
                        .content(o.getOfferingName())
                        .build()
                ).collect(Collectors.toList());
    }

    @PostMapping("/master-reservation")
    @Operation(summary = "查詢老師預約紀錄報表", description = "根據過濾條件查詢老師服務預約報表")
    public List<MasterServiceRequestVO> getMasterReservationReport(MasterReservationFilter filter) {
        return masterRequestService.getMasterReservationByFilter(filter);
    }

    // 如果之後有 mall 報表，可直接複製 pattern
    public void getMallPurchaseReport() { }
}
