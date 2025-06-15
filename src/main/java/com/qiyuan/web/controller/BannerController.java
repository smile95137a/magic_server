package com.qiyuan.web.controller;

import com.qiyuan.web.entity.Banner;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banner")
public class BannerController {


    @Operation(summary = "Get All Banner By Type")
    @PostMapping("/manage/{type}")
    public List<Banner> getBannerByType(@PathVariable("type") String type) {
        return null;
    }

    @Operation(summary = "get available banners by type")
    @PostMapping("/{type}")
    public List<Banner> getAvailableBannerByType(@PathVariable("type")String type) {
        return null;
    }

    @Operation(summary = "new banner")
    @PostMapping("/manage/add")
    public boolean addBanner(@RequestBody Banner banner) {
        return true;
    }

    @Operation(summary = "edit banner")
    @PostMapping("/manage/edit")
    public boolean modifyBanner(@RequestBody Banner banner) {
        return true;
    }
}
