package com.qiyuan.web.controller.admin;

import com.qiyuan.web.entity.Banner;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/banner")
public class BannerAdminController {

    @Operation(summary = "Get All Banner By Type")
    @PostMapping("/{type}")
    public List<Banner> getBannerByType(@PathVariable("type") String type) {
        return null;
    }

    @Operation(summary = "new banner")
    @PostMapping("/add")
    public boolean addBanner(@RequestBody Banner banner) {
        return true;
    }

    @Operation(summary = "edit banner")
    @PostMapping("/edit")
    public boolean modifyBanner(@RequestBody Banner banner) {
        return true;
    }
}
