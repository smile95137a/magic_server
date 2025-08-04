package com.qiyuan.web.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueInvoiceDto {
    
    private String user_id; // 特店代號，必填，通常為統一編號
    
    private String check_pwd; // 特店驗證密碼，必填

    private String eInvoiceMessage; // 傳輸型態，F0401 / A0101，可空

    private String InvoiceNumber; // 發票號碼，10碼，未填由系統取號
    
    private String InvoiceDate; // 格式: YYYY/MM/DD
    
    private String InvoiceTime; // 格式: HH:mm:ss

    private String InvoiceType; // 07:一般電子發票, 08:特種稅額，預設 07

    private String BuyerIdentifier; // 統編，有統編才填

    private String BuyerName;
    private String BuyerAddress;
    private String BuyerTelephoneNumber;
    private String BuyerEmailAddress;


    private Integer SalesAmount;
    private Integer FreeTaxSalesAmount;
    private Integer ZeroTaxSalesAmount;


    private String TaxType; // 1~4 or 9
    private BigDecimal TaxRate; // 例如 0.05
    private Integer TaxAmount;
    
    private Integer TotalAmount;

    
    private String PrintMark; // Y or N

    private String CustomsClearanceMark; // 零稅率時必填 (1 or 2)

    private String CarrierType; // 載具類別，條碼類型等
    private String CarrierId1;
    private String CarrierId2;

    private String NPOBAN; // 捐贈碼

    private String ZeroTaxRateReason; // 零稅率原因（71-79）

    private String MainRemark;

    private List<InvoiceItemDto> ITEM; // 消費明細

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvoiceItemDto {
        
        private String Description; // 商品名稱

        
        private Integer Quantity; // 數量

        
        private BigDecimal UnitPrice; // 單價

        private String TaxType; // 只有當主發票 TaxType 為 9 時必填

        
        private Integer Amount; // 小計金額

        private String Remark; // 備註（如健康捐等）

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public Integer getQuantity() {
            return Quantity;
        }

        public void setQuantity(Integer quantity) {
            Quantity = quantity;
        }

        public BigDecimal getUnitPrice() {
            return UnitPrice;
        }

        public void setUnitPrice(BigDecimal unitPrice) {
            UnitPrice = unitPrice;
        }

        public String getTaxType() {
            return TaxType;
        }

        public void setTaxType(String taxType) {
            TaxType = taxType;
        }

        public Integer getAmount() {
            return Amount;
        }

        public void setAmount(Integer amount) {
            Amount = amount;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCheck_pwd() {
        return check_pwd;
    }

    public void setCheck_pwd(String check_pwd) {
        this.check_pwd = check_pwd;
    }

    public String geteInvoiceMessage() {
        return eInvoiceMessage;
    }

    public void seteInvoiceMessage(String eInvoiceMessage) {
        this.eInvoiceMessage = eInvoiceMessage;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getInvoiceDate() {
        return InvoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        InvoiceDate = invoiceDate;
    }

    public String getInvoiceTime() {
        return InvoiceTime;
    }

    public void setInvoiceTime(String invoiceTime) {
        InvoiceTime = invoiceTime;
    }

    public String getInvoiceType() {
        return InvoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        InvoiceType = invoiceType;
    }

    public String getBuyerIdentifier() {
        return BuyerIdentifier;
    }

    public void setBuyerIdentifier(String buyerIdentifier) {
        BuyerIdentifier = buyerIdentifier;
    }

    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    public String getBuyerAddress() {
        return BuyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        BuyerAddress = buyerAddress;
    }

    public String getBuyerTelephoneNumber() {
        return BuyerTelephoneNumber;
    }

    public void setBuyerTelephoneNumber(String buyerTelephoneNumber) {
        BuyerTelephoneNumber = buyerTelephoneNumber;
    }

    public String getBuyerEmailAddress() {
        return BuyerEmailAddress;
    }

    public void setBuyerEmailAddress(String buyerEmailAddress) {
        BuyerEmailAddress = buyerEmailAddress;
    }

    public Integer getSalesAmount() {
        return SalesAmount;
    }

    public void setSalesAmount(Integer salesAmount) {
        SalesAmount = salesAmount;
    }

    public Integer getFreeTaxSalesAmount() {
        return FreeTaxSalesAmount;
    }

    public void setFreeTaxSalesAmount(Integer freeTaxSalesAmount) {
        FreeTaxSalesAmount = freeTaxSalesAmount;
    }

    public Integer getZeroTaxSalesAmount() {
        return ZeroTaxSalesAmount;
    }

    public void setZeroTaxSalesAmount(Integer zeroTaxSalesAmount) {
        ZeroTaxSalesAmount = zeroTaxSalesAmount;
    }

    public String getTaxType() {
        return TaxType;
    }

    public void setTaxType(String taxType) {
        TaxType = taxType;
    }

    public BigDecimal getTaxRate() {
        return TaxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        TaxRate = taxRate;
    }

    public Integer getTaxAmount() {
        return TaxAmount;
    }

    public void setTaxAmount(Integer taxAmount) {
        TaxAmount = taxAmount;
    }

    public Integer getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getPrintMark() {
        return PrintMark;
    }

    public void setPrintMark(String printMark) {
        PrintMark = printMark;
    }

    public String getCustomsClearanceMark() {
        return CustomsClearanceMark;
    }

    public void setCustomsClearanceMark(String customsClearanceMark) {
        CustomsClearanceMark = customsClearanceMark;
    }

    public String getCarrierType() {
        return CarrierType;
    }

    public void setCarrierType(String carrierType) {
        CarrierType = carrierType;
    }

    public String getCarrierId1() {
        return CarrierId1;
    }

    public void setCarrierId1(String carrierId1) {
        CarrierId1 = carrierId1;
    }

    public String getCarrierId2() {
        return CarrierId2;
    }

    public void setCarrierId2(String carrierId2) {
        CarrierId2 = carrierId2;
    }

    public String getNPOBAN() {
        return NPOBAN;
    }

    public void setNPOBAN(String NPOBAN) {
        this.NPOBAN = NPOBAN;
    }

    public String getZeroTaxRateReason() {
        return ZeroTaxRateReason;
    }

    public void setZeroTaxRateReason(String zeroTaxRateReason) {
        ZeroTaxRateReason = zeroTaxRateReason;
    }

    public String getMainRemark() {
        return MainRemark;
    }

    public void setMainRemark(String mainRemark) {
        MainRemark = mainRemark;
    }

    public List<InvoiceItemDto> getITEM() {
        return ITEM;
    }

    public void setITEM(List<InvoiceItemDto> ITEM) {
        this.ITEM = ITEM;
    }
}
