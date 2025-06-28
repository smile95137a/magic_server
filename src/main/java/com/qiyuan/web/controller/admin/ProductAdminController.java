package com.qiyuan.web.controller.admin;

import com.qiyuan.web.dto.request.ProductSearchRequest;
import com.qiyuan.web.dto.ProductInfo;
import com.qiyuan.web.service.ProductService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/product")
public class ProductAdminController {

    private final ProductService productService;

    public ProductAdminController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/list")
    public List<ProductInfo> getProductList(@RequestBody ProductSearchRequest req) {
        return productService.getProduct(req);
    }

    @PostMapping("/add")
    public boolean addProduct(@RequestBody @Validated ProductInfo p) {
        return productService.addProduct(p);
    }

    @PostMapping("/modify")
    public void modifyProductInfo() {

    }
}
