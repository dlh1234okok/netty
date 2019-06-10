package com.dlh.netty.nio_netty.http_server.proxy;

import com.dlh.netty.nio_netty.http_server.proxy.enhance.EnhanceHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: dulihong
 * @date: 2019/6/10 14:51
 */
public class JdkDynamicProxy implements ArgumentProxy, InvocationHandler {

    private Object target;

    private EnhanceHandler enhanceHandler;


    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public Object newProxy(Object target, EnhanceHandler enhanceHandler) {
        this.target = target;
        this.enhanceHandler = enhanceHandler;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        enhanceHandler.before();
        return method.invoke(target, args);
    }

}
