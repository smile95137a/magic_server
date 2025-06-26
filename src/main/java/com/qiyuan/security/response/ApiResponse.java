package com.qiyuan.security.response;

public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;
    private boolean success;

    public ApiResponse(String code, String message, T data, boolean success) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    // Static factory methods for success and error responses
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("0000", message, data, true);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(code, message, null, false);
    }
}
