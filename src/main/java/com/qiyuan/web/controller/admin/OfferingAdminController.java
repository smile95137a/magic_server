package com.qiyuan.web.controller.admin;


import com.qiyuan.web.dto.response.OfferingVO;
import com.qiyuan.web.service.OfferingService;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offering")
@Validated
public class OfferingAdminController {

    private final OfferingService offeringService;

    public OfferingAdminController(OfferingService offeringService) {
        this.offeringService = offeringService;
    }

    @PostMapping("/list")
    public List<OfferingVO> getOfferingList() {
        return offeringService.getOfferingVo();
    }

    @PostMapping("/add")
    public boolean addOffering(@RequestBody OfferingVO r) {
        return offeringService.addOffering(r);
    }

    @PostMapping("/modify")
    public boolean modifyOffering(@RequestBody OfferingVO r) {
        return offeringService.modifyOffering(r);
    }

    @PostMapping("/delete/{id}")
    public boolean deleteById(@PathVariable @Pattern(regexp = "^[a-fA-F0-9]{32}$", message = "ID格式錯誤") String id) {
        return offeringService.deleteById(id);
    }

}
