package com.dlh.netty.common.exceptions;

/**
 * @author: dulihong
 * @date: 2019/6/6 14:09
 */
public class RequestException extends RuntimeException{


    public RequestException(String message) {
        super(message);
    }

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
