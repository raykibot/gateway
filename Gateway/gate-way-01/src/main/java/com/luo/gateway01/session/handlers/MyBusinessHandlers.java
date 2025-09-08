package com.luo.gateway01.session.handlers;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

public class MyBusinessHandlers extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {

        System.out.println("收到请求：" + fullHttpRequest.uri());


        //构造相应内容
        String result = "网关拦截了你的访问，路径为：" + fullHttpRequest.uri();
        byte[] jsonBytes = JSON.toJSONBytes(result);

        //沟道HTTP响应
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK);

        response.content().writeBytes(jsonBytes);

        //设置响应头
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json;charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, jsonBytes.length);
        response.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);

        //写回客户端
        ctx.writeAndFlush(response);
    }
}
