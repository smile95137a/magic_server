package com.qiyuan.web.enums;

public enum ImageExtension {
    JPG("jpg", "image/jpeg"),
    JPEG("jpeg", "image/jpeg"),
    PNG("png", "image/png"),
    GIF("gif", "image/gif"),
    BMP("bmp", "image/bmp"),
    WEBP("webp", "image/webp");

    private final String extension;
    private final String contentType;

    ImageExtension(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public String getExtension() {
        return extension;
    }

    public String getContentType() {
        return contentType;
    }

    public static ImageExtension fromFilename(String filename) {
        if (filename == null) return null;
        String lower = filename.toLowerCase();
        for (ImageExtension ext : values()) {
            if (lower.endsWith("." + ext.extension)) {
                return ext;
            }
        }
        return null;
    }

    public static ImageExtension fromContentType(String contentType) {
        if (contentType == null) return null;
        for (ImageExtension ext : values()) {
            if (ext.contentType.equalsIgnoreCase(contentType)) {
                return ext;
            }
        }
        return null;
    }
}
