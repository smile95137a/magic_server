package com.qiyuan.web.service;

import com.qiyuan.security.config.ImagePathMappingConfig;
import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.ProductMapper;
import com.qiyuan.web.dao.ProductStockMapper;
import com.qiyuan.web.dto.ProductStockVO;
import com.qiyuan.web.entity.Product;
import com.qiyuan.web.entity.ProductStock;
import com.qiyuan.web.entity.example.ProductStockExample;
import com.qiyuan.web.enums.ProductImageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {
    @Autowired
    private ProductStockMapper productStockMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ImagePathMappingConfig mappingConfig;

    // 查詢列表
    public List<ProductStockVO> list(Integer productId) {
        ProductStockExample example = new ProductStockExample();
        if (productId != null) {
            example.createCriteria().andProductIdEqualTo(productId);
        }
        List<ProductStock> list = productStockMapper.selectByExample(example);
        if (list == null || list.isEmpty()) return Collections.EMPTY_LIST;
        return list.stream().map(this::convertToVo).collect(Collectors.toList());
    }

    // 查詢單一商品
    public ProductStock getByProductId(Integer productId) {
        ProductStockExample example = new ProductStockExample();
        example.createCriteria().andProductIdEqualTo(productId);
        List<ProductStock> result = productStockMapper.selectByExample(example);
        return result.isEmpty() ? null : result.get(0);
    }

    private String buildMainImageUrl(Product p) {
        if (p.getMainImage() == null) return null;
        String prefix = mappingConfig.getUrlPrefix("product") + p.getId() + "/" + ProductImageType.MAIN.getFolder() + "/";
        return prefix + p.getMainImage();
    }

    private ProductStockVO convertToVo(ProductStock s) {
        Product product = productMapper.selectByPrimaryKey(s.getProductId());
        return ProductStockVO.builder()
                .id(s.getId())
                .productId(s.getProductId())
                .productName(product.getName())
                .url(buildMainImageUrl(product))
                .reservedStock(s.getReservedStock())
                .totalStock(s.getTotalStock())
                .updateTime(s.getUpdateTime())
                .remark(s.getRemark())
                .build();

    }

    // 新增庫存紀錄
    public void add(Integer productId) {
        ProductStock ps = new ProductStock();
        ps.setProductId(productId);
        ps.setTotalStock(0);
        ps.setReservedStock(0);
        ps.setUpdateTime(new Date());
        productStockMapper.insertSelective(ps);
    }

    // 更新庫存
    public void update(ProductStock req) {
        req.setUpdateTime(new Date());
        productStockMapper.updateByPrimaryKeySelective(req);
    }

    // 刪除庫存紀錄
    public void delete(Integer productId) {
        ProductStockExample pse = new ProductStockExample();
        pse.createCriteria().andProductIdEqualTo(productId);
        productStockMapper.deleteByExample(pse);

    }

    // 進貨（增加總庫存）
    public void addStock(Integer productId, Integer amount, String remark) {
        ProductStock stock = getByProductId(productId);
        if (stock == null) throw new ApiException("商品不存在");
        stock.setTotalStock(stock.getTotalStock() + amount);
        stock.setUpdateTime(new Date());
        stock.setRemark(remark);
        productStockMapper.updateByPrimaryKeySelective(stock);
    }

    // 下單鎖定庫存
    public void reserveStock(Integer productId, Integer amount, String remark) {
        ProductStock stock = getByProductId(productId);
        if (stock == null) throw new RuntimeException("商品不存在");
        if (stock.getTotalStock() < amount) throw new RuntimeException("庫存不足");
        stock.setTotalStock(stock.getTotalStock() - amount);
        stock.setReservedStock(stock.getReservedStock() + amount);
        stock.setUpdateTime(new Date());
        stock.setRemark(remark);
        productStockMapper.updateByPrimaryKeySelective(stock);
    }

    // 出貨，扣除 reserved
    public void shipReservedStock(Integer productId, Integer amount, String remark) {
        ProductStock stock = getByProductId(productId);
        if (stock == null) throw new RuntimeException("商品不存在");
        if (stock.getReservedStock() < amount) throw new RuntimeException("無足夠待出貨庫存");
        stock.setReservedStock(stock.getReservedStock() - amount);
        stock.setUpdateTime(new Date());
        stock.setRemark(remark);
        productStockMapper.updateByPrimaryKeySelective(stock);
    }

    // 釋放預約庫存（ex:訂單取消）
    public void releaseReservedStock(Integer productId, Integer amount, String remark) {
        ProductStock stock = getByProductId(productId);
        if (stock == null) throw new RuntimeException("商品不存在");
        if (stock.getReservedStock() < amount) throw new RuntimeException("無足夠預約庫存可釋放");
        stock.setReservedStock(stock.getReservedStock() - amount);
        stock.setTotalStock(stock.getTotalStock() + amount);
        stock.setUpdateTime(new Date());
        stock.setRemark(remark);
        productStockMapper.updateByPrimaryKeySelective(stock);
    }
}
