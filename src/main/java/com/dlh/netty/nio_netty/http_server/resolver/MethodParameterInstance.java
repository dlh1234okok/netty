package com.dlh.netty.nio_netty.http_server.resolver;

/**
 * @author: dulihong
 * @date: 2019/6/24 14:36
 */
public class MethodParameterInstance {

    private static MethodParameter methodParameter = new MethodParameter();

    public static MethodParameter getInstance() {
        return Sigleton.getInstance();
    }

    private static class Sigleton {
        static MethodParameter getInstance() {
            return methodParameter;
        }
    }

}
