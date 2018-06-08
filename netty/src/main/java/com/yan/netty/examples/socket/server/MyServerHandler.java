package com.yan.netty.examples.socket.server;

import java.util.UUID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 自己定义的一个handler
 * @author caoqiang
 * @date: 2018年6月8日 上午10:21:03
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + ", " + msg);

        ctx.channel().writeAndFlush("from server: " + UUID.randomUUID());
    }

    /**
     * 对于异常的处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}