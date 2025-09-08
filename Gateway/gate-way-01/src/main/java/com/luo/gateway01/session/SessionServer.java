package com.luo.gateway01.session;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


import java.net.InetSocketAddress;

public class SessionServer {

    //负责接收连接
    private final EventLoopGroup boos = new NioEventLoopGroup();
    //负责处理具体的读写
    private final EventLoopGroup work = new NioEventLoopGroup();

    public void start() {

        try {
            //1.创建启动引导类
            ServerBootstrap server = new ServerBootstrap();

            //2.配置线程组，通道类型，处理器
            server.group(boos, work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new SessionChannelInitializer());

            //3.绑定端口，启动服务
            ChannelFuture channelFuture = server.bind(new InetSocketAddress(7397)).sync();
            Channel channel = channelFuture.channel();
            System.out.println("网关服务启动成功，端口：7397");

            //阻塞等待服务关闭
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boos.shutdownGracefully();
            work.shutdownGracefully();
        }

    }
}
