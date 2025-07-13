package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.ProductSpecMapper;
import com.qiyuan.web.dao.ProductSpecStockMapper;
import com.qiyuan.web.entity.ProductSpec;
import com.qiyuan.web.entity.ProductSpecStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final ProductSpecMapper productSpecMapper;
    private final ProductSpecStockMapper productSpecStockMapper;

    /**
     * 扣減規格庫存（下單時）
     * @param specId 規格ID
     * @param quantity 扣減數量
     */
    public void decreaseStock(Integer specId, Integer quantity) {
        ProductSpec spec = productSpecMapper.selectByPrimaryKey(specId);
        if (spec == null) throw new ApiException("商品規格不存在");
        ProductSpecStock stock = productSpecStockMapper.selectByPrimaryKey(specId);
        if (stock == null) throw new ApiException("庫存資料不存在");
        if (stock.getStock() == null || stock.getStock() < quantity) {
            throw new ApiException("規格[" + spec.getSpecValue() + "]庫存不足");
        }
        stock.setStock(stock.getStock() - quantity);
        productSpecStockMapper.updateByPrimaryKeySelective(stock);
    }

    /**
     * 回補規格庫存（訂單取消、退貨）
     */
    public void increaseStock(Integer specId, Integer quantity) {
        ProductSpec spec = productSpecMapper.selectByPrimaryKey(specId);
        if (spec == null) throw new ApiException("商品規格不存在");
        ProductSpecStock stock = productSpecStockMapper.selectByPrimaryKey(specId);
        if (stock == null) throw new ApiException("庫存資料不存在");
        stock.setStock((stock.getStock() == null ? 0 : stock.getStock()) + quantity);
        productSpecStockMapper.updateByPrimaryKeySelective(stock);
    }
}
