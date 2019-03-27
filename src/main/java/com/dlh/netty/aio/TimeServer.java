package com.dlh.netty.aio;

/**
 * @author: dulihong
 * @date: 2019/3/27 11:32
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8081;
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        new Thread(timeServer, "AIO_SERVER").start();
    }
}
