package com.qiyuan.web.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.ProductCategoryMapper;
import com.qiyuan.web.dto.request.CategoryAdminRequest;
import com.qiyuan.web.dto.request.ModifyCategoryAdminRequest;
import com.qiyuan.web.dto.response.ProductCategoryVO;
import com.qiyuan.web.entity.ProductCategory;
import com.qiyuan.web.entity.example.ProductCategoryExample;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.RandomGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryMapper productCategoryMapper;

    /** 新增分類 */
    @Transactional(rollbackFor = Exception.class)
    public boolean addCategory(CategoryAdminRequest req) {
        Date now = DateUtil.getCurrentDate();
        ProductCategory entity = ProductCategory.builder()
                .id(RandomGenerator.getUUID().toLowerCase(Locale.ROOT))
                .name(StringUtils.trimToEmpty(req.getName()))
                .description(StringUtils.defaultString(req.getDescription()))
                .status(Boolean.TRUE)
                .sortOrder(req.getSortOrder() == null ? 0 : req.getSortOrder())
                .createTime(now)
                .updateTime(now)
                .build();

        return productCategoryMapper.insert(entity) > 0;
    }

    /** 修改分類（部分欄位可選改） */
    @Transactional(rollbackFor = Exception.class)
    public boolean modifyCategory(ModifyCategoryAdminRequest req) {
        ProductCategory existed = productCategoryMapper.selectByPrimaryKey(req.getId());
        if (existed == null) {
            throw new ApiException("修改失敗，查無資料");
        }

        // 允許部分欄位更新；只有非 null / 非空才覆蓋
        if (StringUtils.isNotBlank(req.getName())) {
            existed.setName(StringUtils.trim(req.getName()));
        }
        if (Objects.nonNull(req.getDescription())) {
            existed.setDescription(StringUtils.defaultString(req.getDescription()));
        }
        if (Objects.nonNull(req.getStatus())) {
            existed.setStatus(req.getStatus());
        }
        if (Objects.nonNull(req.getSortOrder())) {
            existed.setSortOrder(req.getSortOrder());
        }

        existed.setUpdateTime(DateUtil.getCurrentDate());
        return productCategoryMapper.updateByPrimaryKeySelective(existed) > 0;
    }

    /** 取得可用分類（狀態=true），按 sort_order、create_time 排序 */
    public List<ProductCategoryVO> getCategoryAvailableList() {
        ProductCategoryExample e = new ProductCategoryExample();
        e.createCriteria().andStatusEqualTo(true);
        e.setOrderByClause("sort_order ASC, create_time ASC");

        List<ProductCategory> list = productCategoryMapper.selectByExample(e);
        return list.stream()
                .map(p -> ProductCategoryVO.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .description(p.getDescription())
                        .sortOrder(p.getSortOrder())
                        .status(p.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ProductCategory> getCategoryList() {
        ProductCategoryExample e = new ProductCategoryExample();
        e.setOrderByClause("sort_order ASC, create_time ASC");
        return productCategoryMapper.selectByExample(e);
    }

    public ProductCategory getCategory(String id) {
        return productCategoryMapper.selectByPrimaryKey(id);
    }
}
