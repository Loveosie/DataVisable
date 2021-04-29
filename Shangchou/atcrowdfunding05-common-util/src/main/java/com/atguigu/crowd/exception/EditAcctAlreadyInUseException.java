package com.atguigu.crowd.exception;

/**
 * @program: Shangchou
 * @description: 修改账户信息已经被使用
 * @author: lance
 * @create: 2021-01-30 00:04
 */
public class EditAcctAlreadyInUseException extends RuntimeException{

    private static final long serialVersionUID = 7851422581225558860L;

    public EditAcctAlreadyInUseException() {
    }

    public EditAcctAlreadyInUseException(String message) {
        super(message);
    }

    public EditAcctAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public EditAcctAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    public EditAcctAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
