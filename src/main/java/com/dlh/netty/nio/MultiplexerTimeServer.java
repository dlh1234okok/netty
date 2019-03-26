package com.dlh.netty.nio;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: dulihong
 * @date: 2019/3/26 13:40
 */
public class MultiplexerTimeServer implements Runnable {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    /**
     * 初始化多路复用器，绑定监听端口
     *
     * @param port 端口
     */
    public MultiplexerTimeServer(int port) {

        try {
            // 初始化多路复用器
            selector = Selector.open();
            // 打开serverSocketChannel
            serverSocketChannel = ServerSocketChannel.open();
            // 设置ServerSocketChannel为非阻塞
            serverSocketChannel.configureBlocking(false);
            // 绑定监听端口
            serverSocketChannel.bind(new InetSocketAddress(port), 1024);
            // 将ServerSocketChannel注册到Selector
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server is start in port :" + port);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        // 循环遍历selector
        while (!stop) {
            try {
                // 休眠时间为1s
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey selectionKey;
                while (it.hasNext()) {
                    selectionKey = it.next();
                    it.remove();
                    try {
                        handleInput(selectionKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (selectionKey != null) {
                            selectionKey.cancel();
                            if (selectionKey.channel() != null) {
                                selectionKey.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 多路复用器关闭后，所有注册在上面的channel和pipe等资源都会被自动去注册并关闭
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void handleInput(SelectionKey selectionKey) throws IOException {

        if (selectionKey.isValid()) {
            // 处理新接入的请求消息
            if (selectionKey.isAcceptable()) {
                // 接受新的连接
                ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
                SocketChannel sc = ssc.accept();
                // 设置socket通道为非阻塞
                sc.configureBlocking(false);
                // 将新连接注册到Selector中
                ssc.register(selector, SelectionKey.OP_READ);
            }
            // 读取channel中的数据
            if (selectionKey.isReadable()) {
                SocketChannel sc = (SocketChannel) selectionKey.channel();
                ByteBuffer readBuf = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuf);
                // 大于0 读取到了数据 编解码
                if (readBytes > 0) {
                    readBuf.flip();
                    byte[] bytes = new byte[readBuf.remaining()];
                    readBuf.get(bytes);
                    String str = new String(bytes, StandardCharsets.UTF_8);
                    System.out.println("the server receive order :" + str);
                    String talk = "QUERY TIME ORDER".equalsIgnoreCase(str) ?
                            new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                    doWrite(sc, talk);
                } else if (readBytes < 0) {
                    System.out.println("SOCKET IS CLOSE");
                    // 关闭链路
                    selectionKey.cancel();
                    sc.close();
                } else {
                    System.out.println("NO DATA HAS READ");
                }
            }
        }

    }

    private void doWrite(SocketChannel sc, String talk) throws IOException {
        if (null != talk && talk.trim().length() > 0) {
            byte[] bytes = talk.getBytes();
            ByteBuffer bf = ByteBuffer.allocate(bytes.length);
            bf.put(bytes);
            bf.flip();
            sc.write(bf);
        }

    }
}
