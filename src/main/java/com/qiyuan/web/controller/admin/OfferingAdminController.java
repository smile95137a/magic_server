package com.qiyuan.web.controller.admin;


import com.qiyuan.web.dto.response.OfferingVO;
import com.qiyuan.web.security.RoleExpressions;
import com.qiyuan.web.service.OfferingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/offering")
@Validated
@PreAuthorize(RoleExpressions.ONLY_ADMIN)
@Tag(name = "供品管理", description = "後台供品管理操作")
public class OfferingAdminController {

    private final OfferingService offeringService;

    public OfferingAdminController(OfferingService offeringService) {
        this.offeringService = offeringService;
    }

    @PostMapping("/list")
    @Operation(summary = "查詢供品清單", description = "取得所有供品的詳細清單")
    public List<OfferingVO> getOfferingList() {
        return offeringService.getOfferingVo();
    }

    @PostMapping("/add")
    @Operation(summary = "新增供品", description = "新增一筆供品資料")
    public boolean addOffering(@RequestBody OfferingVO r) {
        return offeringService.addOffering(r);
    }

    @PostMapping("/modify")
    @Operation(summary = "修改供品", description = "修改已存在的供品資料")
    public boolean modifyOffering(@RequestBody OfferingVO r) {
        return offeringService.modifyOffering(r);
    }

//    @PostMapping("/delete/{id}")
//    @Operation(summary = "刪除供品", description = "依照ID刪除指定供品")
//    public boolean deleteById(
//            @Parameter(description = "供品ID，32位十六進位字串", example = "1a2b3c4d5e6f7g8h9i0j1k2l3m4n5o6p")
//            @PathVariable @Pattern(regexp = "^[a-fA-F0-9]{32}$", message = "ID格式錯誤") String id) {
//        return offeringService.deleteById(id);
//    }
}
