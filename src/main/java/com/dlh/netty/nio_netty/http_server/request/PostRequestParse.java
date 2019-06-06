package com.dlh.netty.nio_netty.http_server.request;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: dulihong
 * @date: 2019/6/6 13:47
 */
public class PostRequestParse implements RequestParser {
    @Override
    public Map<String, Object> parse(HttpRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpPostRequestDecoder postDecoder = new HttpPostRequestDecoder(request);
        //postDecoder.offer(request);
        List<InterfaceHttpData> bodyHttpDatas = postDecoder.getBodyHttpDatas();
        bodyHttpDatas.forEach(body -> {
            Attribute attribute = (Attribute) body;
            try {
                resultMap.put(attribute.getName(), attribute.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return resultMap;
    }
}
