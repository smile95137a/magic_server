package com.qiyuan.web.controller.front;

import com.qiyuan.web.entity.Lantern;
import com.qiyuan.web.service.LanternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lantern")
public class LanternFrontController {

    @Autowired
    private LanternService lanternService;

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Lantern> getList() {
        return lanternService.getLanternList();
    }

}
