package com.dlh.netty.nio_netty.http_server.request;

import io.netty.handler.codec.http.HttpMethod;

/**
 * @author: dulihong
 * @date: 2019/6/6 14:04
 */
public class RequestParseFactory {

    public static RequestParser createRequestParse(HttpMethod method) {

        RequestParser requestParser;

        if (HttpMethod.GET == method) {
            requestParser = new GetRequestParse();
        } else if (HttpMethod.POST == method) {
            requestParser = new PostRequestParse();
        } else {
            return null;
        }
        return requestParser;
    }

}
