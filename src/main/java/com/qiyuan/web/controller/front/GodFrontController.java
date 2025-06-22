package com.qiyuan.web.controller.front;

import com.qiyuan.web.entity.God;
import com.qiyuan.web.service.GodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/god")
@Tag(name = "前台神明", description = "前台神明")
public class GodFrontController {

    private final GodService godService;

    public GodFrontController(GodService godService) {
        this.godService = godService;
    }

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "取得全部神明列表",
            description = "回傳所有神明的清單"
    )
    public List<God> getAllGod() {
        return godService.getGodList();
    }

}
