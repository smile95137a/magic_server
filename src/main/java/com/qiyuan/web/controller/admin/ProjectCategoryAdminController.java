package com.qiyuan.web.controller.admin;

import com.qiyuan.web.dto.request.CategoryAdminRequest;
import com.qiyuan.web.dto.request.ModifyCategoryAdminRequest;
import com.qiyuan.web.service.ProductService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/product/category")
//@PreAuthorize(RoleExpressions.ONLY_ADMIN)
public class ProjectCategoryAdminController {

    private final ProductService productService;

    public ProjectCategoryAdminController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public boolean addCategory(@RequestBody @Validated CategoryAdminRequest req) {
        return productService.addCategory(req);
    }

    @PostMapping("/modify")
    public boolean modifyCategory(@RequestBody @Validated ModifyCategoryAdminRequest req) {
        return productService.modifyCategory(req);
    }

}
