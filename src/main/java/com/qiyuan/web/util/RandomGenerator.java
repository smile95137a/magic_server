package com.qiyuan.web.util;

import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase(Locale.ROOT);
    }

    /**
     * 產生一個 [start, end] 範圍內的隨機整數，機率幾乎均等。
     * @param start 起始值（包含）
     * @param end 結束值（包含）
     * @return 隨機整數
     * @throws IllegalArgumentException 如果 start > end
     */
    public static int getRandomNumberInRange(int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException("Start 必須小於或等於 End");
        }
        return ThreadLocalRandom.current().nextInt(start, end + 1);
    }

}
