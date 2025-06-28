package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.ProductCategoryMapper;
import com.qiyuan.web.dao.ProductMapper;
import com.qiyuan.web.dto.request.*;
import com.qiyuan.web.dto.response.ProductCategoryVO;
import com.qiyuan.web.dto.ProductInfo;
import com.qiyuan.web.dto.response.ProductVO;
import com.qiyuan.web.entity.Product;
import com.qiyuan.web.entity.ProductCategory;
import com.qiyuan.web.entity.example.ProductCategoryExample;
import com.qiyuan.web.entity.example.ProductExample;
import com.qiyuan.web.enums.ProductImageType;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.FileUtil;
import com.qiyuan.web.util.RandomGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Value("${upload.image-path.product}")
    private String productImageDir;

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

    public List<ProductVO> getAvailableProductByCategory(ProductListRequest req) {
        Integer limits = req != null ? req.getCount() : null;
        String category = req != null ? req.getCategoryId() : null;

        ProductExample e = new ProductExample();
        ProductExample.Criteria criteria = e.createCriteria();
        if (StringUtils.isNotBlank(category)) {
            criteria.andCategoryIdEqualTo(category);
        }
        criteria.andStatusEqualTo(true);

        List<Product> products = Collections.EMPTY_LIST;
        if (limits == null) {
            limits = 20;
        }
        products = productMapper.selectLimitsByExample(e, limits);

        return products.stream()
                .map(p -> ProductVO.builder()
                        .id(p.getId())
                        .categoryId(p.getCategoryId())
                        .originalPrice(p.getOriginalPrice())
                        .specialPrice(p.getSpecialPrice())
                        .remark(p.getRemark())
                        .subtitle(p.getSubtitle())
                        .mainImageBase64(FileUtil.imageToBase64(
                                FileUtil.concatFilePath(Paths.get(productImageDir).resolve(p.getCategoryId()).resolve(p.getId().toString()).toString(), p.getMainImage())))
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<ProductInfo> getProduct(ProductSearchRequest request) {
        ProductExample e = new ProductExample();
        ProductExample.Criteria criteria = e.createCriteria();
        if (request != null && StringUtils.isNotBlank(request.getCategoryId())) {
            criteria.andCategoryIdEqualTo(request.getCategoryId());
        }
        List<Product> products = productMapper.selectByExample(e);
        return products.stream()
                .map(p -> ProductInfo.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .description(p.getDescription())
                        .remark(p.getRemark())
                        .subtitle(p.getSubtitle())
                        .categoryId(p.getCategoryId())
                        .originalPrice(p.getOriginalPrice())
                        .specialPrice(p.getSpecialPrice())
                        .createTime(p.getCreateTime())
                        .updateTime(p.getUpdateTime())
                        .mainImageBase64(FileUtil.imageToBase64(
                                FileUtil.concatFilePath(productImageDir, p.getCategoryId(), p.getId().toString(), ProductImageType.MAIN.getFolder() ,p.getMainImage())))
                        .imagesBase64(FileUtil.readAllImagesAsBase64(
                                FileUtil.concatFilePath(productImageDir, p.getCategoryId(), p.getId().toString(), ProductImageType.GALLERY.getFolder())))
                        .descriptionBase64(FileUtil.readAllImagesAsBase64(FileUtil.concatFilePath(productImageDir, p.getCategoryId(), p.getId().toString(), ProductImageType.DESCRIPTION.getFolder())))
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean addProduct(ProductInfo p) {
        Date now = DateUtil.getCurrentDate();

        ProductCategory productCategory = productCategoryMapper.selectByPrimaryKey(p.getCategoryId());
        if (productCategory == null) {
            throw new ApiException("新增失敗，查無資料");
        }

        Product info = Product.builder()
                .id(null)
                .name(p.getName())
                .categoryId(p.getCategoryId())
                .subtitle(p.getSubtitle())
                .description(p.getDescription())
                .remark(p.getRemark())
                .originalPrice(p.getOriginalPrice())
                .specialPrice(p.getSpecialPrice())
                .status(true)
                .createTime(now)
                .updateTime(now)
                .build();
        if (productMapper.insert(info) == 0) {
            throw new ApiException("新增失敗");
        }

        if (info.getId() == null) {
            throw new ApiException("發生未知錯誤");
        }

        String mainImageBase64 = p.getMainImageBase64();
        List<String> descriptionBase64 = p.getDescriptionBase64();
        List<String> imagesBase64 = p.getImagesBase64();
        String mainPath = FileUtil.base64ToImage(mainImageBase64, FileUtil.concatFilePath(productImageDir, p.getCategoryId(), info.getId().toString(), ProductImageType.MAIN.getFolder()), "main");
        FileUtil.saveBase64Images(descriptionBase64, FileUtil.concatFilePath(productImageDir, p.getCategoryId(), info.getId().toString(), ProductImageType.DESCRIPTION.getFolder()));
        FileUtil.saveBase64Images(imagesBase64, FileUtil.concatFilePath(productImageDir, p.getCategoryId(), info.getId().toString(), ProductImageType.GALLERY.getFolder()));

       return productMapper.updateByPrimaryKeySelective(Product.builder().id(info.getId()).mainImage(Paths.get(mainPath).getFileName().toString()).build()) > 0;

    }

}
