package com.qiyuan.web.controller.front;

import com.qiyuan.web.service.BannerService;
import com.qiyuan.web.vo.BannerVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banner")
public class BannerFrontController {

    @Autowired
    private BannerService bannerService;

    @Operation(summary = "get available banners by type")
    @PostMapping(value = "/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BannerVO> getAvailableBannerByType(@PathVariable("type") @Pattern(regexp = "^[a-zA-Z0-9]$", message = "type 必須為一個英數字元") String type) {
        return bannerService.getAvailableBannerByType(type);
    }
}
