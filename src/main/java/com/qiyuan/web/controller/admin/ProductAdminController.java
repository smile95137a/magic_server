package com.qiyuan.web.controller.admin;

import com.qiyuan.web.dto.request.DeleteImageRequest;
import com.qiyuan.web.dto.request.DiscardProductRequest;
import com.qiyuan.web.dto.request.EditProductRequest;
import com.qiyuan.web.dto.ProductAdminVO;
import com.qiyuan.web.dto.request.UploadImageRequest;
import com.qiyuan.web.dto.response.CreateProductDraftResponse;
import com.qiyuan.web.dto.response.UploadImageResponse;
import com.qiyuan.web.security.RoleExpressions;
import com.qiyuan.web.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product")
@PreAuthorize(RoleExpressions.ONLY_ADMIN)
@Tag(name = "商品管理", description = "後台商品(Product)管理操作")
public class ProductAdminController {

    private final Logger logger = LoggerFactory.getLogger(ProductAdminController.class);

    private final ProductService productService;

    public ProductAdminController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/draft")
    @Operation(summary = "建立商品草稿", description = "獲取商品ID")
    public CreateProductDraftResponse createDraft() {
        return productService.createProductDraft();
    }

    @PostMapping("/upload-image")
    @Operation(summary = "上傳商品圖片", description = "上傳商品圖片並回傳圖片路徑")
    public UploadImageResponse uploadImage(@ModelAttribute UploadImageRequest req) {
        return productService.uploadImage(req);
    }

    @PostMapping("/edit")
    @Operation(summary = "編輯商品", description = "更新指定商品資料")
    public boolean editProduct(@RequestBody @Validated EditProductRequest req) {
        return productService.editProduct(req);
    }

    @PostMapping("/discard")
    @Operation(summary = "丟棄商品草稿", description = "刪除/丟棄指定商品草稿")
    public boolean discard(@RequestBody DiscardProductRequest req) {
        try {
            return productService.discardProduct(req);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{productId}")
    @Operation(summary = "查詢商品詳細資料", description = "根據商品ID查詢商品的詳細資料")
    public ProductAdminVO getProduct(
            @Parameter(description = "商品ID", example = "1005") @PathVariable Integer productId) {
        return productService.getProduct(productId);
    }

    @PostMapping("/delete-image")
    @Operation(summary = "刪除商品圖片", description = "刪除商品關聯的圖片檔案")
    public boolean deleteImage(@RequestBody DeleteImageRequest req) {
        return productService.deleteImage(req);
    }
}