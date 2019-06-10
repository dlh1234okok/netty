package com.dlh.netty.nio_netty.http_server.path_resolver;

/**
 * @author: dulihong
 * @date: 2019/6/10 9:30
 */
public interface HandlerMethodArgumentResolver {

    public boolean supportsParameter(MethodParameter methodParameter);

    public Object resolveArgument(MethodParameter methodParameter);

}
