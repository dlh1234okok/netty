package com.dlh.netty.nio_netty.http_server.resources;

import com.dlh.netty.nio_netty.http_server.annotation.NettyController;
import com.dlh.netty.nio_netty.http_server.annotation.Path;

/**
 * @author: dulihong
 * @date: 2019/6/6 15:55
 */
@NettyController
@Path("hello")
public class TestController {

    @Path("t1")
    public String test(String t) {
        return "test1" + t;
    }
    @Path("t2")
    public String test2(Integer a) {
        return "test2" + a;
    }
}
