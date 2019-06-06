package com.dlh.netty.common.exceptions;

/**
 * @author: dulihong
 * @date: 2019/6/6 14:39
 */
public class ResolverException extends RuntimeException{

    public ResolverException(String message) {
        super(message);
    }

    public ResolverException(String message, Throwable cause) {
        super(message, cause);
    }
}
