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

    public String getExtension() { return extension; }
    public String getContentType() { return contentType; }

    public static ImageExtension fromFilename(String filename) {
        if (filename == null) return null;
        int idx = filename.lastIndexOf('.');
        if (idx < 0) return null;
        String ext = filename.substring(idx + 1).toLowerCase();
        for (ImageExtension e : values()) {
            if (e.extension.equals(ext)) return e;
        }
        return null;
    }

    public static ImageExtension fromContentType(String contentType) {
        if (contentType == null) return null;
        for (ImageExtension e : values()) {
            if (contentType.equalsIgnoreCase(e.contentType)) return e;
        }
        return null;
    }
}

