package com.dlh.netty.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author: dulihong
 * @date: 2019/3/27 11:43
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] bytes = new byte[attachment.remaining()];
        attachment.get(bytes);
        String req = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("THE TIME SERVER RECEIVE ORDER : " + req);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        doWrite(currentTime);
    }

    private void doWrite(String currentTime) {
        if (null != currentTime && currentTime.trim().length() > 0) {
            byte[] bytes = currentTime.getBytes();
            ByteBuffer bf = ByteBuffer.allocate(bytes.length);
            bf.put(bytes);
            bf.flip();
            channel.write(bf, bf, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    // 如果还有剩余字节可写说明没有发送完成，继续发送
                    if (attachment.hasRemaining()) {
                        channel.write(attachment, attachment, this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
