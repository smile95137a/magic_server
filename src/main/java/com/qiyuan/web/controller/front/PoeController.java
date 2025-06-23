package com.qiyuan.web.controller.front;

import com.qiyuan.web.dto.request.CountRequest;
import com.qiyuan.web.dto.response.PoeRankVO;
import com.qiyuan.web.service.PoeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/poe")
public class PoeController {

    private final PoeService poeService;

    public PoeController(PoeService poeService) {
        this.poeService = poeService;
    }

    @PostMapping("/rank")
    public List<PoeRankVO> getPoeRank(@RequestBody @Validated CountRequest req) {
        return poeService.getPoeRank(req.getCount());
    }

    @PostMapping("/siunn")
    public boolean addSiunnPoe(@RequestBody @Validated CountRequest req) {
        return poeService.addSiunnPoe(req.getCount());
    }
}
