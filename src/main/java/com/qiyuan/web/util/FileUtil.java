package com.qiyuan.web.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class FileUtil {

    public static String concatFilePath(String prev, String suf) {
        String sep = File.separator;
        if (prev.endsWith(sep) && suf.startsWith(sep)) {
            return prev.concat(suf.substring(1));
        } else if (prev.endsWith(sep) || suf.startsWith(sep)) {
            return prev.concat(suf);
        } else {
            return prev.concat(sep).concat(suf);
        }
    }

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

    public static String base64ToImage(String base64Str, String destDir, String originalFilename) {
        if (base64Str == null || destDir == null || originalFilename == null) return null;
        try {
            // 處理 data:image/xxx;base64, 前綴
            String contentType = "image/png";
            String pureBase64 = base64Str;

            if (base64Str.startsWith("data:")) {
                int commaIdx = base64Str.indexOf(',');
                if (commaIdx > 0) {
                    String header = base64Str.substring(0, commaIdx);
                    pureBase64 = base64Str.substring(commaIdx + 1);
                    int semiIdx = header.indexOf(';');
                    if (header.startsWith("data:") && semiIdx > 0) {
                        contentType = header.substring(5, semiIdx);
                    }
                }
            }

            // 原始檔名副檔名
            String ext = "";
            int dotIdx = originalFilename.lastIndexOf(".");
            if (dotIdx >= 0) {
                ext = originalFilename.substring(dotIdx);
            } else {
                // 沒有副檔名才用 contentType
                switch (contentType) {
                    case "image/jpeg":
                    case "image/jpg": ext = ".jpg"; break;
                    case "image/gif": ext = ".gif"; break;
                    case "image/png":
                    default: ext = ".png"; break;
                }
            }

            // 檢查資料夾
            File dir = new File(destDir);
            if (!dir.exists()) {
                if (!dir.mkdirs()) return null;
            }

            // 取得檔名本身
            String fileNameOnly = originalFilename;
            if (dotIdx > 0) fileNameOnly = originalFilename.substring(0, dotIdx);

            // 避免重複加副檔名
            String filename = fileNameOnly + ext;
            File file = new File(dir, filename);

            // decode & 存檔
            byte[] imageBytes = Base64.getDecoder().decode(pureBase64);
            try (OutputStream out = new FileOutputStream(file)) {
                out.write(imageBytes);
            }

            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
