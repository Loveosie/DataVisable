package com.atguigu.crowd.exception;

/**
 * @program: Shangchou
 * @description: 访问受限: 未登录用户
 * @author: lance
 * @create: 2021-01-30 00:04
 */
public class AccessForbiddenException extends RuntimeException{
    private static final long serialVersionUID = -7054227971798592349L;

    public AccessForbiddenException() {
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    public AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
