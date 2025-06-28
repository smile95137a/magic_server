package com.qiyuan.web.util;

import com.qiyuan.web.enums.ImageExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class FileUtil {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FileUtil.class);

    public static String concatFilePath(String... paths) {
        return Paths.get(paths[0], java.util.Arrays.copyOfRange(paths, 1, paths.length)).toString();
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
        ImageExtension ext = ImageExtension.fromFilename(imageUrl);
        return ext != null ? ext.getContentType() : "image/*";
    }

    /**
     * base64 轉圖片檔，檔名允許無副檔名（會根據 base64 前綴自動補副檔名）
     */
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

            // 判斷副檔名
            String ext = "";
            int dotIdx = originalFilename.lastIndexOf(".");
            if (dotIdx >= 0) {
                ext = originalFilename.substring(dotIdx);
            } else {
                // 沒有副檔名才用 contentType
                ImageExtension imageExt = ImageExtension.fromContentType(contentType);
                if (imageExt != null) {
                    ext = "." + imageExt.getExtension();
                } else {
                    ext = ".png"; // 預設
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

    /**
     * 將多個 base64 字串儲存為圖片，依序命名為 01.jpg, 02.png ...（自動判斷副檔名，僅支援 ImageExtension）
     * @param base64List 前台傳來的 base64 字串清單
     * @param destDir 儲存資料夾
     * @return List<String> 實際儲存成功的路徑
     */
    public static List<String> saveBase64Images(List<String> base64List, String destDir) {
        if (base64List == null || base64List.isEmpty()) return List.of();

        File dir = new File(destDir);
        if (!dir.exists()) dir.mkdirs();

        List<String> result = new ArrayList<>();
        int digits = Math.max(2, String.valueOf(base64List.size()).length());

        for (int i = 0; i < base64List.size(); i++) {
            String base64 = base64List.get(i);

            // 取得 contentType
            String contentType = "image/png";
            if (base64 != null && base64.startsWith("data:")) {
                int commaIdx = base64.indexOf(',');
                if (commaIdx > 0) {
                    String header = base64.substring(0, commaIdx);
                    int semiIdx = header.indexOf(';');
                    if (header.startsWith("data:") && semiIdx > 0) {
                        contentType = header.substring(5, semiIdx);
                    }
                }
            }
            ImageExtension imageExt = ImageExtension.fromContentType(contentType);
            if (imageExt == null) {
                logger.warn(String.format("不支援的圖片格式：%s，index=%d", contentType, i));
                continue;
            }

            String ext = "." + imageExt.getExtension();
            String filename = String.format("%0" + digits + "d%s", i + 1, ext);
            try {
                String absPath = base64ToImage(base64, destDir, filename);
                if (absPath != null) {
                    result.add(absPath);
                } else {
                    logger.warn(String.format("儲存失敗：%s", filename));
                }
            } catch (Exception e) {
                logger.warn(String.format("例外發生：%s，原因：%s", filename, e.getMessage()));
            }
        }
        return result;
    }

    /**
     * 讀取指定資料夾下所有圖片檔案（不含子資料夾），轉成 base64（含data:image/xxx;base64,...前綴）
     * @param dirPath 資料夾路徑
     * @return List<String> 每個圖片的base64字串
     */
    public static List<String> readAllImagesAsBase64(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.isDirectory()) return List.of();

        File[] files = dir.listFiles((file, name) -> {
            return ImageExtension.fromFilename(name) != null;
        });
        if (files == null) return List.of();

        List<String> result = new ArrayList<>();
        for (File file : files) {
            String base64 = imageToBase64(file.getAbsolutePath());
            if (base64 != null) result.add(base64);
        }
        return result;
    }
}

