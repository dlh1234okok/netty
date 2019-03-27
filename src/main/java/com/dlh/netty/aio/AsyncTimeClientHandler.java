package com.dlh.netty.aio;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

/**
 * @author: dulihong
 * @date: 2019/3/27 14:54
 */
public class AsyncTimeClientHandler implements Runnable, CompletionHandler<Void, AsyncTimeClientHandler> {

    private String host;

    private int port;

    private AsynchronousSocketChannel client;

    private CountDownLatch latch;

    public AsyncTimeClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            client = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        latch = new CountDownLatch(1);

        client.connect(new InetSocketAddress(host, port), this, this);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, AsyncTimeClientHandler attachment) {
        String msg = "QUERY TIME ORDER";
        byte[] bytes = msg.getBytes();
        ByteBuffer write = ByteBuffer.allocate(bytes.length);
        write.put(bytes);
        write.flip();
        client.write(write, write, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer bf) {
                // 如果还有字节未发送
                if (bf.hasRemaining()) {
                    // 继续异步发送
                    client.write(bf, bf, this);
                } else {
                    ByteBuffer read = ByteBuffer.allocate(1024);
                    client.read(read, read, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer bf) {
                            bf.flip();
                            byte[] bytes1 = new byte[bf.remaining()];
                            bf.get(bytes1);
                            String body;
                            body = new String(bytes1, StandardCharsets.UTF_8);
                            System.out.println("NOW IS:" + body);
                            latch.countDown();
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer bf) {
                            try {
                                client.close();
                                latch.countDown();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
                try {
                    client.close();
                    latch.countDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
        exc.printStackTrace();
        try {
            client.close();
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
