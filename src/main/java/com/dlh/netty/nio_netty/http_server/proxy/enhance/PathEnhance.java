package com.dlh.netty.nio_netty.http_server.proxy.enhance;


import com.dlh.netty.nio_netty.http_server.resolver.MethodParameter;
import com.dlh.netty.nio_netty.http_server.resolver.MethodParameterInstance;
import com.dlh.netty.nio_netty.http_server.resolver.binding.HandlerArgumentBinding;
import com.dlh.netty.nio_netty.http_server.resolver.binding.PojoArgumentBinding;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author: dulihong
 * @date: 2019/6/10 17:03
 */
public class PathEnhance implements EnhanceHandler {

    private HandlerArgumentBinding handlerArgumentBinding;

    @Override
    public void before(Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();
        Class<?>[] clz = new Class[parameters.length];
        MethodParameter methodParameter = MethodParameterInstance.getInstance();
        for (int i = 0; i <= parameters.length - 1; i++) {
            clz[i] = parameters[i].getType();
            methodParameter.setMethod(method);
        }
        methodParameter.setParamClass(clz);
        handlerArgumentBinding = new PojoArgumentBinding();
        handlerArgumentBinding.bind(methodParameter);
        
    }

    @Override
    public void after(Object result) {

    }
}
