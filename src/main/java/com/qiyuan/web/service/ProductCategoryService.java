package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.ProductCategoryMapper;
import com.qiyuan.web.dto.request.CategoryAdminRequest;
import com.qiyuan.web.dto.request.ModifyCategoryAdminRequest;
import com.qiyuan.web.dto.response.ProductCategoryVO;
import com.qiyuan.web.entity.ProductCategory;
import com.qiyuan.web.entity.example.ProductCategoryExample;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.RandomGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {

    private final ProductCategoryMapper productCategoryMapper;

    public ProductCategoryService(ProductCategoryMapper productCategoryMapper) {
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
        return productCategoryMapper.updateByPrimaryKeySelective(existed) > 0;
    }

    public List<ProductCategoryVO> getCategoryAvailableList() {
        ProductCategoryExample e = new ProductCategoryExample();
        e.createCriteria().andStatusEqualTo(true);
        List<ProductCategory> list = productCategoryMapper.selectByExample(e);
        return list.stream().map(p -> ProductCategoryVO.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .description(p.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ProductCategory> getCategoryList() {
        ProductCategoryExample e = new ProductCategoryExample();
        return productCategoryMapper.selectByExample(e);
    }
}