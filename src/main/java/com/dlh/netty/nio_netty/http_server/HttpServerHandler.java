package com.dlh.netty.nio_netty.http_server;

import com.alibaba.fastjson.JSON;
import com.dlh.netty.common.exceptions.RequestException;
import com.dlh.netty.nio_netty.http_server.path_resolver.DefaultPathResolver;
import com.dlh.netty.nio_netty.http_server.path_resolver.PathResolverHandler;
import com.dlh.netty.nio_netty.http_server.request.RequestParseFactory;
import com.dlh.netty.nio_netty.http_server.request.RequestParser;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author: dulihong
 * @date: 2019/6/6 10:10
 */
public class HttpServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {

            HttpRequest httpRequest = (HttpRequest) msg;

            HttpMethod method = httpRequest.getMethod();

            RequestParser requestParser = RequestParseFactory.createRequestParse(method);
            if (null == requestParser) {
                throw new RequestException("the request method can not discern");
            }
            Map<String, Object> params = requestParser.parse(httpRequest);

            PathResolverHandler pathResolverHandler = new DefaultPathResolver();
            pathResolverHandler.setRequestParams(params);
            pathResolverHandler.setRequestUri(httpRequest.getUri().split("\\?")[0]);
            Object result = pathResolverHandler.execute();

            String responseHtml = JSON.toJSONString(result);
            byte[] bytes = responseHtml.getBytes(StandardCharsets.UTF_8);

            FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(bytes));
            fullHttpResponse.headers().set("Content-Type", "application/json; charset=utf-8");
            fullHttpResponse.headers().set("Content-Length", Integer.toString(bytes.length));
            ctx.writeAndFlush(fullHttpResponse);
        } else {
            throw new RequestException("not http connection");
        }
    }
}
