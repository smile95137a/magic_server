package com.qiyuan.web.controller.front;

import com.chl.security.response.ApiResponse;
import com.qiyuan.web.request.MasterServiceRequest;
import com.qiyuan.web.service.MasterRequestService;
import com.qiyuan.web.service.MasterService;
import com.qiyuan.web.vo.MasterVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/master")
@Tag(name = "前台老師", description = "老師清單與預約")
public class MasterFrontController {
    private final MasterService masterService;
    private final MasterRequestService masterRequestService;

    public MasterFrontController(MasterService masterService, MasterRequestService masterRequestService) {
        this.masterService = masterService;
        this.masterRequestService = masterRequestService;
    }

    @Operation(summary = "取得老師清單",  description = "回傳在職的老師資料")
    @PostMapping("/list")
    public List<MasterVO> getMasterList() {
        return masterService.getMasterList();
    }

    @Operation(summary = "預約老師服務", description = "送出預約資料，回傳預約是否成功")
    @PostMapping("/reservation")
    public ApiResponse<String> reservation(@Validated @RequestBody MasterServiceRequest req) {
        return ApiResponse.success("執行成功", masterRequestService.addMasterRequest(req)) ;
    }

}
