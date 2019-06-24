package com.dlh.netty.nio_netty.http_server.resolver;

import com.dlh.netty.common.exceptions.ResolverException;
import com.dlh.netty.nio_netty.http_server.annotation.NettyController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @author: dulihong
 * @date: 2019/6/10 9:31
 */
public class MethodParameter {

    private String requestUri;

    private Map<String, Object> requestParam;

    private Class<?>[] paramClass;

    private Class<?>[] referenceType = new Class[]{String.class,Integer.class,Double.class,Character.class,Float.class,Short.class};

    private Method method;

    private static String controller_package;


    static {

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("application.properties")));
            controller_package = properties.getProperty("netty.controller.package");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public Class<?> classLoader() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try {
            File file = new File(Objects.requireNonNull(classLoader.getResource(controller_package.replace(".", "/"))).toURI());
            File[] files = file.listFiles(f -> f.getName().endsWith(".class"));
            if (null == files) {
                return null;
            }
            for (File f : files) {
                Class<?> controllerClass = classLoader.loadClass(controller_package + f.getName().replace(".class", ""));
                Annotation[] annotations = controllerClass.getAnnotations();
                boolean notController = true;
                for (Annotation annotation : annotations) {
                    if (annotation instanceof NettyController) {
                        notController = false;
                        break;
                    }
                }
                if (notController) {
                    throw new ResolverException("controller no annotation");
                }
            }
        } catch (URISyntaxException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public Map<String, Object> getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(Map<String, Object> requestParam) {
        this.requestParam = requestParam;
    }

    public Class<?>[] getParamClass() {
        return paramClass;
    }

    public void setParamClass(Class<?>[] paramClass) {
        this.paramClass = paramClass;
    }

    public Class<?>[] getReferenceType() {
        return referenceType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

}
