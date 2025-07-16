package com.qiyuan.web.controller.front;

import com.qiyuan.web.dto.request.CountRequest;
import com.qiyuan.web.dto.response.PoeRankVO;
import com.qiyuan.web.security.RoleExpressions;
import com.qiyuan.web.service.PoeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "新增擲筊紀錄", description = "使用者進行擲筊行為，將資料新增至紀錄")
    @PostMapping("/siunn")
    @PreAuthorize(RoleExpressions.ONLY_USER)
    public boolean addSiunnPoe(@RequestBody @Validated CountRequest req) {
        return poeService.addSiunnPoe(req.getCount());
    }

}
