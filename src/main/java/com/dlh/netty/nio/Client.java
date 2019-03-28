package com.dlh.netty.nio;

/**
 * @author: dulihong
 * @date: 2019/3/26 15:49
 */
class Client {

    public static void main(String[] args) {

        int port = 8081;
        String host = "127.0.0.1";

        new Thread(new TimeClientHandler(host, port), "NIO_CLIENT").start();

    }

}
