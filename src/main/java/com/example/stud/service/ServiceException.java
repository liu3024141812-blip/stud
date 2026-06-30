package com.example.stud.service;

/**
 * 业务层统一异常（包装底层 SQLException 等受检异常为非受检异常）。
 * Controller 不再需要 throws SQLException，可统一 try-catch 弹窗提示。
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
