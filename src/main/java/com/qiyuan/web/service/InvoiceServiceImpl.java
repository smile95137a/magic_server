package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.InvoiceMapper;
import com.qiyuan.web.dto.request.GomypayAddInvoiceRequest;
import com.qiyuan.web.dto.request.GomypayVoidInvoiceRequest;
import com.qiyuan.web.dto.request.InvoiceRequest;
import com.qiyuan.web.dto.response.GomypayAddInvoiceResponse;
import com.qiyuan.web.dto.response.GomypayVoidInvoiceResponse;
import com.qiyuan.web.dto.response.InvoiceResult;
import com.qiyuan.web.entity.Invoice;
import com.qiyuan.web.entity.example.InvoiceExample;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceMapper invoiceMapper;
    private final GomypayInvoiceClient gomypayInvoiceClient;

    @Value("${gomypay.einvoice.customerNo}")
    private String customerNo;
    @Value("${gomypay.einvoice.pwd}")
    private String checkPwd;

    @Override
    @Transactional
    public InvoiceResult createInvoice(InvoiceRequest req) {
        GomypayAddInvoiceRequest apiReq = GomypayAddInvoiceRequest.builder()
                .userId(customerNo)
                .checkPwd(checkPwd)
                .orderId(req.getOrderId())
                .buyerIdentifier(req.getBuyerIdentifier())
                .buyerName(req.getBuyerName())
                .carrierType(req.getCarrierType())
                .carrierId1(req.getCarrierId()) // 這邊要注意是 carrierId1
                .npoban(req.getNpoban())
                .printMark(req.getPrintMark())
                .amount(req.getAmount())
                .tax(req.getTax())
                .items(req.getItems())
                .remark(req.getRemark())
                .build();

        GomypayAddInvoiceResponse resp = gomypayInvoiceClient.addInvoice(apiReq);

        Invoice invoice = new Invoice();
        invoice.setId(RandomGenerator.getUUID());
        invoice.setOrderId(req.getOrderId());
        invoice.setInvoiceNumber(resp.getInvoiceNumber());
        invoice.setInvoiceDate(DateUtil.parseStringToDate(resp.getInvoiceDate()));
        invoice.setRandomNumber(resp.getRandomNumber());
        invoice.setBuyerIdentifier(req.getBuyerIdentifier());
        invoice.setBuyerName(req.getBuyerName());
        invoice.setCarrierType(req.getCarrierType());
        invoice.setCarrierId(req.getCarrierId());
        invoice.setNpoban(req.getNpoban());
        invoice.setPrintMark(req.getPrintMark());
        invoice.setAmount(req.getAmount());
        invoice.setTax(req.getTax());
        invoice.setStatus("1".equals(resp.getRtnCode()) ? "issued" : "failed");
        invoice.setResponseMsg(resp.getRtnMsg());
        invoice.setRawResponse(resp.getRawResponse());  // 保留 XML 原文
        invoice.setCreateTime(new Date());
        invoice.setUpdateTime(new Date());
        invoiceMapper.insertSelective(invoice);

        return InvoiceResult.builder()
                .success("1".equals(resp.getRtnCode()))
                .invoiceNumber(resp.getInvoiceNumber())
                .randomNumber(resp.getRandomNumber())
                .status("1".equals(resp.getRtnCode()) ? "issued" : "failed")
                .message(resp.getRtnMsg())
                .rawResponse(resp.getRawResponse())
                .build();
    }

    @Override
    public InvoiceResult getInvoiceByOrderId(String orderId) {
        InvoiceExample example = new InvoiceExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<Invoice> invoices = invoiceMapper.selectByExample(example);

        if (invoices == null || invoices.isEmpty()) {
            throw new ApiException("發票不存在");
        }
        Invoice invoice = invoices.get(0);

        return InvoiceResult.builder()
                .success("issued".equalsIgnoreCase(invoice.getStatus()))
                .invoiceNumber(invoice.getInvoiceNumber())
                .randomNumber(invoice.getRandomNumber())
                .status(invoice.getStatus())
                .message(invoice.getResponseMsg())
                .rawResponse(invoice.getRawResponse())
                .build();
    }

    @Override
    @Transactional
    public InvoiceResult voidInvoice(String invoiceNumber, String invoiceDate, String reason) {
        GomypayVoidInvoiceRequest apiReq = GomypayVoidInvoiceRequest.builder()
                .userId(customerNo)
                .checkPwd(checkPwd)
                .invoiceNumber(invoiceNumber)
                .invoiceDate(invoiceDate)
                .reason(reason)
                .build();

        GomypayVoidInvoiceResponse resp = gomypayInvoiceClient.voidInvoice(apiReq);

        InvoiceExample example = new InvoiceExample();
        example.createCriteria().andInvoiceNumberEqualTo(invoiceNumber);
        Invoice update = new Invoice();
        update.setStatus("1".equals(resp.getRtnCode()) ? "voided" : "void_failed");
        update.setResponseMsg(resp.getRtnMsg());
        update.setRawResponse(resp.getRawResponse());
        update.setUpdateTime(new Date());
        invoiceMapper.updateByExampleSelective(update, example);

        return InvoiceResult.builder()
                .success("1".equals(resp.getRtnCode()))
                .invoiceNumber(invoiceNumber)
                .status("1".equals(resp.getRtnCode()) ? "voided" : "void_failed")
                .message(resp.getRtnMsg())
                .rawResponse(resp.getRawResponse())
                .build();
    }
}
