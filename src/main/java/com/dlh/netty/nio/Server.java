package com.dlh.netty.nio;

/**
 * @author: dulihong
 * @date: 2019/3/26 14:55
 */
class Server {

    public static void main(String[] args) {

        int port = 8081;

        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);

        new Thread(timeServer, "NIO_SERVER").start();

    }
}
