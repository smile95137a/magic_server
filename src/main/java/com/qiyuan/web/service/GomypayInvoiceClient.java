package com.qiyuan.web.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.qiyuan.web.dto.request.GomypayAddInvoiceRequest;
import com.qiyuan.web.dto.request.GomypayVoidInvoiceRequest;
import com.qiyuan.web.dto.request.InvoiceItem;
import com.qiyuan.web.dto.response.GomypayAddInvoiceResponse;
import com.qiyuan.web.dto.response.GomypayVoidInvoiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
@Service
@RequiredArgsConstructor
public class GomypayInvoiceClient {

    @Value("${gomypay.einvoice.api}")
    private String einvoiceApiBase;

    public GomypayAddInvoiceResponse addInvoice(GomypayAddInvoiceRequest req) {
        String url = einvoiceApiBase + "/AddInvoice";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("UserId", req.getUserId());
        params.add("CheckPwd", req.getCheckPwd());
        params.add("OrderId", req.getOrderId());
        params.add("BuyerIdentifier", req.getBuyerIdentifier());
        params.add("BuyerName", req.getBuyerName());
        params.add("CarrierType", req.getCarrierType());
        params.add("CarrierId1", req.getCarrierId1());
        params.add("NPOBAN", req.getNpoban());
        params.add("PrintMark", req.getPrintMark());
        params.add("Amount", req.getAmount() != null ? req.getAmount().toPlainString() : null);
        params.add("Tax", req.getTax() != null ? req.getTax().toPlainString() : null);
        params.add("Remark", req.getRemark());

        if (req.getItems() != null) {
            int i = 1;
            for (InvoiceItem item : req.getItems()) {
                params.add("ItemName" + i, item.getItemName());
                params.add("ItemPrice" + i, item.getItemPrice() != null ? item.getItemPrice().toPlainString() : null);
                params.add("ItemCount" + i, item.getItemCount() != null ? String.valueOf(item.getItemCount()) : null);
                params.add("ItemAmount" + i, item.getItemAmount() != null ? item.getItemAmount().toPlainString() : null);
                params.add("ItemRemark" + i, item.getItemRemark());
                i++;
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);

        String rawXml = responseEntity.getBody();
        if (rawXml == null || rawXml.trim().isEmpty()) {
            throw new RuntimeException("Gomypay 發票 API 無回應，請稍後再試！");
        }

        try {
            XmlMapper xmlMapper = new XmlMapper();
            GomypayAddInvoiceResponse resp = xmlMapper.readValue(rawXml, GomypayAddInvoiceResponse.class);
            resp.setRawResponse(rawXml);
            return resp;
        } catch (Exception ex) {
            throw new RuntimeException("Gomypay 發票 XML 解析失敗，請聯絡管理員！", ex);
        }
    }

    /**
     * 作廢發票 API 呼叫
     */
    @Transactional
    public GomypayVoidInvoiceResponse voidInvoice(GomypayVoidInvoiceRequest req) {
        String url = einvoiceApiBase + "/VoidInvoice";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("UserId", req.getUserId());
        params.add("CheckPwd", req.getCheckPwd());
        params.add("InvoiceNumber", req.getInvoiceNumber());
        params.add("InvoiceDate", req.getInvoiceDate());
        params.add("Reason", req.getReason());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);

        String rawXml = responseEntity.getBody();
        if (rawXml == null || rawXml.trim().isEmpty()) {
            throw new RuntimeException("Gomypay 發票 API 無回應，請稍後再試！");
        }

        try {
            XmlMapper xmlMapper = new XmlMapper();
            GomypayVoidInvoiceResponse resp = xmlMapper.readValue(rawXml, GomypayVoidInvoiceResponse.class);
            resp.setRawResponse(rawXml); // 保留原始 XML
            return resp;
        } catch (Exception ex) {
            throw new RuntimeException("Gomypay 作廢發票 XML 解析失敗！", ex);
        }
    }

}
