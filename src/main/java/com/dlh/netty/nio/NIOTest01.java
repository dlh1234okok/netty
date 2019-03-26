package com.dlh.netty.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author: dulihong
 * @date: 2019/3/26 10:58
 */
public class NIOTest01 {

    public static void main(String[] args) {
        ServerSocketChannel channel;
        int port = 8081;
        try {
            // 打开ServerSocketChannel，用于监听客户端连接
            channel = ServerSocketChannel.open();
            // 绑定监听端口
            channel.socket().bind(new InetSocketAddress(InetAddress.getByName("IP"), port));
            // 设置连接为非阻塞模式
            channel.configureBlocking(false);
            // 创建Reactor线程,创建多路复用器并启动线程
            Selector selector = Selector.open();
            // 将ServerSocketChannel注册到Reactor线程的多路复用器Selector上，监听Accept事件
            SelectionKey key = channel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
