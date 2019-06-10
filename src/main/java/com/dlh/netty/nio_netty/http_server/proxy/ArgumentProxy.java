package com.dlh.netty.nio_netty.http_server.proxy;

import com.dlh.netty.nio_netty.http_server.proxy.enhance.EnhanceHandler;

/**
 * @author: dulihong
 * @date: 2019/6/10 14:50
 */
public interface ArgumentProxy {

    public Object getTarget();

    public Object newProxy(Object target, EnhanceHandler enhanceHandler);
}
