package com.dlh.netty.nio_netty.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author: dulihong
 * @date: 2019/6/4 11:38
 */
public class SubReqServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
        System.out.println("client > message:["+req.toString()+"]");
        ctx.writeAndFlush(subscribeResp(req.getSubReqId()));
    }

    private SubscribeRespProto.SubscribeResp subscribeResp(int subId) {
        SubscribeRespProto.SubscribeResp.Builder respBuilder = SubscribeRespProto.SubscribeResp.newBuilder();
        respBuilder.setSubReqId(subId);
        respBuilder.setRespCode(1);
        respBuilder.setDesc("netty book order succeed ---");
        return respBuilder.build();
    }
}
