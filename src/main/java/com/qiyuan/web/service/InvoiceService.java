package com.qiyuan.web.service;

import com.qiyuan.web.dto.response.InvoiceVo;
import com.qiyuan.web.enums.InvoiceType;


public interface InvoiceService {

    /**
     * 檢查發票資訊是否正確（手機條碼、捐贈碼等）
     */
    boolean validateInfo(InvoiceType type, String code);

    /**
     * 開立發票
     */
    InvoiceVo issueInvoice(String paymentId);
}

