package com.qiyuan.web.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Result")
public class GomypayVoidInvoiceResponse {
    @JacksonXmlProperty(localName = "RtnCode")
    private String rtnCode;

    @JacksonXmlProperty(localName = "RtnMsg")
    private String rtnMsg;

    private transient String rawResponse;
}
