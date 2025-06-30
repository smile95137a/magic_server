package com.qiyuan.web.controller.admin;

import com.qiyuan.web.dto.request.CategoryAdminRequest;
import com.qiyuan.web.dto.request.ModifyCategoryAdminRequest;
import com.qiyuan.web.entity.ProductCategory;
import com.qiyuan.web.security.RoleExpressions;
import com.qiyuan.web.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/product/category")
@Tag(name = "後台商品分類管理", description = "後台商品分類維護（新增、修改、查詢）")
@PreAuthorize(RoleExpressions.ONLY_ADMIN)
public class ProjectCategoryAdminController {

    private final ProductCategoryService productCategoryService;

    public ProjectCategoryAdminController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @Operation(summary = "新增商品分類", description = "後台新增一筆商品分類")
    @PostMapping("/add")
    public boolean addCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "新增分類內容")
            @RequestBody @Validated CategoryAdminRequest req) {
        return productCategoryService.addCategory(req);
    }

    @Operation(summary = "修改商品分類", description = "後台修改既有商品分類")
    @PostMapping("/modify")
    public boolean modifyCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "修改分類內容")
            @RequestBody @Validated ModifyCategoryAdminRequest req) {
        return productCategoryService.modifyCategory(req);
    }

    @Operation(summary = "查詢所有商品分類", description = "後台查詢所有商品分類清單")
    @PostMapping("/list")
    public List<ProductCategory> getCategoryList() {
        return productCategoryService.getCategoryList();
    }
}
