package com.qiyuan.web.service;

import com.qiyuan.web.dao.MasterServiceRequestMapper;
import com.qiyuan.web.entity.MasterServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterRequestService {
    @Autowired
    private MasterServiceRequestMapper mapper;

    public boolean addMasterRequest(com.qiyuan.web.request.MasterServiceRequest req) {
        MasterServiceRequest request = MasterServiceRequest.builder()
                .masterCode(req.getMasterCode())
                .service(req.getServiceItem())
                .email(req.getCustomerEmail())
                .lineId(req.getCustomerLine())
                .name(req.getCustomerName())
                .note(req.getNote())
                .phone(req.getCustomerPhone())
                .build();
        return mapper.insertSelective(request) > 0;
    }
}
