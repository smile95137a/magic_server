package com.qiyuan.web.util;

public class Base36Util {

    public static String encode(int num) {
        return Integer.toString(num, 36).toUpperCase();
    }

    public static int decode(String base36) {
        return Integer.parseInt(base36, 36);
    }

    /**
     * 轉 base36，不足4碼補0
     */
    public static String encode4digit(int num) {
        String code = Base36Util.encode(num);
        return String.format("%4s", code).replace(' ', '0');
    }

    /**
     * 解碼：長度<=4，會自動去除前導0
     */
    public static int decode4digit(String base36) {
        if (base36 == null) {
            throw new IllegalArgumentException("輸入不能為null");
        }
        String trimmed = base36;
        if (base36.length() <= 4) {
            trimmed = base36.replaceFirst("^0+", "");
            if (trimmed.isEmpty()) trimmed = "0";
        }
        return Base36Util.decode(trimmed);
    }

    public static void main(String[] args) {
        int serial = 10000;      // 假設你流水號從10000起
        String code = encode4digit(serial);    // 7PS
        System.out.println("serial " + serial + " 編碼後：" + code);

        int restored = decode4digit(code);
        System.out.println("還原：" + restored);
    }
}

