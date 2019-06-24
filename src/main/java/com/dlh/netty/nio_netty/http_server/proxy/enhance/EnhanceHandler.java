package com.dlh.netty.nio_netty.http_server.proxy.enhance;

import java.lang.reflect.Method;

/**
 * @author: dulihong
 * @date: 2019/6/10 16:57
 */
public interface EnhanceHandler {

    public void before(Method method,Object[] args);

    public void after(Object result);

}
