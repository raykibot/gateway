package com.luo.gateway.demo1.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.channels.SocketChannel;

public abstract class BaseHandler<T> extends SimpleChannelInboundHandler<T> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
        session(ctx, ctx.channel(), msg);
    }

    protected abstract void session(ChannelHandlerContext ctx, final Channel channel, T msg);
}
