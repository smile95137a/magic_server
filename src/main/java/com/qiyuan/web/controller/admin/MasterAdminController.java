package com.qiyuan.web.controller.admin;

import com.qiyuan.web.dto.request.MasterRequest;
import com.qiyuan.web.dto.response.MasterAdminVO;
import com.qiyuan.web.security.RoleExpressions;
import com.qiyuan.web.service.MasterService;
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
@RequestMapping("/admin/master")
@PreAuthorize(RoleExpressions.ONLY_ADMIN)
@Tag(name = "老師管理", description = "後台老師管理操作")
public class MasterAdminController {

    private final MasterService masterService;

    public MasterAdminController(MasterService masterService) {
        this.masterService = masterService;
    }

    @PostMapping("/list")
    @Operation(summary = "查詢老師清單", description = "取得所有老師的詳細清單")
    public List<MasterAdminVO> getAllMaster() {
        return masterService.getAllMasterList();
    }

    @PostMapping("/add")
    @Operation(summary = "新增老師", description = "新增一位老師資料")
    public boolean addMaster(@Validated @RequestBody MasterRequest req) {
        return masterService.addMaster(req);
    }

    @PostMapping("/modify")
    @Operation(summary = "修改老師資料", description = "修改已存在的老師資料")
    public boolean modifyMaster(@RequestBody MasterRequest req) {
        return masterService.modifyMaster(req);
    }
}
