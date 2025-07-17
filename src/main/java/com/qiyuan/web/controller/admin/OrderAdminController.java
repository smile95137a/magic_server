package com.qiyuan.web.controller.admin;

import com.qiyuan.web.dto.request.QueryOrderAdminRequest;
import com.qiyuan.web.dto.request.ShippingMethodRequest;
import com.qiyuan.web.dto.request.UpdateOrderStatusBatchRequest;
import com.qiyuan.web.dto.response.*;
import com.qiyuan.web.enums.OrderStatus;
import com.qiyuan.web.service.OrderAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/order")
@Tag(name = "後台訂單管理", description = "提供訂單查詢、物流設定、狀態更新等後台管理功能")
public class OrderAdminController {

    private final OrderAdminService orderAdminService;

    public OrderAdminController(OrderAdminService orderAdminService) {
        this.orderAdminService = orderAdminService;
    }

    @Operation(summary = "查詢訂單列表", description = "多條件查詢訂單")
    @PostMapping("/list")
    public List<OrderVO> getOrderList(@RequestBody QueryOrderAdminRequest request) {
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

    @Operation(
            summary = "查詢可更改的訂單狀態列表",
            description = "取得後台可用於訂單狀態異動的狀態列表（僅限: 訂單準備中、已出貨、已完成、已取消、已退款）"
    )
    @ApiResponse(responseCode = "200", description = "查詢成功，回傳可更改狀態")
    @GetMapping("/status/updatable-list")
    public List<OrderStatusVO> getUpdatableOrderStatusList() {
        return OrderStatus.BACKEND_SET.stream()
                .map(status -> new OrderStatusVO(status.name(), status.getLabel()))
                .collect(Collectors.toList());
    }

    @Operation(summary = "取得訂單出貨單資訊", description = "回傳指定訂單的出貨資訊、客戶資訊、訂單明細等")
    @GetMapping("/delivery-note/{orderId}")
    public DeliveryNoteVO getDeliveryNote(@PathVariable String orderId) {
        return orderAdminService.getDeliveryNote(orderId);
    }

}
