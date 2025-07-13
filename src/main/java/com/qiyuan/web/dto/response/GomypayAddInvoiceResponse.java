package com.qiyuan.web.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Result")
public class GomypayAddInvoiceResponse {
    @JacksonXmlProperty(localName = "RtnCode")
    private String rtnCode;

    @JacksonXmlProperty(localName = "RtnMsg")
    private String rtnMsg;

    @JacksonXmlProperty(localName = "InvoiceNumber")
    private String invoiceNumber;

    @JacksonXmlProperty(localName = "InvoiceDate")
    private String invoiceDate;

    @JacksonXmlProperty(localName = "RandomNumber")
    private String randomNumber;

    private transient String rawResponse;
}

