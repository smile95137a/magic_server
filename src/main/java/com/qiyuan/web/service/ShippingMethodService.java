package com.qiyuan.web.service;

import com.qiyuan.web.dao.ShippingMethodMapper;
import com.qiyuan.web.dto.request.ShippingMethodUpdateRequest;
import com.qiyuan.web.entity.ShippingMethod;
import com.qiyuan.web.entity.example.ShippingMethodExample;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingMethodService {
    private final ShippingMethodMapper shippingMethodMapper;

    public List<ShippingMethod> list() {
        return shippingMethodMapper.selectByExample(new ShippingMethodExample());
    }

    public boolean update(ShippingMethodUpdateRequest req) {
        ShippingMethod record = new ShippingMethod();
        record.setId(req.getId());
        record.setName(req.getName());
        record.setDescription(req.getDescription());
        record.setFee(req.getFee());
        record.setStatus(req.getStatus());
        record.setSort(req.getSort() == null ? null : (short) req.getSort().intValue());
        record.setUpdateTime(new Date());

        return shippingMethodMapper.updateByPrimaryKeySelective(record) > 0;
    }
}
