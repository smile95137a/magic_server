package com.qiyuan.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static String adjustDate(String inputDate, int days) {
        Date date = parseStringToDate(inputDate);
        return adjustDate(date, days, String.class);
    }

    public static <T> T adjustDate(String inputDate, int days, Class<T> returnType) {
        Date date = parseStringToDate(inputDate);
        return adjustDate(date, days, returnType);
    }

    public static <T> T adjustDate(Date inputDate, int days, Class<T> returnType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        calendar.add(Calendar.DATE, days);

        return convertResult(calendar, returnType);
    }

    public static <T> T adjustDate(Calendar inputDate, int days, Class<T> returnType) {
        Calendar calendar = (Calendar) inputDate.clone();
        calendar.add(Calendar.DATE, days);

        return convertResult(calendar, returnType);
    }

    public static Date parseStringToDate(String str) {
        str = str.replace("/", "-");
        try {
            return DEFAULT_FORMAT.parse(str);
        } catch (ParseException e) {
            throw new IllegalArgumentException("日期格式錯誤，應為 yyyy-MM-dd 或 yyyy/MM/dd");
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T convertResult(Calendar calendar, Class<T> returnType) {
        if (returnType == String.class) {
            return (T) DEFAULT_FORMAT.format(calendar.getTime());
        } else if (returnType == Date.class) {
            return (T) calendar.getTime();
        } else if (returnType == Calendar.class) {
            return (T) calendar;
        } else {
            throw new IllegalArgumentException("不支援的回傳型別: " + returnType.getSimpleName());
        }
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    private static LocalDateTime getNowTaiwanTime() {
        return LocalDateTime.now(ZoneId.of("Asia/Taipei"));
    }
}
