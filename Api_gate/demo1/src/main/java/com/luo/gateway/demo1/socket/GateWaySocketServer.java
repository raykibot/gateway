package com.luo.gateway.demo1.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

@Slf4j
public class GateWaySocketServer implements Callable<Channel> {

    private final EventLoopGroup boss = new NioEventLoopGroup();
    private final EventLoopGroup worker = new NioEventLoopGroup();
    private Channel channel;


    @Override
    public Channel call() throws Exception {

        ChannelFuture channelFuture = null;

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new GateWayChannelInitializer());

            channelFuture = serverBootstrap.bind(new InetSocketAddress(7777)).syncUninterruptibly();
            this.channel = channelFuture.channel();
        } catch (Exception e) {
            throw new RuntimeException("server start error");
        }
        finally {
            if (channelFuture != null && channelFuture.isSuccess()) {
                log.info("server start success");
            } else {
                throw new RuntimeException("server start error");
            }
        }
        return channel;
    }
}
