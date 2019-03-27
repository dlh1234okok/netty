package com.dlh.netty.aio;

/**
 * @author: dulihong
 * @date: 2019/3/27 13:48
 */
public class TimeClient {

    public static void main(String[] args) {

        String host = "127.0.0.1";
        int port = 8081;

        new Thread(new AsyncTimeClientHandler(host,port),"AIO_CLIENT").start();

    }

}
