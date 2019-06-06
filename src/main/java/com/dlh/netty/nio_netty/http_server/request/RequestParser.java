package com.dlh.netty.nio_netty.http_server.request;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;

import java.util.Map;

/**
 * @author: dulihong
 * @date: 2019/6/6 13:43
 */
public interface RequestParser {

    Map<String, Object> parse(HttpRequest request);

}
