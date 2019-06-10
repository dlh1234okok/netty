package com.dlh.netty.nio_netty.http_server.path_resolver.binding;

import com.dlh.netty.nio_netty.http_server.path_resolver.MethodParameter;

/**
 * @author: dulihong
 * @date: 2019/6/10 11:01
 */
public interface HandlerArgumentBinding {

    public void bind(MethodParameter methodParameter);

}
