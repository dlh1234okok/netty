package com.dlh.netty.nio_netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.time.Instant;
import java.util.Arrays;

/**
 * @author: dulihong
 * @date: 2019/3/28 9:24
 */
public class TimeServer {

    public void bind(int port) {

        // 用于服务端接受客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 用于进行SocketChannel的网络读写
        EventLoopGroup workGroup = new NioEventLoopGroup();
        // 创建启动NIO服务端的辅助启动类
        try {
            ServerBootstrap sbs = new ServerBootstrap();
            sbs.group(bossGroup, workGroup)
                    // ServerSocketChannel
                    .channel(NioServerSocketChannel.class)
                    // 配置tcp参数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());

            // 绑定端口，同步等待成功
            ChannelFuture cf = sbs.bind(port).sync();
            // 等待服务端监听端口关闭
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 退出，释放线程资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) {

            // 添加以换行符为结束标志的解码器
            ch.pipeline().addLast(new FixedLengthFrameDecoder(2));
            // 将接收到的对象转换为字符串
            ch.pipeline().addLast(new StringDecoder());
            ch.pipeline().addLast(new TimeServerHandler());

        }
    }


    public static void main(String[] args) {

        int port = 8082;

        new TimeServer().bind(port);
    }

}
