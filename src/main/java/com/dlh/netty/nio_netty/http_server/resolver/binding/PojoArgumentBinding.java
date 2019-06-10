package com.dlh.netty.nio_netty.http_server.resolver.binding;

import com.dlh.netty.common.exceptions.ResolverException;
import com.dlh.netty.nio_netty.http_server.resolver.MethodParameter;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author: dulihong
 * @date: 2019/6/10 11:02
 */
public class PojoArgumentBinding implements HandlerArgumentBinding {

    @Override
    @SuppressWarnings("all")
    public void bind(MethodParameter methodParameter) {
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
                throw new ResolverException("pojo bind failed");
            }
        }
        methodParameter.setArgument(instance);
    }


    private String setMethod(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
