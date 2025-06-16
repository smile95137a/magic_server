package com.qiyuan.web.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class FileUtil {

    /**
     * 將圖片 URL（本地或遠端）轉換成 Base64 字串
     *
     * @param imageUrl 圖片的本地路徑或網路 URL
     * @return base64 字串，格式為 "data:image/xxx;base64,..."，若失敗則回傳 null
     */
    public static String imageToBase64(String imageUrl) {
        try {
            byte[] imageBytes;

            if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                imageBytes = fetchImageFromHttp(imageUrl);
            } else {
                imageBytes = fetchImageFromFile(imageUrl);
            }

            if (imageBytes == null) return null;

            String contentType = getContentType(imageUrl);
            String base64 = Base64.getEncoder().encodeToString(imageBytes);
            return "data:" + contentType + ";base64," + base64;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] fetchImageFromHttp(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.connect();

        try (InputStream in = conn.getInputStream();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buf = new byte[4096];
            int n;
            while ((n = in.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
            return out.toByteArray();
        }
    }

    private static byte[] fetchImageFromFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) return null;

        try (InputStream in = new FileInputStream(file);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buf = new byte[4096];
            int n;
            while ((n = in.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
            return out.toByteArray();
        }
    }

    private static String getContentType(String imageUrl) {
        String lower = imageUrl.toLowerCase();
        if (lower.endsWith(".png")) return "image/png";
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) return "image/jpeg";
        if (lower.endsWith(".gif")) return "image/gif";
        return "image/*";
    }
}
