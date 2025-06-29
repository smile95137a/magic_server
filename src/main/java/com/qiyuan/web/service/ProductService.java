package com.qiyuan.web.service;

import com.qiyuan.security.config.ImagePathMappingConfig;
import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.ProductCategoryMapper;
import com.qiyuan.web.dao.ProductImageMapper;
import com.qiyuan.web.dao.ProductMapper;
import com.qiyuan.web.dto.GalleryImageVO;
import com.qiyuan.web.dto.ProductAdminVO;
import com.qiyuan.web.dto.request.*;
import com.qiyuan.web.dto.response.CreateProductDraftResponse;
import com.qiyuan.web.dto.response.ProductDetailVO;
import com.qiyuan.web.dto.response.ProductVO;
import com.qiyuan.web.dto.response.UploadImageResponse;
import com.qiyuan.web.entity.Product;
import com.qiyuan.web.entity.ProductCategory;
import com.qiyuan.web.entity.ProductImage;
import com.qiyuan.web.entity.example.ProductExample;
import com.qiyuan.web.enums.ProductImageType;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.FileUtil;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Value("${upload.image-path.product}")
    private String productImageDir;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImageMapper productImageMapper;
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private ImagePathMappingConfig mappingConfig;

    @Transactional
    public CreateProductDraftResponse createProductDraft() {
        Date currentDate = DateUtil.getCurrentDate();
        Product product = new Product();
        product.setStatus(false); // 草稿
        product.setCreateTime(currentDate);
        product.setUpdateTime(currentDate);
        productMapper.insertSelective(product);
        return new CreateProductDraftResponse(product.getId());
    }

    @Transactional
    public UploadImageResponse uploadImage(@Valid UploadImageRequest req) {
        Integer productId = req.getProductId();
        ProductImageType imageType = ProductImageType.fromString(req.getType());
        MultipartFile file = req.getFile();

        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) throw new ApiException("商品不存在");
        if (Boolean.TRUE.equals(product.getStatus()))
            throw new ApiException("商品已完成，禁止上傳圖片");

        String baseDir = mappingConfig.getImagePath("product");
        String subDir = imageType.getFolder();
        String saveDir = FileUtil.concatFilePath(baseDir, productId.toString(), subDir);
        String urlPrefix = mappingConfig.getUrlPrefix("product") + productId + "/" + subDir + "/";

        String filename = FileUtil.saveUploadImageReturnFilename(file, saveDir, 5);
        String url = urlPrefix + filename;

        if (imageType == ProductImageType.MAIN) {
            Product update = new Product();
            update.setId(productId);
            update.setMainImage(filename);
            update.setUpdateTime(new Date());
            productMapper.updateByPrimaryKeySelective(update);
        } else {
            ProductImage img = new ProductImage();
            img.setProductId(productId);
            img.setType(imageType.getFolder());
            img.setFilename(filename);
            img.setSort(imageType == ProductImageType.GALLERY ? getNextGallerySort(productId) : null);
            img.setCreateTime(new Date());
            img.setUpdateTime(new Date());
            productImageMapper.insertSelective(img);
        }

        return new UploadImageResponse(url);
    }

    private short getNextGallerySort(Integer productId) {
        Short maxSort = productImageMapper.selectMaxSortByProductId(productId);
        return (short) ((maxSort == null) ? 1 : maxSort + 1);
    }

    /**
     * 清理未被 description 內容引用的圖片（以前叫 cleanUnusedRichImages）
     */
    private void cleanUnusedDescriptionImages(Integer productId, String detailHtml) {
        String productPhysicalDir = mappingConfig.getImagePath("product");
        File descriptionDir = new File(FileUtil.concatFilePath(productPhysicalDir, productId.toString(), ProductImageType.DESCRIPTION.getFolder()));
        if (!descriptionDir.exists()) return;
        // 找出HTML引用的圖片檔名
        Pattern p = Pattern.compile("/images/product/" + productId + "/" + ProductImageType.DESCRIPTION.getFolder() + "/([\\w\\-.]+)");
        Set<String> usedImages = new HashSet<>();
        if (detailHtml != null) {
            Matcher m = p.matcher(detailHtml);
            while (m.find()) usedImages.add(m.group(1));
        }
        // 刪除未被引用的圖片
        for (File f : descriptionDir.listFiles()) {
            if (!usedImages.contains(f.getName())) f.delete();
        }
    }

    public String getProductImagePhysicalPath(Integer productId, ProductImageType type, String filename) {
        String baseFolder = mappingConfig.getImagePath("product");
        return FileUtil.concatFilePath(baseFolder, String.valueOf(productId), type.getFolder(), filename);
    }

    public String getProductImageUrl(Integer productId, ProductImageType type, String filename) {
        String baseUrl = mappingConfig.getUrlPrefix("product");
        if (!baseUrl.endsWith("/")) baseUrl = baseUrl + "/";
        return baseUrl + productId + "/" + type.getFolder() + "/" + filename;
    }

    public GalleryImageVO toGalleryImageVO(ProductImage image) {
        return GalleryImageVO.builder()
                .id(image.getId())
                .sort(image.getSort() != null ? image.getSort().intValue() : null)
                .url(getProductImageUrl(image.getProductId(), ProductImageType.GALLERY, image.getFilename()))
                .description(image.getDescription())
                .build();
    }

    @Transactional
    public boolean editProduct(EditProductRequest req) {
        // 驗證商品草稿
        Product product = productMapper.selectByPrimaryKey(req.getProductId());
        if (product == null) throw new ApiException("商品不存在");
        if (Boolean.TRUE.equals(product.getStatus()))
            throw new ApiException("商品已完成，禁止重複提交");

        // 驗證分類（如有）
        if (req.getCategoryId() != null) {
            ProductCategory c = productCategoryMapper.selectByPrimaryKey(req.getCategoryId());
            if (c == null) throw new ApiException("分類不存在");
        }

        // 更新商品主資料
        Product update = new Product();
        update.setId(req.getProductId());
        update.setName(req.getName());
        update.setCategoryId(req.getCategoryId());
        update.setSubtitle(req.getSubtitle());
        update.setDescription(req.getDescription());
        update.setRemark(req.getRemark());
        update.setOriginalPrice(req.getOriginalPrice());
        update.setSpecialPrice(req.getSpecialPrice());
        update.setMainImage(req.getMainImage());
        update.setDetailHtml(req.getDetailHtml());
        update.setStatus(true); // 正式
        update.setUpdateTime(new Date());
        productMapper.updateByPrimaryKeySelective(update);

        // **只更新 gallery 排序**
        if (req.getGalleryImages() != null) {
            for (GalleryImageVO img : req.getGalleryImages()) {
                if (img.getId() != null && img.getSort() != null) {
                    productImageMapper.updateSortById(img.getId(), img.getSort().shortValue());
                }
            }
        }

        // 清理 description 圖片
        cleanUnusedDescriptionImages(req.getProductId(), req.getDetailHtml());

        return true;
    }


    @Transactional
    public boolean deleteImage(DeleteImageRequest req) {
        ProductImage img = productImageMapper.selectByPrimaryKey(req.getImageId());
        if (img == null || !img.getProductId().equals(req.getProductId()))
            throw new ApiException("圖片不存在");

        // 刪DB
        productImageMapper.deleteByPrimaryKey(req.getImageId());

        // 刪檔案
        String path = getProductImagePhysicalPath(img.getProductId(), ProductImageType.fromString(img.getType()), img.getFilename());
        File f = new File(path);
        if (f.exists()) f.delete();
        return true;
    }


    private String extractFilenameFromUrl(String url, String prefix) {
        if (url != null && url.startsWith(prefix)) {
            return url.substring(prefix.length());
        }
        return url;
    }

    @Transactional
    public boolean discardProduct(DiscardProductRequest req) throws IOException {
        Integer productId = req.getProductId();
        productImageMapper.deleteByProductId(productId);
        productMapper.deleteByPrimaryKey(productId);
        String dir = FileUtil.concatFilePath(mappingConfig.getImagePath("product"), productId.toString());
        File productDir = new File(dir);
        if (productDir.exists()) org.apache.commons.io.FileUtils.deleteDirectory(productDir);
        return true;
    }

    public ProductAdminVO getProduct(Integer productId) {
        Product p = productMapper.selectByPrimaryKey(productId);
        if (p == null) throw new ApiException("查無資料");
        List<ProductImage> galleryList = productImageMapper.selectByProductIdAndType(productId, ProductImageType.GALLERY.getFolder());
        List<GalleryImageVO> galleryVOs = galleryList.stream().map(this::toGalleryImageVO).collect(Collectors.toList());

        return ProductAdminVO.builder()
                .id(p.getId())
                .categoryId(p.getCategoryId())
                .name(p.getName())
                .subtitle(p.getSubtitle())
                .description(p.getDescription())
                .remark(p.getRemark())
                .originalPrice(p.getOriginalPrice())
                .specialPrice(p.getSpecialPrice())
                .mainImageUrl(buildMainImageUrl(p))
                .galleryImages(galleryVOs)
                .detailHtml(p.getDetailHtml())
                .status(p.getStatus())
                .build();
    }

    private String buildMainImageUrl(Product p) {
        if (p.getMainImage() == null) return null;
        String prefix = mappingConfig.getUrlPrefix("product") + p.getId() + "/" + ProductImageType.MAIN.getFolder() + "/";
        return prefix + p.getMainImage();
    }

    public List<ProductVO> getAvailableProductByCategory(ProductListRequest req) {
        ProductExample e = new ProductExample();
        ProductExample.Criteria criteria = e.createCriteria();
        if (req != null && StringUtils.isNotBlank(req.getCategoryId())) {
            criteria.andCategoryIdEqualTo(req.getCategoryId());
        }
        criteria.andStatusEqualTo(true);

        List<Product> products = productMapper.selectByExample(e);
        return products.stream()
                .map(p -> {
                    String mainImageUrl = buildMainImageUrl(p);
                    return new ProductVO(
                            p.getId(), p.getName(), p.getOriginalPrice(), p.getSpecialPrice(), mainImageUrl
                    );
                })
                .collect(Collectors.toList());
    }

    public ProductDetailVO getProductDetail(Integer productId) {
        Product p = productMapper.selectByPrimaryKey(productId);
        if (p == null || !Boolean.TRUE.equals(p.getStatus()))
            throw new ApiException("商品不存在或已下架");

        // gallery
        List<ProductImage> galleryList = productImageMapper.selectByProductIdAndType(productId, ProductImageType.GALLERY.getFolder());
        List<String> galleryImageUrls = galleryList.stream()
                .sorted(Comparator.comparing(ProductImage::getSort))
                .map(img -> getProductImageUrl(img.getProductId(), ProductImageType.GALLERY, img.getFilename()))
                .collect(Collectors.toList());

        return ProductDetailVO.builder()
                .id(p.getId())
                .categoryId(p.getCategoryId())
                .name(p.getName())
                .subtitle(p.getSubtitle())
                .description(p.getDescription())
                .remark(p.getRemark())
                .originalPrice(p.getOriginalPrice())
                .specialPrice(p.getSpecialPrice())
                .mainImageUrl(buildMainImageUrl(p))
                .galleryImageUrls(galleryImageUrls)
                .detailHtml(p.getDetailHtml())
                .status(p.getStatus())
                .build();
    }


}
