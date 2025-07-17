package com.qiyuan.web.controller.admin;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dto.ProductStockVO;
import com.qiyuan.web.dto.request.UpdateTotalStockRequest;
import com.qiyuan.web.entity.ProductStock;
import com.qiyuan.web.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/stock")
public class StockAdminController {

    @Autowired
    private StockService stockService;

    /**
     * 1. 查詢所有商品庫存
     */
    @GetMapping("/list")
    public List<ProductStockVO> listAll() {
        return stockService.list(null);
    }

    /**
     * 2. 查詢單一商品庫存
     */
    @GetMapping("/{productId}")
    public ProductStock getByProductId(@PathVariable Integer productId) {
        return stockService.getByProductId(productId);
    }

    /**
     * 3. 更新總庫存
     *   - 只允許 totalStock 調整
     */
    @PostMapping("/{productId}/adjust")
    public void updateTotalStock(@PathVariable Integer productId, @RequestBody UpdateTotalStockRequest req) {
        ProductStock stock = stockService.getByProductId(productId);
        if (stock == null) throw new ApiException("找不到商品庫存記錄");
        stock.setTotalStock(req.getTotalStock());
        stock.setUpdateTime(new Date());
        stock.setRemark(req.getRemark());
        stockService.update(stock);
    }
}
