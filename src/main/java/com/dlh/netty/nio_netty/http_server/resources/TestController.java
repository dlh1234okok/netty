package com.dlh.netty.nio_netty.http_server.resources;

import com.dlh.netty.nio_netty.http_server.servlet.annotation.NettyController;
import com.dlh.netty.nio_netty.http_server.servlet.annotation.Path;

/**
 * @author: dulihong
 * @date: 2019/6/6 15:55
 */
@NettyController
@Path("hello")
public class TestController {

    @Path("t1")
    public String test(String t) {
        System.out.println(t);
        return t;
    }

}
