package com.qiyuan.web.controller;

import com.qiyuan.web.entity.God;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/god")
public class GodController {

    @PostMapping("/")
    public List<God> list() {
        return null;
    }

}

