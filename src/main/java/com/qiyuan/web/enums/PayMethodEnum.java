package com.qiyuan.web.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public enum PayMethodEnum {
    CREDIT_CARD("credit_card", "信用卡", "0", false),
    CREDIT_CARD_INSTALLMENT("credit_card_installment", "信用卡分期", "0", true),
    UNIONPAY("unionpay", "銀聯卡", "1", false),
    CVS_BARCODE("cvs_barcode", "超商條碼繳費", "2", false),
    WEBATM("webatm", "WebATM", "3", false),
    ATM_VIRTUAL("atm_virtual", "虛擬帳號轉帳", "4", false),
    CVS_CODE("cvs_code", "超商代碼繳費", "6", false),
    LINEPAY("linepay", "LINE Pay", "7", false);

    private final String code;
    private final String label;
    private final String apiValue;
    private final boolean installment; // 是否為分期

    public static Set<PayMethodEnum> allows = Set.of(CREDIT_CARD, ATM_VIRTUAL);

    public static List<PayMethodEnum> getAllowsMethod () {
        return new ArrayList<>(allows);
    }

    PayMethodEnum(String code, String label, String apiValue, boolean installment) {
        this.code = code;
        this.label = label;
        this.apiValue = apiValue;
        this.installment = installment;
    }

    public String getCode() { return code; }
    public String getLabel() { return label; }
    public String getApiValue() { return apiValue; }
    public boolean isInstallment() { return installment; }

    public static PayMethodEnum fromCode(String code) {
        for (PayMethodEnum pm : values()) {
            if (pm.getCode().equalsIgnoreCase(code)) return pm;
        }
        throw new IllegalArgumentException("不支援的付款方式: " + code);
    }
}
