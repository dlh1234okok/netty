package com.dlh.netty.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author: dulihong
 * @date: 2019/3/26 16:15
 */
final class SocketUtil {


    static void closeSelectionKey(SelectionKey key) throws IOException {
        if (null != key) {
            key.cancel();
            if (null != key.channel()) {
                key.channel().close();
            }
        }
    }


    static String readBuff(ByteBuffer bf) {
        bf.flip();
        byte[] bytes = new byte[bf.remaining()];
        bf.get(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }


    static ByteBuffer writeBuf(String msg, SocketChannel sc) throws IOException {
        byte[] bytes = msg.getBytes();
        ByteBuffer bf = ByteBuffer.allocate(bytes.length);
        bf.put(bytes);
        bf.flip();
        System.out.println("return :" + msg);
        sc.write(bf);
        return bf;
    }

}
