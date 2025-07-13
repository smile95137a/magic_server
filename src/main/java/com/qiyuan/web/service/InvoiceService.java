package com.qiyuan.web.service;

import com.qiyuan.web.dto.request.InvoiceRequest;
import com.qiyuan.web.dto.response.InvoiceResult;

public interface InvoiceService {
    InvoiceResult createInvoice(InvoiceRequest req);
    InvoiceResult getInvoiceByOrderId(String orderId);
    InvoiceResult voidInvoice(String invoiceNumber, String invoiceDate, String reason);
}

