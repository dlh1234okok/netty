package com.dlh.netty.aio;

import java.io.BufferedReader;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author: dulihong
 * @date: 2019/3/27 11:38
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
        attachment.serverSocketChannel.accept(attachment, this);

        ByteBuffer bf = ByteBuffer.allocate(1024);
        result.read(bf, bf, new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
        exc.printStackTrace();
        attachment.countDownLatch.countDown();
    }
}
