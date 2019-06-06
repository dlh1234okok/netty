package com.dlh.netty.nio_netty.http_server.path_resolver;

import com.dlh.netty.common.exceptions.ResolverException;
import com.dlh.netty.nio_netty.http_server.servlet.annotation.NettyController;
import com.dlh.netty.nio_netty.http_server.servlet.annotation.Path;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: dulihong
 * @date: 2019/6/6 14:35
 */
public class DefaultPathResolver implements PathResolverHandler {

    private String BASE_PACKAGE = "com.dlh.netty.nio_netty.http_server.resources";

    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    private List<Class<?>> controllerList = new ArrayList<>();

    private String requestUri;

    private Map<String, Class<?>> requestController = new ConcurrentHashMap<>();

    private Map<String, Method> invokeMap = new ConcurrentHashMap<>();

    private Map<String, Object> requestParams;


    @Override
    public Object execute() {
        init();
        if (CollectionUtils.isEmpty(controllerList)) {
            throw new ResolverException("no controller dispose");
        }
        controllerList.forEach(resource -> {
            Annotation[] types = resource.getAnnotations();
            StringBuilder path = new StringBuilder();
            for (Annotation type : types) {
                if (type instanceof Path) {
                    String value = ((Path) type).value();
                    if (!value.startsWith("/")) {
                        value = "/" + value;
                    }
                    path.append(value);
                }
            }
            Method[] declaredMethods = resource.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                Annotation[] methodAnnotations = declaredMethod.getAnnotations();
                for (Annotation method : methodAnnotations) {
                    if (method instanceof Path) {
                        String value = ((Path) method).value();
                        if (!value.startsWith("/")) {
                            value = "/" + value;
                        }
                        path.append(value);
                        if (requestUri.equals(path.toString())) {
                            requestController.put(path.toString(), resource);
                            invokeMap.put(path.toString(), declaredMethod);
                        }
                    }
                }
            }
        });
        return invoke(invokeMap);
    }

    private void init() {
        File[] resources = getResources();
        for (File file : resources) {
            try {
                boolean hasController = false;
                Class<?> resource = classLoader.loadClass(BASE_PACKAGE + "." + file.getName().replace(".class", ""));
                Annotation[] annotations = resource.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof NettyController) {
                        hasController = true;
                    }
                }
                if (hasController) {
                    controllerList.add(resource);
                }
            } catch (ClassNotFoundException e) {
                throw new ResolverException("resource not found");
            }

        }
    }


    private File[] getResources() {
        try {
            File file = new File(Objects.requireNonNull(classLoader.getResource(BASE_PACKAGE.replace(".", "/"))).toURI());
            System.out.println("+===" + Arrays.toString(file.listFiles()));
            return file.listFiles(pathname -> pathname.getName().endsWith(".class"));
        } catch (URISyntaxException e) {
            throw new ResolverException("uri resolver error");
        }
    }


    private Object invoke(Map<String, Method> invokeMap) {
        Class<?> clz = requestController.get(requestUri);
        if (null == clz) {
            throw new ResolverException("no such controller to invoke");
        }
        Object instance;
        try {
            instance = clz.newInstance();
        } catch (Exception e) {
            throw new ResolverException("get instance field");
        }
        Method method = invokeMap.get(requestUri);
        if (null == method) {
            throw new ResolverException("no such method to invoke");
        }

        Parameter[] parameters = method.getParameters();
        Object[] params = new Object[parameters.length];
        for (int i = 0; i < params.length; i++) {
            for (Parameter parameter : parameters) {
                params[i] = requestParams.get(parameter.getName());
            }
        }
        Object result = null;
        try {
            result = method.invoke(instance, params);
        } catch (Exception e) {
            throw new ResolverException("invoke error");
        }
        return result;
    }


    @Override
    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    @Override
    public void setRequestParams(Map<String, Object> requestParams) {
        this.requestParams = requestParams;
    }
}
