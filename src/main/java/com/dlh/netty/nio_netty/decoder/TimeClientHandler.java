package com.dlh.netty.nio_netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

/**
 * @author: dulihong
 * @date: 2019/3/28 10:00
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    // private final ByteBuf firstMsg;

    byte[] req;

    int count;

    public TimeClientHandler() {
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
        /*firstMsg = Unpooled.buffer(req.length);
        firstMsg.writeBytes(req);*/
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf bf = null;
        for (int i = 0; i < 100; i++) {
            bf = Unpooled.buffer(req.length);
            bf.writeBytes(req);
            ctx.writeAndFlush(bf);
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*ByteBuf bf = (ByteBuf) msg;
        byte[] req = new byte[bf.readableBytes()];
        bf.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8);*/
        String body = (String) msg;
        System.out.println("NOW IS :" + body + ++count);
    }
}
