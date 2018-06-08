package com.yan.netty.examples.socket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * socket编程的客户端
 * @author caoqiang
 * @date: 2018年6月8日 上午10:22:06
 */
public class MyClient {

    public static void main(String[] args) throws Exception {
        // 客户端只有一个事件循环组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            // 客户端启动器
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8999).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}