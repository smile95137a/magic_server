package com.qiyuan.web.controller.admin;

import com.qiyuan.web.dto.request.BannerRequest;
import com.qiyuan.web.entity.Banner;
import com.qiyuan.web.service.BannerService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/banner")
public class BannerAdminController {

    @Autowired
    private BannerService bannerService;

    @PostMapping("/{type}")
    public List<Banner> getBannerByType(@PathVariable("type")
                                        @Pattern(regexp = "^[a-zA-Z0-9]$", message = "type 必須為一個英數字元")  String type) {
        return bannerService.getAllBannerByType(type);
    }

    @PostMapping("/add")
    public boolean addBanner(@RequestBody BannerRequest banner) {
        return bannerService.addNewBanner(banner);
    }

    @PostMapping("/edit")
    public boolean modifyBanner(@RequestBody Banner banner) {
        //TODO:
        return true;
    }
}
