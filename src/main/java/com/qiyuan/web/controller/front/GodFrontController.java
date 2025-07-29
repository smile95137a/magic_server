package com.qiyuan.web.controller.front;

import com.qiyuan.security.response.ApiResponse;
import com.qiyuan.web.dto.request.GodInfoRequest;
import com.qiyuan.web.dto.request.GodExtendPeriodRequest;
import com.qiyuan.web.dto.request.PresentOfferingRequest;
import com.qiyuan.web.dto.response.GodInfoVO;
import com.qiyuan.web.dto.response.OfferingVO;
import com.qiyuan.web.dto.response.PaymentNoVO;
import com.qiyuan.web.entity.God;
import com.qiyuan.web.security.RoleExpressions;
import com.qiyuan.web.service.GodService;
import com.qiyuan.web.service.OfferingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/god")
@Tag(name = "前台神明", description = "前台神明")
public class GodFrontController {

    private final GodService godService;
    private final OfferingService offeringService;

    public GodFrontController(GodService godService, OfferingService offeringService) {
        this.godService = godService;
        this.offeringService = offeringService;
    }

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "取得全部神明列表", description = "回傳所有神明的清單")
    public List<God> getAllGod() {
        return godService.getGodList();
    }

    @PostMapping("/info")
    @PreAuthorize(RoleExpressions.ONLY_USER)
    @Operation(summary = "查詢神明資訊", description = "取得指定神明的詳細資訊")
    public ApiResponse<GodInfoVO> getGodInfo(@RequestBody @Validated GodInfoRequest req) {
        String godCode = req.getGodCode().toLowerCase(Locale.ROOT);
        GodInfoVO info = godService.getGodInfo(godCode);
        if (info == null) {
            return ApiResponse.success("查詢成功", null);
        } else {
            return ApiResponse.success("查詢成功", info);
        }
    }

    @PostMapping("/descend")
    @PreAuthorize(RoleExpressions.ONLY_USER)
    @Operation(summary = "神明降臨", description = "讓指定神明降臨，需傳入神明代碼及降臨天數")
    public PaymentNoVO godDescend(@RequestBody @Validated GodExtendPeriodRequest req) {
        return godService.prepareGodDescendPurchase(req);
    }

    @PostMapping("/offering/list")
    @PreAuthorize(RoleExpressions.ONLY_USER)
    @Operation(summary = "取得供品列表", description = "回傳所有可用的供品資訊")
    public List<OfferingVO> getOfferingList() {
        return offeringService.getOfferingVo();
    }

    @PostMapping("/offering/present")
    @Operation(summary = "供奉供品", description = "提供供品給指定神明")
    @PreAuthorize(RoleExpressions.ONLY_USER)
    public PaymentNoVO presentOffering(@Validated @RequestBody PresentOfferingRequest req) {
        return godService.addOffering(req);
    }

}
