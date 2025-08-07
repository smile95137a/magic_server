package com.qiyuan.web.controller.admin;

import com.qiyuan.web.dto.request.ShippingMethodUpdateRequest;
import com.qiyuan.web.dto.response.ShippingMethodVO;
import com.qiyuan.web.entity.ShippingMethod;
import com.qiyuan.web.service.ShippingMethodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping-method")
@Tag(name = "物流方式管理")
@RequiredArgsConstructor
public class ShippingMethodController {
    private final ShippingMethodService shippingMethodService;

    @Operation(summary = "查詢物流方式列表", description = "取得目前所有可用的物流方式")
    @GetMapping("/list")
    public List<ShippingMethod> listShippingMethods() {
        return shippingMethodService.list();
    }

    @Operation(summary = "編輯物流方式", description = "更新物流方式資訊")
    @PutMapping("/modify")
    public boolean updateShippingMethod(@RequestBody @Validated ShippingMethodUpdateRequest req) {
        return shippingMethodService.update(req);
    }
}
