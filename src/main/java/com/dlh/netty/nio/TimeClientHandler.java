package com.dlh.netty.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: dulihong
 * @date: 2019/3/26 15:51
 */
public class TimeClientHandler implements Runnable {

    private String host;

    private int port;

    private Selector selector;

    private SocketChannel socketChannel;

    private volatile boolean stop;

    /**
     * 初始化Selector/SocketChannel
     */
    public TimeClientHandler(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            // 发生错误关闭应用程序
            System.exit(1);
        }
    }

    @Override
    public void run() {
        // 开始连接
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 轮询多路复用器
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key;
                // 当有就绪的channel时
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handlerInput(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                        SocketUtil.closeSelectionKey(key);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        try {
            if (selector != null) {
                selector.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handlerInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel sc = (SocketChannel) key.channel();
            // 判断是否处于连接状态
            if (key.isConnectable()) {
                // 已经连接成功
                if (sc.finishConnect()) {
                    sc.register(selector, SelectionKey.OP_READ);
                    doWrite(sc);
                    // 连接失败停止服务
                } else {
                    System.exit(1);
                }
            }
            // 判断SocketChannel是否可读
            if (key.isReadable()) {
                ByteBuffer bf = ByteBuffer.allocate(1024);
                int readBytes = sc.read(bf);
                if (readBytes > 0) {
                    String body = SocketUtil.readBuff(bf);
                    System.out.println("NOW is :" + body);
                    this.stop = true;
                } else if (readBytes < 0) {
                    System.out.println("SOCKET IS CLOSE");
                    // 关闭链路
                    key.cancel();
                    sc.close();
                } else {
                    System.out.println("NO DATA HAS READ");
                }
            }
        }

    }

    private void doConnect() throws IOException {
        // 如果直接连接成功，则注册到多路复用器上，发送请求消息，读应答
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        } else {
            // 注册OP_CONNECT，当服务端返回TCP syn-ack消息后，Selector就能轮询到这个channel处于就绪状态
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel socketChannel) throws IOException {
        String msg = "QUERY TIME ORDER";
        ByteBuffer bf = SocketUtil.writeBuf(msg, socketChannel);
        if (!bf.hasRemaining()) {
            System.out.println("SEND ORDER 2 SERVER SUCCEED");
        }
    }
}
