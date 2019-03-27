package com.dlh.netty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author: dulihong
 * @date: 2019/3/27 11:33
 */
public class AsyncTimeServerHandler implements Runnable {

    private int port;

    AsynchronousServerSocketChannel serverSocketChannel;

    CountDownLatch countDownLatch;

    AsyncTimeServerHandler(int port) {
        this.port = port;
        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("AIO_SERVER START AT:" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        countDownLatch = new CountDownLatch(1);

        doAccept();
        try {
            // 完成一组操作前阻塞
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void doAccept() {
        serverSocketChannel.accept(this, new AcceptCompletionHandler());
    }
}
