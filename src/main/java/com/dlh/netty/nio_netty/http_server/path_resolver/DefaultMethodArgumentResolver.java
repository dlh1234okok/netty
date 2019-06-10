package com.dlh.netty.nio_netty.http_server.path_resolver;

import com.dlh.netty.common.exceptions.ResolverException;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;

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
    @SuppressWarnings("all")
    public Object resolveArgument(MethodParameter methodParameter) {
        Map<String, Object> requestParam = methodParameter.getRequestParam();
        Object instance = null;
        if (null != requestParam) {
            Class<?> clz = methodParameter.getParamClass();
            try {
                instance = clz.newInstance();
                for (Map.Entry<String, Object> entry : requestParam.entrySet()) {
                    Method method = clz.getDeclaredMethod(setMethod(entry.getKey()));
                    method.invoke(instance, entry.getValue());
                }
            } catch (Exception e) {
                throw new ResolverException("resolver param error");
            }

        }
        return instance;
    }

    private String setMethod(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
