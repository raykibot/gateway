package com.luo.gateway.demo1.socket.handler;

import com.luo.gateway.demo1.bind.IGenericService;
import com.luo.gateway.demo1.session.GateWaySession;
import com.luo.gateway.demo1.session.GateWaySessionFactory;
import com.luo.gateway.demo1.socket.BaseHandler;
import com.luo.gateway.demo1.socket.agreement.RequestParser;
import com.luo.gateway.demo1.socket.agreement.ResponseParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class GateWayServerHandler extends BaseHandler<FullHttpRequest> {


    private final GateWaySessionFactory gateWaySessionFactory;

    public GateWayServerHandler(GateWaySessionFactory gateWaySessionFactory) {
        this.gateWaySessionFactory = gateWaySessionFactory;
    }

    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {

        log.info("网关开始接收请求");
        //1.解析请求参数
        RequestParser requestParser = new RequestParser(request);
        String uri = requestParser.getUri();
        if (uri == null)  return;

        Map<String, Object> args = requestParser.parse();

        //2.调用会话服务
        GateWaySession gateWaySession = gateWaySessionFactory.openSession(uri);
        IGenericService genericService = gateWaySession.getMapper();
        Object result = genericService.$invoke(args);

        //3.返回请求结果
        ResponseParser responseParser = new ResponseParser();
        DefaultFullHttpResponse response = responseParser.parse(result);
        channel.writeAndFlush(response);
    }
}
