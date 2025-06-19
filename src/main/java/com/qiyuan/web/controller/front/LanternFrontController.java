package com.qiyuan.web.controller.front;

import com.qiyuan.web.entity.Lantern;
import com.qiyuan.web.entity.example.LanternExample;
import com.qiyuan.web.request.CountRequest;
import com.qiyuan.web.request.LanternPurchaseRequest;
import com.qiyuan.web.service.LanternPurchaseService;
import com.qiyuan.web.service.LanternService;
import com.qiyuan.web.vo.LanternBlessingVO;
import com.qiyuan.web.vo.LanternVO;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{code}")
    public LanternVO getLanternByCode(@PathVariable
                                      @Pattern(regexp = "^lan-[A-Za-z]+$", message = "code 格式錯誤")
                                      String code) {
        return lanternService.getLanternInfo(code);
    }

    @PostMapping("/purchase/info")
    public Boolean purchaseLantern(@Validated @RequestBody LanternPurchaseRequest req) {
        return lanternPurchaseService.addLanternPurchaseRecord(req);
    }



}
