package com.dlh.netty.nio_netty.http_server.resolver.binding;

import com.dlh.netty.nio_netty.http_server.resolver.MethodParameter;

/**
 * @author: dulihong
 * @date: 2019/6/10 11:03
 */
public class ReferenceArgumentBinding implements HandlerArgumentBinding {
    @Override
    public void bind(MethodParameter methodParameter) {
        Class<?> paramClass = methodParameter.getParamClass();
        
    }
}
