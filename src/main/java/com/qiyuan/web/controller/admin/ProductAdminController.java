package com.qiyuan.web.controller.admin;

import com.qiyuan.web.dto.request.DeleteImageRequest;
import com.qiyuan.web.dto.request.DiscardProductRequest;
import com.qiyuan.web.dto.request.EditProductRequest;
import com.qiyuan.web.dto.ProductAdminVO;
import com.qiyuan.web.dto.request.UploadImageRequest;
import com.qiyuan.web.dto.response.CreateProductDraftResponse;
import com.qiyuan.web.dto.response.UploadImageResponse;
import com.qiyuan.web.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product")
public class ProductAdminController {

    private final Logger logger = LoggerFactory.getLogger(ProductAdminController.class);

    private final ProductService productService;

    public ProductAdminController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/draft")
    public CreateProductDraftResponse createDraft() {
        return productService.createProductDraft();
    }

    @PostMapping("/upload-image")
    public UploadImageResponse uploadImage(@ModelAttribute UploadImageRequest req) {
        return productService.uploadImage(req);
    }

    @PostMapping("/edit")
    public boolean editProduct(@RequestBody @Validated EditProductRequest req) {
        return productService.editProduct(req);
    }

    @PostMapping("/discard")
    public boolean discard(@RequestBody DiscardProductRequest req) {
        try {
            return productService.discardProduct(req);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{productId}")
    public ProductAdminVO getProduct(@PathVariable Integer productId) {
        return productService.getProduct(productId);
    }

    @PostMapping("/delete-image")
    public boolean deleteImage(@RequestBody DeleteImageRequest req) {
        return productService.deleteImage(req);
    }
}
