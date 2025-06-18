package com.qiyuan.web.controller.front;

import com.qiyuan.web.entity.Lantern;
import com.qiyuan.web.request.CountRequest;
import com.qiyuan.web.service.LanternPurchaseService;
import com.qiyuan.web.service.LanternService;
import com.qiyuan.web.vo.LanternBlessingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lantern")
public class LanternFrontController {

    @Autowired
    private LanternService lanternService;

    @Autowired
    private LanternPurchaseService lanternPurchaseService;

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Lantern> getList() {
        return lanternService.getLanternList();
    }

    @PostMapping("/latest")
    public List<LanternBlessingVO> getLatestLanterns(@RequestBody @Validated CountRequest req) {
        return lanternPurchaseService.getLatestLanternBlessing(req.getCount());
    }

    @PostMapping("/recommendation")
    public List<LanternBlessingVO> getRecommendationLanterns(@RequestBody @Validated CountRequest req) {
        return lanternPurchaseService.getRecommendation(req.getCount());
    }

    @PostMapping("/rank")
    public List<LanternBlessingVO> getRankLanterns(@RequestBody @Validated CountRequest req) {
        return lanternPurchaseService.getRankLanternBlessing(req.getCount());
    }


}
