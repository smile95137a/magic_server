package com.qiyuan.web.controller;

import com.qiyuan.web.entity.MasterServiceRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ma-service")
public class MasterServiceController {

    @Operation(summary = "frontend: apply master")
    @PostMapping("/apply")
    public String apply() {
        return "";
    }

    @PostMapping("/list")
    public List<MasterServiceRequest> listRecords() {
        return null;
    }
}
