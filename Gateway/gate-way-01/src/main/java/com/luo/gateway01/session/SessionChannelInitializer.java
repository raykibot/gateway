package com.luo.gateway01.session;

import com.luo.gateway01.session.handlers.MyBusinessHandlers;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;


public class SessionChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline channelPipeline = socketChannel.pipeline();

        //请求解码器
        channelPipeline.addLast(new HttpRequestDecoder());

        //响应解码器
        channelPipeline.addLast(new HttpResponseEncoder());

        //聚合器
        channelPipeline.addLast(new HttpObjectAggregator(1024 * 1024));

        //添加具体业务逻辑
        channelPipeline.addLast(new MyBusinessHandlers());
    }
}
