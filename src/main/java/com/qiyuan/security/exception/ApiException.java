package com.qiyuan.security.exception;

public class ApiException extends RuntimeException {
    private final String message;

    public ApiException(String message) {
        super(message);
        this.message = message;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

