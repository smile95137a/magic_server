package com.qiyuan.web.controller.front;

import com.qiyuan.web.request.MasterServiceRequest;
import com.qiyuan.web.service.MasterRequestService;
import com.qiyuan.web.service.MasterService;
import com.qiyuan.web.vo.MasterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/master")
public class MasterFrontController {
    @Autowired
    private MasterService masterService;

    @Autowired
    private MasterRequestService masterRequestService;

    @PostMapping("/list")
    public List<MasterVO> getMasterList() {
        return masterService.getMasterList();
    }

    @PostMapping("/reservation")
    public boolean reservation(@Validated @RequestBody MasterServiceRequest req) {
        return masterRequestService.addMasterRequest(req);
    }

}
