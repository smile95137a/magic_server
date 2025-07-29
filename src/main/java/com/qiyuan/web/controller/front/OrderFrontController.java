package com.qiyuan.web.controller.front;

import com.qiyuan.web.dto.request.CancelOrderRequest;
import com.qiyuan.web.dto.request.CreateOrderRequest;
import com.qiyuan.web.dto.request.PaySuccessRequest;
import com.qiyuan.web.dto.request.QueryOrderRequest;
import com.qiyuan.web.dto.response.*;
import com.qiyuan.web.enums.InvoiceType;
import com.qiyuan.web.enums.PayMethodEnum;
import com.qiyuan.web.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "前台訂單管理", description = "會員下單、查詢訂單、取消訂單等")
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderFrontController {

    private final OrderService orderService;

    @Operation(summary = "建立訂單（會員下單）", description = "會員提交購物車商品、物流、收件人資訊後建立新訂單")
    @ApiResponse(responseCode = "200", description = "建立成功，回傳訂單ID與金額")
    @PostMapping("/create")
    public CreateOrderResponse createOrder(@RequestBody @Validated CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @Operation(summary = "查詢自己的訂單列表", description = "查詢登入會員的所有訂單")
    @ApiResponse(responseCode = "200", description = "查詢成功，回傳訂單清單")
    @PostMapping("/list")
    public List<OrderVO> getOrderList(@RequestBody @Validated QueryOrderRequest request) {
        return orderService.getOrderList(request);
    }

    @Operation(summary = "查詢訂單詳情", description = "查詢指定訂單（只能查自己的）")
    @ApiResponse(responseCode = "200", description = "查詢成功，回傳訂單詳情")
    @GetMapping("/detail/{orderId}")
    public OrderDetailVO getOrderDetail(
            @Parameter(description = "訂單ID（UUID）", required = true)
            @PathVariable String orderId) {
        return orderService.getOrderDetail(orderId);
    }

    @Operation(summary = "取消訂單", description = "會員可取消未付款/未出貨的訂單")
    @ApiResponse(responseCode = "200", description = "取消成功")
    @PostMapping("/cancel")
    public boolean cancelOrder(@RequestBody @Validated CancelOrderRequest request) {
        orderService.cancelOrder(request);
        return true;
    }

    @Operation(summary = "信用卡付款完成", description = "通知後台該訂單已完成付款")
    @PostMapping("/pay-success")
    public boolean paySuccess(@RequestBody @Validated PaySuccessRequest request) {
        orderService.markAsPaid(request);
        return true;
    }

    @Operation(summary = "查詢物流方式列表", description = "取得前台可選物流方式清單")
    @ApiResponse(responseCode = "200", description = "查詢成功，回傳物流方式")
    @GetMapping("/shipping-method/list")
    public List<ShippingMethodVO> getShippingMethodList() {
        return orderService.getShippingMethodList();
    }

    @Operation(summary = "查詢發票類型列表", description = "取得可選發票類型清單")
    @ApiResponse(responseCode = "200", description = "查詢成功，回傳發票類型")
    @GetMapping("/invoice-type/list")
    public List<InvoiceTypeVO> getInvoiceTypeList() {
        return Arrays.stream(InvoiceType.values())
                .map(type -> new InvoiceTypeVO(type.getValue(), type.getLabel()))
                .collect(Collectors.toList());
    }

    @PostMapping("/pay-method/list")
    public List<PayMethodVO> getPayMethodList() {
        return PayMethodEnum.getAllowsMethod().stream()
                .map(pm -> new PayMethodVO(pm.getCode(), pm.getLabel()))
                .collect(Collectors.toList());
    }


}

