package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.MasterMapper;
import com.qiyuan.web.dao.MasterServiceRequestMapper;
import com.qiyuan.web.entity.Master;
import com.qiyuan.web.entity.MasterServiceRequest;
import com.qiyuan.web.entity.example.MasterExample;
import com.qiyuan.web.util.Base36Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MasterRequestService {

    private static final Logger logger = LoggerFactory.getLogger(MasterRequestService.class);

    private final MasterServiceRequestMapper mapper;

    private final MasterMapper masterMapper;

    public MasterRequestService(MasterServiceRequestMapper mapper, MasterMapper masterMapper) {
        this.masterMapper = masterMapper;
        this.mapper = mapper;
    }

    @Transactional
    public String addMasterRequest(com.qiyuan.web.dto.request.MasterServiceRequest req) {
        MasterExample e = new MasterExample();
        e.createCriteria().andStatusEqualTo(true).andCodeEqualTo(req.getMasterCode());
        List<Master> masters = masterMapper.selectByExample(e);
        if (masters == null || masters.isEmpty()) {
            throw new ApiException("請選擇老師！");
        }

        MasterServiceRequest request = MasterServiceRequest.builder()
                .masterCode(req.getMasterCode())
                .service(req.getServiceItem())
                .email(req.getCustomerEmail())
                .lineId(req.getCustomerLine())
                .name(req.getCustomerName())
                .note(req.getNote())
                .phone(req.getCustomerPhone())
                .build();
        if (mapper.insert(request) == 0) {
            throw new ApiException("系統發生錯誤，請聯繫客服！");
        }
        String serial = String.format("%s-%s", req.getMasterCode(), getOrderIdFromTid(request.getSerial()));
        logger.info("[老師親算] 成功新建訂單 => 編號: {}, 老師代號:{}, 流水號: {}", serial, req.getMasterCode(), request.getSerial());
        return serial;
    }

    public String getOrderIdFromTid(int i) {
        return Base36Util.encode4digit(i);
    }

    public int parseOrderIdToTid(String orderId) {
        return Base36Util.decode4digit(orderId);
    }

}
