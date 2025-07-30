package com.qiyuan.web.controller.front;

import com.qiyuan.web.dto.request.CountRequest;
import com.qiyuan.web.dto.response.PoeRankVO;
import com.qiyuan.web.entity.PoeRank;
import com.qiyuan.web.entity.PoeThrow;
import com.qiyuan.web.security.RoleExpressions;
import com.qiyuan.web.service.PoeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/poe")
@Tag(name = "擲筊管理", description = "提供擲筊排名、記錄等相關操作")
public class PoeController {

    private final PoeService poeService;

    public PoeController(PoeService poeService) {
        this.poeService = poeService;
    }

    @Operation(summary = "取得擲筊排名", description = "根據輸入的數量(count)，取得前N名的擲筊記錄")
    @PostMapping("/rank")
    public List<PoeRankVO> getPoeRank(@RequestBody CountRequest req) {
        return poeService.getPoeRank(req.getCount());
    }

    @Operation(summary = "查詢使用者擲聖筊次數",description = "取得目前登入使用者的擲聖筊總次數")
    @GetMapping("/siunn")
    @PreAuthorize(RoleExpressions.ONLY_USER)
    public PoeRank getSiunnPoe() {
        return poeService.getSiunnPoe();
    }

    @Operation(summary = "新增擲聖筊紀錄", description = "使用者進行擲聖筊行為，將資料新增至紀錄")
    @PostMapping("/siunn")
    @PreAuthorize(RoleExpressions.ONLY_USER)
    public boolean addSiunnPoe(@RequestBody @Validated CountRequest req) {
        return poeService.addSiunnPoe(req.getCount());
    }

    @Operation(summary = "新增一般擲筊紀錄", description = "使用者進行一般擲筊行為，將次數紀錄至資料庫中（累加）。需登入後才能操作。")
    @PostMapping("/throws/normal")
    @PreAuthorize(RoleExpressions.ONLY_USER)
    public boolean addPoeThrowTimes(@RequestBody @Validated CountRequest req) {
        return poeService.addNormalPoe(req.getCount());
    }

    @Operation(summary = "查詢使用者擲筊次數", description = "取得目前使用者一般用途的擲筊次數")
    @GetMapping("/throws/normal")
    @PreAuthorize(RoleExpressions.ONLY_USER)
    public PoeThrow getNormalPoe() {
        return poeService.getNormalPoe();
    }





}
