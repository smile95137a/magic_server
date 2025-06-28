package com.qiyuan.web.controller.front;

import com.qiyuan.web.dto.request.ProductListRequest;
import com.qiyuan.web.dto.response.ProductCategoryVO;
import com.qiyuan.web.dto.response.ProductVO;
import com.qiyuan.web.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/product")
@Tag(name = "前台商品相關", description = "前台商品相關 API")
public class ProductFrontController {

    private final ProductService productService;

    public ProductFrontController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "取得可用商品分類列表", description = "回傳所有可用商品分類（如ALL、各大分類）")
    @PostMapping("/category/list")
    public List<ProductCategoryVO> getCategoryAvailableList() {
        return productService.getCategoryAvailableList();
    }

    @Operation(summary = "依分類查詢商品列表", description = "根據查詢條件取得商品列表")
    @PostMapping("/list")
    public List<ProductVO> getProductByCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "商品查詢條件", required = false)
            @RequestBody ProductListRequest req) {
        return productService.getAvailableProductByCategory(req);
    }

    @Operation(summary = "查詢單一商品資訊", description = "根據商品ID查詢商品詳細資料")
    @PostMapping("/info/{productId}")
    public void getProductInfo(
            @Parameter(description = "商品ID", example = "123")
            @PathVariable Integer productId) {

    }
}