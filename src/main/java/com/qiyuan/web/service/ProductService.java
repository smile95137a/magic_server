package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.ProductCategoryMapper;
import com.qiyuan.web.dao.ProductMapper;
import com.qiyuan.web.dto.request.CategoryAdminRequest;
import com.qiyuan.web.dto.request.ModifyCategoryAdminRequest;
import com.qiyuan.web.entity.ProductCategory;
import com.qiyuan.web.entity.example.ProductCategoryExample;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.RandomGenerator;
import com.qiyuan.web.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductCategoryMapper productCategoryMapper;

    public ProductService(ProductMapper productMapper, ProductCategoryMapper productCategoryMapper) {
        this.productMapper = productMapper;
        this.productCategoryMapper = productCategoryMapper;
    }

    public boolean addCategory(CategoryAdminRequest req) {
        Date now = DateUtil.getCurrentDate();
        ProductCategory r = ProductCategory.builder()
                .id(RandomGenerator.getUUID())
                .description(StringUtils.defaultString(req.getDescription()))
                .name(req.getName())
                .status(true)
                .createTime(now)
                .updateTime(now)
                .build();
        return productCategoryMapper.insert(r) > 0;
    }

    public boolean modifyCategory(ModifyCategoryAdminRequest req) {
        ProductCategory existed = productCategoryMapper.selectByPrimaryKey(req.getId());
        if (existed == null) {
            throw new ApiException("新增失敗，查無資料");
        }
        existed.setDescription(req.getDescription());
        existed.setName(req.getName());
        existed.setStatus(req.getStatus());
        return productCategoryMapper.updateByPrimaryKey(existed) > 0;
    }
}
