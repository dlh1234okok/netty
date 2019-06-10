package com.dlh.netty.nio_netty.http_server.resolver;

import org.springframework.util.StringUtils;

/**
 * @author: dulihong
 * @date: 2019/6/10 9:48
 */
public class DefaultMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return !StringUtils.isEmpty(methodParameter.getRequestUri());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter) {
        return null;
    }

}
