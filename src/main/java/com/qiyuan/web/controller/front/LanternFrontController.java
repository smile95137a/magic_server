package com.qiyuan.web.controller.front;

import com.qiyuan.web.entity.Lantern;
import com.qiyuan.web.dto.request.CountRequest;
import com.qiyuan.web.dto.request.LanternPurchaseRequest;
import com.qiyuan.web.service.LanternPurchaseService;
import com.qiyuan.web.service.LanternService;
import com.qiyuan.web.dto.response.LanternBlessingVO;
import com.qiyuan.web.dto.response.LanternVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Lantern", description = "點燈相關 API")
@RestController
@RequestMapping("/lantern")
public class LanternFrontController {

    @Autowired
    private LanternService lanternService;

    @Autowired
    private LanternPurchaseService lanternPurchaseService;

    @Operation(summary = "取得點燈清單", description = "回傳所有點燈清單")
    @ApiResponse(responseCode = "200", description = "成功取得點燈清單")
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Lantern> getList() {
        return lanternService.getLanternList();
    }

    @Operation(summary = "取得最新點燈名單", description = "取得最近新增的點燈資料")
    @ApiResponse(responseCode = "200", description = "成功取得名單")
    @PostMapping("/latest")
    public List<LanternBlessingVO> getLatestLanterns(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "欲取得的筆數")
            @RequestBody @Validated CountRequest req) {
        return lanternPurchaseService.getLatestLanternBlessing(req.getCount());
    }

    @Operation(summary = "推薦點燈名單", description = "取得推薦的點燈名單")
    @ApiResponse(responseCode = "200", description = "成功取得名單")
    @PostMapping("/recommendation")
    public List<LanternBlessingVO> getRecommendationLanterns(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "欲取得的筆數")
            @RequestBody @Validated CountRequest req) {
        return lanternPurchaseService.getRecommendation(req.getCount());
    }

    @Operation(summary = "點燈最多祝福排行", description = "取得點燈祝福次數排行名單")
    @ApiResponse(responseCode = "200", description = "成功取得排行")
    @PostMapping("/rank")
    public List<LanternBlessingVO> getRankLanterns(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "欲取得的筆數")
            @RequestBody @Validated CountRequest req) {
        return lanternPurchaseService.getRankLanternBlessing(req.getCount());
    }

    @Operation(summary = "查詢指定 code 點燈", description = "根據 code 查詢點燈資訊")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功取得點燈資訊"),
            @ApiResponse(responseCode = "400", description = "code 格式錯誤")
    })
    @PostMapping("/{code}")
    public LanternVO getLanternByCode(
            @Parameter(description = "點燈 code（格式: lan-開頭英文字母）", example = "lan-abc")
            @PathVariable
            @Pattern(regexp = "^lan-[A-Za-z]+$", message = "code 格式錯誤")
                    String code) {
        return lanternService.getLanternInfo(code);
    }

    @Operation(summary = "購買點燈", description = "購買點燈")
    @ApiResponse(responseCode = "200", description = "成功新增購買紀錄")
    @PostMapping("/purchase/info")
    public boolean purchaseLantern(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "購買資料")
            @Validated @RequestBody LanternPurchaseRequest req) {
        return lanternPurchaseService.addLanternPurchaseRecord(req);
    }

}