package com.qiyuan.web.controller.admin;

import com.qiyuan.web.dto.request.QueryOrderAdminRequest;
import com.qiyuan.web.dto.request.ShippingMethodRequest;
import com.qiyuan.web.dto.request.UpdateOrderStatusBatchRequest;
import com.qiyuan.web.dto.response.OrderDetailVO;
import com.qiyuan.web.dto.response.OrderVO;
import com.qiyuan.web.dto.response.ShippingMethodVO;
import com.qiyuan.web.service.OrderAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/order")
@Tag(name = "後台訂單管理", description = "提供訂單查詢、物流設定、狀態更新等後台管理功能")
public class OrderAdminController {

    private final OrderAdminService orderAdminService;

    public OrderAdminController(OrderAdminService orderAdminService) {
        this.orderAdminService = orderAdminService;
    }

    @Operation(summary = "查詢訂單列表", description = "多條件查詢訂單，支援分頁")
    @PostMapping("/list")
    public List<OrderVO> getOrderList(@RequestBody @Validated QueryOrderAdminRequest request) {
        return orderAdminService.getOrderList(request);
    }

    @Operation(summary = "查詢訂單詳情", description = "根據訂單編號查詢完整訂單資訊")
    @GetMapping("/detail/{orderId}")
    public OrderDetailVO getOrderDetail(@PathVariable String orderId) {
        return orderAdminService.getOrderDetail(orderId);
    }

    @Operation(summary = "批次更新訂單狀態", description = "批次變更訂單狀態，如出貨、取消、完成等")
    @PostMapping("/status/batch-update")
    public boolean updateOrderStatusBatch(@RequestBody @Validated UpdateOrderStatusBatchRequest request) {
        orderAdminService.updateOrderStatusBatch(request);
        return true;
    }

    @Operation(summary = "變更物流內容", description = "更新物流方式資訊")
    @PostMapping("/shipping-method/save")
    public boolean saveShippingMethod(@RequestBody @Validated ShippingMethodRequest request) {
        orderAdminService.saveShippingMethod(request);
        return true;
    }

    @Operation(summary = "查詢物流方式列表", description = "取得目前所有可用的物流方式")
    @GetMapping("/shipping-method/list")
    public List<ShippingMethodVO> getShippingMethodList() {
        return orderAdminService.getShippingMethodList();
    }
}
