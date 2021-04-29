package com.atguigu.crowd.exception;

/**
 * @program: Shangchou
 * @description: 注册或修改账户信息已经被使用
 * @author: lance
 * @create: 2021-01-30 00:04
 */
public class LoginAcctAlreadyInUseException extends RuntimeException{

    private static final long serialVersionUID = 7851422581225558860L;

    public LoginAcctAlreadyInUseException() {
    }

    public LoginAcctAlreadyInUseException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
