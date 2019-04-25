package com.swnote.common.exception;

/**
 * 用于封装提示信息的异常
 * 
 * @author lzj
 * @since 1.0
 * @date [2019-04-23]
 */
public class TipException extends Exception {
    private static final long serialVersionUID = -535664856064139068L;

    public TipException() {
        super();
    }

    public TipException(String message) {
        super(message);
    }

    public TipException(String message, Throwable cause) {
        super(message, cause);
    }

    public TipException(Throwable cause) {
        super(cause);
    }
}