package com.qiyuan.web.controller.admin;

import com.qiyuan.web.dao.OfferingPurchaseMapper;
import com.qiyuan.web.dto.request.MasterReservationFilter;
import com.qiyuan.web.dto.request.RecordPeriodRequest;
import com.qiyuan.web.dto.response.MasterServiceRequestVO;
import com.qiyuan.web.dto.response.RecordVO;
import com.qiyuan.web.entity.OfferingRecord;
import com.qiyuan.web.enums.RecordItem;
import com.qiyuan.web.service.LanternPurchaseService;
import com.qiyuan.web.service.MasterRequestService;
import com.qiyuan.web.util.DateUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
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
    public List<RecordVO> getLanternPurchaseReport(@Validated @RequestBody RecordPeriodRequest req) {
        return lanternPurchaseService.getLanternPurchaseList(req);
    }

    @PostMapping("/offering")
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
    public List<MasterServiceRequestVO> getMasterReservationReport(MasterReservationFilter filter) {
        return masterRequestService.getMasterReservationByFilter(filter);
    }

    public void getMallPurchaseReport() {

    }
}
