package com.dlh.netty.nio_netty.http_server.request;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: dulihong
 * @date: 2019/6/6 13:44
 */
public class GetRequestParse implements RequestParser {

    @Override
    public Map<String, Object> parse(HttpRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        QueryStringDecoder qsd = new QueryStringDecoder(request.getUri());
        qsd.parameters().forEach((k, v) -> resultMap.put(k, v.get(0)));
        return resultMap;
    }

}
