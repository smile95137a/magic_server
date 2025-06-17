package com.qiyuan.web.controller.admin;

import com.qiyuan.web.entity.Banner;
import com.qiyuan.web.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/banner")
public class BannerAdminController {

    @Autowired
    private BannerService bannerService;

    @Operation(summary = "Get All Banner By Type")
    @PostMapping("/{type}")
    public List<Banner> getBannerByType(@PathVariable("type")  @Pattern(regexp = "^[a-zA-Z0-9]$", message = "type 必須為一個英數字元")  String type) {
        return bannerService.getAllBannerByType(type);
    }

    @Operation(summary = "new banner")
    @PostMapping("/add")
    public boolean addBanner(@RequestBody Banner banner) {
        //TODO:
        return true;
    }

    @Operation(summary = "edit banner")
    @PostMapping("/edit")
    public boolean modifyBanner(@RequestBody Banner banner) {
        //TODO:
        return true;
    }
}
