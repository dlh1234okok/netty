package com.dlh.netty.nio_netty.http_server.resolver.binding;

import com.dlh.netty.nio_netty.http_server.resolver.MethodParameter;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author: dulihong
 * @date: 2019/6/10 11:03
 */
public class ReferenceArgumentBinding implements HandlerArgumentBinding {
    @Override
    public void bind(MethodParameter methodParameter) {
        Map<String, Object> requestParam = methodParameter.getRequestParam();
        Method method = methodParameter.getMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();

    }
}
