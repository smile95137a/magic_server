package com.qiyuan.web.controller.front;

import com.qiyuan.web.entity.Poem;
import com.qiyuan.web.service.PoemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/poem")
@Tag(name = "前台詩籤", description = "前台詩籤抽取")
public class PoemFrontController {

    private final PoemService poemService;

    public PoemFrontController(PoemService poemService) {
        this.poemService = poemService;
    }

    @Operation(summary = "隨機抽取詩籤", description = "取得隨機一首詩籤")
    @PostMapping("/drawing")
    public Poem getRandomPoem() {
        return poemService.getRandomPoem();
    }

}
