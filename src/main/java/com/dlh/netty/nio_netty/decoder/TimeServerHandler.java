package com.dlh.netty.nio_netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author: dulihong
 * @date: 2019/3/28 9:37
 */
class TimeServerHandler extends ChannelHandlerAdapter {

    int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        /*ByteBuf bf = (ByteBuf) msg;
        byte[] req = new byte[bf.readableBytes()];
        bf.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8);*/
        /*String body = (String) msg;
        System.out.println("TIM SERVER RECEIVE ORDER:" + body + ++count);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf buf = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(buf);*/
        System.out.println(msg);
    }

   /* @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }*/


}
