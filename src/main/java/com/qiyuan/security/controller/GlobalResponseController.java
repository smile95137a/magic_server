package com.qiyuan.security.controller;

import java.sql.SQLException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.security.response.ApiResponse;

import jakarta.validation.ConstraintViolationException;
@RestControllerAdvice
public class GlobalResponseController {

    private static final Logger logger = LoggerFactory.getLogger(GlobalResponseController.class);

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<String> handleCustomError(ApiException ex) {
        logger.error(ex.getMessage(), ex);
        return ApiResponse.error("9401", ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<String> handleAccountError(UsernameNotFoundException ex) {
        logger.error(ex.getMessage(), ex);
        return ApiResponse.error("9001", "帳號或密碼輸入錯誤");
    }

    @ExceptionHandler({SQLException.class, UncategorizedSQLException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<String> handleDatabaseError(Exception ex) {
        logger.error("資料庫錯誤", ex);
        return ApiResponse.error("9002", "資料庫操作錯誤，請稍後再試");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ApiResponse.error("9003", msg);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> handleConstraintViolation(ConstraintViolationException ex) {
        String msg = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
        return ApiResponse.error("9004", msg);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<String> handleAuthorizationDenied(AuthorizationDeniedException ex) {
        return ApiResponse.error("9005", "您沒有權限執行此操作");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<String> handleNoResourceFound(NoResourceFoundException ex) {
        logger.warn("找不到資源: {}", ex.getMessage());
        return ApiResponse.error("9404", "找不到資源");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<String> handleNoHandlerFound(NoHandlerFoundException ex) {
        return ApiResponse.error("9404", "找不到資源");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiResponse<String> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ApiResponse.error("9404", "不支援的請求方式");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<String> handleGeneralError(Exception ex) {
        logger.error("未知系統錯誤", ex);
        return ApiResponse.error("9999", "系統錯誤，請聯絡管理員");
    }
}
