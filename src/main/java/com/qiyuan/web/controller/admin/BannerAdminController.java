package com.qiyuan.web.controller.admin;

import com.qiyuan.web.dto.request.ModifyBannerRequest;
import com.qiyuan.web.dto.request.NewBannerRequest;
import com.qiyuan.web.dto.response.BannerAdminVO;
import com.qiyuan.web.service.BannerService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/banner")
//@PreAuthorize(RoleExpressions.ONLY_ADMIN)
public class BannerAdminController {

    @Autowired
    private BannerService bannerService;

    @PostMapping("/{type}")
    public List<BannerAdminVO> getBannerByType(@PathVariable("type")
                                        @Pattern(regexp = "^[a-zA-Z0-9]$", message = "type 必須為一個英數字元")  String type) {
        return bannerService.getAllBannerByType(type);
    }

    @PostMapping("/add")
    public boolean addBanner(@Validated @RequestBody NewBannerRequest banner) {
        return bannerService.addNewBanner(banner);
    }

    @PostMapping("/modify")
    public boolean modifyBanner(@Validated @RequestBody ModifyBannerRequest banner) {
        return bannerService.modifyBanner(banner);
    }
}
