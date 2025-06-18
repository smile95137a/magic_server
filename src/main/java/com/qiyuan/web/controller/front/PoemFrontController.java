package com.qiyuan.web.controller.front;

import com.qiyuan.web.entity.Poem;
import com.qiyuan.web.service.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/poem")
public class PoemFrontController {

    @Autowired
    private PoemService poemService;

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Poem> getPoemList() {
        return poemService.getPoemList();
    }


    @PostMapping(value = "/init", produces = MediaType.APPLICATION_JSON_VALUE)
    public void init() {
        poemService.init();
    }

}
