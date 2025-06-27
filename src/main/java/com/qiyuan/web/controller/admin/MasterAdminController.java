package com.qiyuan.web.controller.admin;

import com.qiyuan.web.dto.request.MasterRequest;
import com.qiyuan.web.dto.response.MasterAdminVO;
import com.qiyuan.web.service.MasterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/master")
//@PreAuthorize(RoleExpressions.ONLY_ADMIN)
public class MasterAdminController {

    private final MasterService masterService;

    public MasterAdminController(MasterService masterService) {
        this.masterService = masterService;
    }

    @PostMapping("/list")
    public List<MasterAdminVO> getAllMaster() {
        return masterService.getAllMasterList();
    }

    @PostMapping("/add")
    public boolean addMaster(@Validated @RequestBody MasterRequest req) {
        return masterService.addMaster(req);
    }

    @PostMapping("/modify")
    public boolean modifyMaster(@RequestBody MasterRequest req) {
        return masterService.modifyMaster(req);

    }
}
