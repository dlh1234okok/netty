package com.dlh.netty.nio_netty.http_server.servlet.annotation;

import java.lang.annotation.*;

/**
 * @author: dulihong
 * @date: 2019/6/6 14:49
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NettyController {

}
