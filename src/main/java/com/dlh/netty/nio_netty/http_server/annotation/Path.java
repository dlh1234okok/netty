package com.dlh.netty.nio_netty.http_server.annotation;

import java.lang.annotation.*;

/**
 * @author: dulihong
 * @date: 2019/6/6 11:36
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Path {

    String value() default "/";

}
