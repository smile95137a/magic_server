package com.qiyuan.web.controller.front;

import com.qiyuan.web.entity.God;
import com.qiyuan.web.service.GodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/god")
public class GodFrontController {

    @Autowired
    private GodService godService;

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<God> getAllGod() {
        return godService.getGodList();
    }

}
