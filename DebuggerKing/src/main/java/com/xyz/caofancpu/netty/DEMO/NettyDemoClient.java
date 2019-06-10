package com.xyz.caofancpu.netty.DEMO;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;

/**
 * Netty 客户端DEMO
 *
 * @author DebuggerKing
 */
public class NettyDemoClient {
    public static void main(String[] args) {
        String serverHost = "127.0.0.1";
        int serverPort = 8000;
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        channel.pipeline().addLast(new StringEncoder());
                    }
                });
        
        Channel channel = bootstrap.connect(serverHost, serverPort).channel();
        
        while (true) {
            channel.writeAndFlush(new Date() + ": Hello My King!");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
