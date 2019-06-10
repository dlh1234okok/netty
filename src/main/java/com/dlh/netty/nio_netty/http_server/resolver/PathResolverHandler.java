package com.dlh.netty.nio_netty.http_server.resolver;


import java.util.Map;

/**
 * @author: dulihong
 * @date: 2019/6/6 11:38
 */
public interface PathResolverHandler {

    public Object execute();

    public void setRequestUri(String requestUri);

    public void setRequestParams(Map<String, Object> requestParams);
}
